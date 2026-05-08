package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.mapper.SucoMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.SucoRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.TepSuCoRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.NguoidanRepository;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.DATN.PhanAnhSuCoDoThi.util.IdGenerator.generateMaSuCo;
import static com.DATN.PhanAnhSuCoDoThi.util.IdGenerator.generateMaTep;

@Service
@RequiredArgsConstructor
public class SucoService implements ISucoService {

    private final SucoRepository sucoRepository;
    private final TepSuCoRepository tepSuCoRepository;
    private final SucoMapper sucoMapper;
    private final NguoidanRepository nguoidanRepository;
    private final PhieuPhanCongRepository phieuPhanCongRepository;

    @Override
    public PageResponse<SucoResponse> findAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAll(pageable);

        Page<SucoResponse> mapper = pageResult.map(suco ->
                sucoMapper.toResponse(
                        suco,null,null
        ) );

        return PageResponse.of(mapper);
    }

    @Override
    public SucoResponse findById(String maSuco) {

        SucoEntity sucoEntity;
        sucoEntity = sucoRepository.findById(maSuco)
                .orElseThrow(()-> new RuntimeException("Khong tim thấy sự cố"));
        List<TepSuCoEntity> listTepSuCo = tepSuCoRepository.findBySuCo_MaSuCo(sucoEntity.getMaSuCo());

        List<PhieuPhanCongEntity> listPhieuPhanCong = phieuPhanCongRepository.findAllBySuCo_MaSuCo(sucoEntity.getMaSuCo());
        return sucoMapper.toResponse(sucoEntity, listTepSuCo,  listPhieuPhanCong);
    }
    @Override
    public SucoResponse create(CreateSucoRequest request, String maNguoiDan) {

        // 1. tìm người dân
        NguoidanEntity nguoiDan =nguoidanRepository.findById (maNguoiDan)
                .orElseThrow(() -> new RuntimeException("Người dân không tồn tại"));

        // 2. tạo sự cố
        SucoEntity suco = new SucoEntity();
        suco.setMaSuCo(generateMaSuCo(maNguoiDan));
        suco.setNguoiDan(nguoiDan);
        suco.setKinhDo(request.getKinhDo());
        suco.setViDo(request.getViDo());
        suco.setDiaDiem(request.getDiaDiem());
        suco.setNoiDung(request.getNoiDung());

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

        // 5. trả response
        return sucoMapper.toResponse(suco, tepList, List.of());
    }

    @Override
    public PageResponse<SucoResponse> findByNguoiDan(String maNguoiDan, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        Page<SucoEntity> pageResult = sucoRepository.findAllByNguoiDan_MaNguoiDan(maNguoiDan,pageable);

        Page<SucoResponse> mapper = pageResult.map(suco ->
                sucoMapper.toResponse(
                        suco,null,null
                ) );

        return PageResponse.of(mapper);
    }
}
