package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCRespomse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PhieuPhanCongMapper {

    private final NhanVienMapper nhanVienMapper;
    private final SucoMapper sucoMapper;
    private final DonViXuLyMapper donViXuLyMapper;
    private final MediaMapper mediaMapper;

    public PhieuPhanCongSCRespomse toSummary(
            PhieuPhanCongEntity e,
            List<TepSuCoEntity> medias
    ) {
        if (e == null) return null;

        return PhieuPhanCongSCRespomse.builder()
                .maPhieuPhanCong(e.getMaPhieuPhanCong())
                .trangThai(e.getTrangThai())
                .thoiGianTao(e.getThoiGianTao())

                .nhanVienDieuPhoi(
                        nhanVienMapper.toResponseNhanVienDieuPhoi(e.getNhanVienDieuPhoi())
                )

                .donViXuLy(
                        donViXuLyMapper.toResponse(e.getDonViXuLy())
                )
                .suCo(
                        sucoMapper.toSummary(
                                e.getSuCo(),
                                medias
                        )
                )
                .build();
    }

    public PhieuPhanCongResponse toDetail(
            PhieuPhanCongEntity e,
            List<TepSuCoEntity> medias
    ) {
        if (e == null) return null;

        return PhieuPhanCongResponse.builder()
                .maPhieuPhanCong(e.getMaPhieuPhanCong())
                .trangThai(e.getTrangThai())
                .ghiChu(e.getGhiChu())
                .lyDoTuChoi(e.getLyDoTuChoi())
                .thoiGianTao(e.getThoiGianTao())

                .nhanVienDieuPhoi(
                        nhanVienMapper.toResponseNhanVienDieuPhoi(e.getNhanVienDieuPhoi())
                )

                .donViXuLy(
                        donViXuLyMapper.toResponse(e.getDonViXuLy())
                )

                .suCo(
                        sucoMapper.toDetail(
                                e.getSuCo(),
                                medias,
                                List.of()
                        )
                )
                .build();
    }

    public List<PhieuPhanCongSCRespomse> toSummaryList(
            List<PhieuPhanCongEntity> list,
            Map<String, List<TepSuCoEntity>> mediaMap
    ) {
        if (list == null) return List.of();

        return list.stream()
                .map(p -> toSummary(
                        p,
                        mediaMap.getOrDefault(
                                p.getSuCo().getMaSuCo(),
                                List.of()
                        )
                ))
                .toList();
    }
}