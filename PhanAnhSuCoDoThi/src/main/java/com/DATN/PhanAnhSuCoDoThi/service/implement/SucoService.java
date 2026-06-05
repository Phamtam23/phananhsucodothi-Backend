package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.SuCoFilterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.UpdateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.DoUuTien;
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
    private final TitleGeneratorService titleGeneratorService;
    @Override
    public PageResponse<SucoSummaryResponse> findAll(SuCoFilterRequest filter,int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAllFilter(
                filter.getKeyword(),
                filter.getTrangThai(),
                filter.getMaLoai(),
                filter.getDiaDiem(),
                pageable
        );

        return mapPage(pageResult);
    }

    @Override
    public SucoDetailResponse findById(String maSuco,String maNguoiDan) {
        SucoEntity sucoEntity = sucoRepository.findById(maSuco)
                .orElseThrow(()-> new RuntimeException("Khong tim thấy sự cố"));
        List<TepSuCoEntity> listTepSuCo = tepSuCoRepository.findBySuCo_MaSuCo(maSuco);

        List<String> loais = phieuPhanLoaiRepository.findAllByMaSuCo(maSuco)
                .stream()
                .map(p -> p.getLoai().getTenLoaiSuCo())
                .toList();
        return sucoMapper.toDetail(sucoEntity, listTepSuCo,loais);
    }

        @Override
       public PageResponse<SucoSummaryResponse> findForMap(String maLoai, TrangThaiSuCo trangThai, int page, int size){
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        List<TrangThaiSuCo> trangThaiList = 
                trangThai == null 
                ? List.of(TrangThaiSuCo.CHO_TIEP_NHAN, TrangThaiSuCo.DANG_XU_LY) 
                : List.of(trangThai);

         Page<SucoEntity> pageResult = sucoRepository.findAllForMap(
                trangThaiList,
                 maLoai,
                pageable
         );
        return mapPage(pageResult);
       }

       public SucoDetailResponse update(UpdateSucoRequest updateSucoRequest,String maNguoiDan) {

            SucoEntity sucoEntity = sucoRepository.findById(updateSucoRequest.getMaSuCo())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy sự cố"));

            if(!maNguoiDan.equals(sucoEntity.getNguoiDan().getMaNguoiDan())) {
                throw new RuntimeException("Không có thẩm quyền");
            }
            if(updateSucoRequest.getNoiDung() != null)
            {
                sucoEntity.setNoiDung(updateSucoRequest.getNoiDung());
            }
            if(updateSucoRequest.getMediaUrls() != null)
            {
                luuDanhSachTep(sucoEntity,updateSucoRequest.getMediaUrls());
            }

            sucoRepository.save(sucoEntity);

            return sucoMapper.toDetail(sucoEntity,null,null);
       }

    @Override
    public SucoDetailResponse create(CreateSucoRequest request, String maNguoiDan) {

        // 1. tìm người dân
        NguoidanEntity nguoiDan =nguoidanRepository.findById (maNguoiDan)
                .orElseThrow(() -> new RuntimeException("Người dân không tồn tại"));

        String tieuDe = titleGeneratorService.generateTitle(request.getNoiDung(),request.getDiaDiem());
        // 2. tạo sự cố
        SucoEntity suco = new SucoEntity();
        suco.setMaSuCo(generateMaSuCo(maNguoiDan));
        suco.setNguoiDan(nguoiDan);
        suco.setKinhDo(request.getKinhDo());
        suco.setViDo(request.getViDo());
        suco.setDiaDiem(request.getDiaDiem());
        suco.setNoiDung(request.getNoiDung());
        suco.setDoUuTien(phanTichDoUuTien(request.getNoiDung()));
        suco.setTieuDe(tieuDe);

        // default
        suco.setTrangThai(TrangThaiSuCo.CHO_TIEP_NHAN);
        suco.setLaSpam(false);
        suco.setDiemSpam(0);
        suco.setThoiGianTao(LocalDateTime.now());

        // 3. save suco trước
        sucoRepository.save(suco);

        // 4. lưu danh sách tệp
        List<TepSuCoEntity> tepList = luuDanhSachTep(suco,request.getMediaUrls());

        // Kích hoạt phân tích spam bất đồng bộ bằng AI
        spamDetectionService.analyzeSpamAsync(suco.getMaSuCo());

        // 5. trả response
        return sucoMapper.toDetail(suco, tepList,null);
    }

    @Override
    public PageResponse<SucoSummaryResponse> findByNguoiDan(String maNguoiDan,SuCoFilterRequest filter, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAllByNguoiDanFilter(
                maNguoiDan,
                filter.getKeyword(),
                filter.getTrangThai(),
                filter.getMaLoai(),
                filter.getDiaDiem(),
                pageable
        );
        return mapPage(pageResult);
    }

    /* ------------------------------------Helper--------------------------------------------------------*/

    private PageResponse<SucoSummaryResponse> mapPage(
            Page<SucoEntity> pageResult
    )
    {
        List<String> maSuCos = pageResult.getContent()
                .stream()
                .map(SucoEntity::getMaSuCo)
                .toList();

        Map<String,List<TepSuCoEntity>> tepTheoSuCo = layTepTheoSuCo(maSuCos);
        Map<String,List<String>> loaiTheoSuCo = layLoaiTheoSuCo(maSuCos);

        Page<SucoSummaryResponse> result = pageResult
                .map(suco ->
                        sucoMapper.toSummary(suco,
                                tepTheoSuCo.getOrDefault(suco.getMaSuCo(),Collections.emptyList()),
                                loaiTheoSuCo.getOrDefault(suco.getMaSuCo(),Collections.emptyList())
                                )
                        );

            return PageResponse.of(result);
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

    public Map<String,List<String>> layLoaiTheoSuCo(List<String> maSuCos) {

        return phieuPhanLoaiRepository
                .findBySuCo_MaSuCoIn(maSuCos)
                .stream()
                .collect(Collectors.groupingBy(
                        p -> p.getSuCo().getMaSuCo(),
                        Collectors.mapping(p -> p.getLoai().getTenLoaiSuCo(), Collectors.toList())
                ));

    }

    private List<TepSuCoEntity> luuDanhSachTep(
            SucoEntity suco,
            List<String> mediaUrls
    )
    {
        if (mediaUrls == null || mediaUrls.isEmpty()) {
            return Collections.emptyList();
        }

        List<TepSuCoEntity> tepSuCoEntities = mediaUrls
                .stream()
                .map(url ->{
                    TepSuCoEntity tep = new TepSuCoEntity();
                    tep.setSuCo(suco);
                    tep.setMaTepSuCo(generateMaTep());
                    tep.setUrl(url);
                    tep.setLoai(
                            url.endsWith(".mp4") ? "VIDEO" : "IMAGE"
                    );

                    return tep;
                })
                .toList();

        tepSuCoRepository.saveAll(tepSuCoEntities);

        return tepSuCoEntities;
    }


    private DoUuTien phanTichDoUuTien(String noiDung)
    {
        if (noiDung == null || noiDung.trim().isEmpty()) {
            return DoUuTien.THAP;
        }
        String content = noiDung.toLowerCase();

        List<String> khanCapKeywords = List.of(
                "cháy", "hỏa hoạn", "chập điện", "đứt dây điện", "hở điện", "giật điện",
                "nổ trạm biến áp", "rò rỉ ga", "sập nhà", "đổ sập", "sạt lở đất",
                "hố tử thần", "tai nạn nghiêm trọng", "chết người"
        );

        if(khanCapKeywords.contains(content))
        {
            return DoUuTien.KHAN_CAP;
        }

        List<String> caoKeywords = List.of(
                "ngập lụt", "ngập sâu", "vỡ ống nước chính", "đổ cây đè", "cây đổ chắn ngang",
                "cản trở giao thông", "kẹt xe", "tắc đường", "hỏng đèn tín hiệu", "hỏng đèn giao thông",
                "sụt lún đường"
        );
        if (caoKeywords.stream().anyMatch(content::contains)) {
            return DoUuTien.CAO;
        }

        List<String> trungBinhKeywords = List.of(
                "hỏng đèn đường", "đèn đường tắt", "ổ gà", "ổ voi", "mất nắp cống",
                "hỏng nắp cống", "rò rỉ nước", "hố ga", "biển báo hỏng", "lan can hỏng"
        );
        if (trungBinhKeywords.stream().anyMatch(content::contains)) {
            return DoUuTien.TRUNG_BINH;
        }

        return DoUuTien.THAP;
    }


}
