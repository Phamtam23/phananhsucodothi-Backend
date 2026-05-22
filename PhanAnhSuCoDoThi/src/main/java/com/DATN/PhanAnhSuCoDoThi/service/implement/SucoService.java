package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.mapper.SucoMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.DATN.PhanAnhSuCoDoThi.util.IdGenerator.generateMaSuCo;
import static com.DATN.PhanAnhSuCoDoThi.util.IdGenerator.generateMaTep;

@Service
@RequiredArgsConstructor
public class SucoService implements ISucoService {
    private final SucoRepository sucoRepository;
    private final TepSuCoRepository tepSuCoRepository;
    private final SucoMapper sucoMapper;
    private final NguoidanRepository nguoidanRepository;
    private final PhieuPhanLoaiRepository phieuPhanLoaiRepository;
    private final GeminiService geminiService;
    private final SpamDetectionService spamDetectionService;
    @Override
    public PageResponse<SucoSummaryResponse> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAll(pageable);

        List<String> listMaSuCo = pageResult.getContent()
                .stream()
                .map(suCo -> suCo.getMaSuCo())
                .collect(Collectors.toList());



        Map<String,List<TepSuCoEntity>> tepTheoSuCo = layTepTheoSuCo(listMaSuCo);
        Page<SucoSummaryResponse> mapper = pageResult.map(suco ->
                sucoMapper.toSummary(
                        suco,
                        tepTheoSuCo.getOrDefault(suco.getMaSuCo(), Collections.emptyList()),
                        getPhanLoaiSuco(suco.getMaSuCo())

        ) );

        return PageResponse.of(mapper);
    }

    public List<String> getPhanLoaiSuco(String maSuCo) {
        return phieuPhanLoaiRepository.findAllByMaSuCo(maSuCo)
                .stream()
                .map(p -> p.getLoai().getTenLoaiSuCo())
                .toList();
    }

    @Override
    public PageResponse<SucoSummaryResponse> findByTrangThai(int page, int size, TrangThaiSuCo trangThaiSuCo) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAllByTrangThai(trangThaiSuCo,pageable);
        List<String> listMaSuCo = pageResult.getContent()
                .stream()
                .map(suCo -> suCo.getMaSuCo())
                .collect(Collectors.toList());

        Map<String,List<TepSuCoEntity>> tepTheoSuCo = layTepTheoSuCo(listMaSuCo);
        Page<SucoSummaryResponse> mapper = pageResult.map(suco ->
                sucoMapper.toSummary(
                        suco,
                        tepTheoSuCo.getOrDefault(suco.getMaSuCo(), Collections.emptyList()),
                        getPhanLoaiSuco(suco.getMaSuCo())

                ) );

        return PageResponse.of(mapper);
    }

    @Override
    public SucoDetailResponse findById(String maSuco,String maNguoiDan) {
        SucoEntity sucoEntity = sucoRepository.findById(maSuco)
                .orElseThrow(()-> new RuntimeException("Khong tim thấy sự cố"));
        List<TepSuCoEntity> listTepSuCo = tepSuCoRepository.findBySuCo_MaSuCo(sucoEntity.getMaSuCo());
        boolean isOwner =
                sucoEntity.getNguoiDan().getMaNguoiDan().equals(maNguoiDan);

        boolean daXuLy =
                sucoEntity.getTrangThai() == TrangThaiSuCo.DA_XU_LY_XONG;

        return sucoMapper.toDetail(sucoEntity, listTepSuCo, getPhanLoaiSuco(sucoEntity.getMaSuCo()));
    }
    @Override
    public SucoDetailResponse create(CreateSucoRequest request, String maNguoiDan) {

        // 1. tìm người dân
        NguoidanEntity nguoiDan =nguoidanRepository.findById (maNguoiDan)
                .orElseThrow(() -> new RuntimeException("Người dân không tồn tại"));

        String tieuDe = geminiService.generateTitle(request.getNoiDung(),request.getDiaDiem());
        // 2. tạo sự cố
        SucoEntity suco = new SucoEntity();
        suco.setMaSuCo(generateMaSuCo(maNguoiDan));
        suco.setNguoiDan(nguoiDan);
        suco.setKinhDo(request.getKinhDo());
        suco.setViDo(request.getViDo());
        suco.setDiaDiem(request.getDiaDiem());
        suco.setNoiDung(request.getNoiDung());
        suco.setTieuDe(tieuDe);

        // default
        suco.setTrangThai(TrangThaiSuCo.CHO_TIEP_NHAN);
        suco.setLaSpam(false);
        suco.setDiemSpam(0);
        suco.setThoiGianTao(LocalDateTime.now());

        // 3. save suco trước
        sucoRepository.save(suco);

        // 4. lưu danh sách tệp
        List<TepSuCoEntity> tepList = new ArrayList<>();

        if (request.getMediaUrls() != null && !request.getMediaUrls().isEmpty()) {

            for (String url : request.getMediaUrls()) {

                TepSuCoEntity tep = new TepSuCoEntity();
                tep.setSuCo(suco);
                tep.setUrl(url);
                tep.setMaTepSuCo(generateMaTep());
                // auto detect type
                if (url.endsWith(".mp4") || url.endsWith(".mov")) {
                    tep.setLoai("VIDEO");
                } else {
                    tep.setLoai("IMAGE");
                }

                tepList.add(tep);
            }

            tepSuCoRepository.saveAll(tepList);
        }

        // Kích hoạt phân tích spam bất đồng bộ bằng AI
        spamDetectionService.analyzeSpamAsync(suco.getMaSuCo());

        // 5. trả response
        return sucoMapper.toDetail(suco, tepList, getPhanLoaiSuco(suco.getMaSuCo()));
    }

    @Override
    public PageResponse<SucoSummaryResponse> findByNguoiDan(String maNguoiDan, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAllByNguoiDan_MaNguoiDan(maNguoiDan,pageable);

        List<String> listMaSuCo = pageResult.getContent()
                .stream()
                .map(suCo -> suCo.getMaSuCo())
                .collect(Collectors.toList());

        Map<String,List<TepSuCoEntity>> tepTheoSuCo = layTepTheoSuCo(listMaSuCo);
        Page<SucoSummaryResponse> mapper = pageResult.map(suco ->
                sucoMapper.toSummary(
                        suco,
                        tepTheoSuCo.getOrDefault(suco.getMaSuCo(),Collections.emptyList()),
                        getPhanLoaiSuco(suco.getMaSuCo())
                ) );

        return PageResponse.of(mapper);
    }



    private Map<String, List<TepSuCoEntity>> layTepTheoSuCo(List<String> maSuCoList) {
        if (maSuCoList == null || maSuCoList.isEmpty()) {
            return Collections.emptyMap();
        }
        return tepSuCoRepository
                .findBySuCo_MaSuCoIn(maSuCoList)
                .stream()
                .collect(Collectors.groupingBy(tep -> tep.getSuCo().getMaSuCo()));
    }

}
