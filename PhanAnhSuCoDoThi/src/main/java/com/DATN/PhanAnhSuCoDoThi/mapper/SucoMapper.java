package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCRespomse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SucoMapper {
    private final NhanVienMapper nhanVienMapper;
    private final DonViXuLyMapper donViXuLyMapper;
    private final PhieuPhanCongMapper phieuPhanCongMapper;


    public SucoSummaryResponse toSummary(SucoEntity e, List<TepSuCoEntity> medias) {
        if (e == null) return null;

        return SucoSummaryResponse.builder()
                .maSuCo(e.getMaSuCo())
                .noiDung(e.getNoiDung())
                .diaDiem(e.getDiaDiem())
                .trangThai(e.getTrangThai() != null ? e.getTrangThai().name() : null)
                .thoiGianTao(e.getThoiGianTao())
                .thumbnail(getThumbnail(medias))
                .build();
    }

    public SucoDetailResponse toDetail(
            SucoEntity e,
            List<TepSuCoEntity> medias,
            List<PhieuPhanCongEntity> phieus
    ) {
        if (e == null) return null;

        return SucoDetailResponse.builder()
                .maSuCo(e.getMaSuCo())
                .noiDung(e.getNoiDung())
                .diaDiem(e.getDiaDiem())
                .trangThai(e.getTrangThai() != null ? e.getTrangThai().name() : null)
                .thoiGianTao(e.getThoiGianTao())

                .maNguoiDan(e.getNguoiDan() != null ? e.getNguoiDan().getMaNguoiDan() : null)
                .kinhDo(e.getKinhDo())
                .viDo(e.getViDo())
                .ngayDuKienHoanThanh(e.getNgayDuKienHoanThanh())
                .medias(mapMedia(medias))
                .phieuPhanCongs(phieuPhanCongMapper(phieus))
                .build();
    }

    private String getThumbnail(List<TepSuCoEntity> medias) {
        if (medias == null || medias.isEmpty()) {
            return null;
        }

        return medias.get(0).getUrl();
    }


    public SucoResponse toResponse(SucoEntity sucoEntity, List<TepSuCoEntity> tepSuCoEntity , List<PhieuPhanCongEntity> phieuPhanCongEntitys) {
        if (sucoEntity == null) return null;
        return SucoResponse.builder()
                .maSuCo(sucoEntity.getMaSuCo())
                .maNguoiDan(
                        sucoEntity.getNguoiDan() != null
                                ? sucoEntity.getNguoiDan().getMaNguoiDan()
                                : null
                )
                .kinhDo(sucoEntity.getKinhDo())
                .viDo(sucoEntity.getViDo())
                .diaDiem(sucoEntity.getDiaDiem())
                .noiDung(sucoEntity.getNoiDung())
                .trangThai(
                        sucoEntity.getTrangThai() != null
                                ? sucoEntity.getTrangThai().name()
                                : null
                )
                .ngayDuKienHoanThanh(sucoEntity.getNgayDuKienHoanThanh())
                .thoiGianTao(sucoEntity.getThoiGianTao())

                // media
                .medias(mapMedia(tepSuCoEntity))

                .phieuPhanCongs(mapPhieuPhanCongs(phieuPhanCongEntitys))
                .build();

    }



    private static List<MediaResponse> mapMedia(List<TepSuCoEntity> medias) {
        if (medias == null) return null;

        return medias.stream()
                .map(m -> MediaResponse.builder()
                        .url(m.getUrl())
                        .type(m.getLoai())
                        .build()
                )
                .collect(Collectors.toList());
    }

    private List<PhieuPhanCongSCRespomse> mapPhieuPhanCongs(List<PhieuPhanCongEntity> phieuPhanCongs) {
        if (phieuPhanCongs == null) return null;

        return phieuPhanCongs.stream()
                .map(m -> PhieuPhanCongSCRespomse.builder()
                        .maPhieuPhanCong(m.getMaPhieuPhanCong())
                        .nhanVienDieuPhoi(
                                nhanVienMapper.toResponseNhanVienDieuPhoi(m.getNhanVienDieuPhoi())
                        )
                        .donViXuLy(
                                donViXuLyMapper.toResponse(m.getDonViXuLy())
                        )
                        .trangThai(m.getTrangThai())
                        .thoiGianTao(m.getThoiGianTao())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
