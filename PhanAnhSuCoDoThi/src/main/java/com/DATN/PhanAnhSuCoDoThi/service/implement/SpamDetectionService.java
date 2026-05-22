package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import com.DATN.PhanAnhSuCoDoThi.repository.SucoRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.TepSuCoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpamDetectionService {

    private final SucoRepository sucoRepository;
    private final TepSuCoRepository tepSuCoRepository;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${spam.detection.api.url:http://localhost:8000/analyze}")
    private String spamApiUrl;

    @Async("spamCheckExecutor")
    public void analyzeSpamAsync(String maSuCo) {
        log.info("Bắt đầu phân tích spam bất đồng bộ cho sự cố mã: {}", maSuCo);
        try {
            // 1. Tìm sự cố trong DB
            SucoEntity suco = sucoRepository.findById(maSuCo).orElse(null);
            if (suco == null) {
                log.warn("Không tìm thấy sự cố: {}", maSuCo);
                return;
            }

            // 2. Lấy danh sách tệp đính kèm
            List<TepSuCoEntity> teps = tepSuCoRepository.findBySuCo_MaSuCo(maSuCo);

            // 3. Chuyển đổi đường dẫn URL cục bộ thành đường dẫn vật lý tuyệt đối trên đĩa
            List<String> images = teps.stream()
                    .filter(t -> "IMAGE".equalsIgnoreCase(t.getLoai()))
                    .map(t -> getAbsoluteDiskPath(t.getUrl()))
                    .toList();

            List<String> videos = teps.stream()
                    .filter(t -> "VIDEO".equalsIgnoreCase(t.getLoai()))
                    .map(t -> getAbsoluteDiskPath(t.getUrl()))
                    .toList();

            // 4. Chuẩn bị Request Body gửi FastAPI
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("content", suco.getNoiDung());
            requestBody.put("images", images);
            requestBody.put("videos", videos);

            log.info("Đang gửi yêu cầu phân tích spam đến AI API (FastAPI) tại: {}. Payload: {}", spamApiUrl, requestBody);

            // 5. Gửi POST Request
            @SuppressWarnings("unchecked")
            Map<String, Object> response = restTemplate.postForObject(spamApiUrl, requestBody, Map.class);

            if (response != null) {
                String label = (String) response.get("label"); // SPAM / NGHI_NGO / SACH
                Double finalScore = (Double) response.get("final_score");
                String summary = (String) response.get("summary");

                log.info("Kết quả kiểm tra AI cho sự cố {}: Nhãn={}, Điểm={}, Tóm tắt={}", 
                        maSuCo, label, finalScore, summary);

                // 6. Cập nhật kết quả vào DB
                suco.setDiemSpam((int) (finalScore * 100));
                suco.setLyDoSpam(summary);
                suco.setLaSpam("SPAM".equalsIgnoreCase(label));

                sucoRepository.save(suco);
                log.info("Đã cập nhật kết quả phân tích spam thành công cho sự cố mã: {}", maSuCo);
            }
        } catch (Exception e) {
            log.error("Lỗi khi tự động phân tích spam cho sự cố mã: " + maSuCo, e);
        }
    }

    /**
     * Chuyển đổi URL tương đối cục bộ dạng "/uploads/suco/images/..."
     * thành đường dẫn tuyệt đối đầy đủ trên ổ đĩa cứng của máy chủ.
     */
    private String getAbsoluteDiskPath(String relativeUrl) {
        if (relativeUrl == null || relativeUrl.trim().isEmpty()) {
            return "";
        }
        // Bỏ ký tự '/' ở đầu nếu có để Paths.get phân tích đúng
        String cleanPath = relativeUrl.startsWith("/") ? relativeUrl.substring(1) : relativeUrl;
        Path absolutePath = Paths.get(cleanPath).toAbsolutePath();
        return absolutePath.toString();
    }
}
