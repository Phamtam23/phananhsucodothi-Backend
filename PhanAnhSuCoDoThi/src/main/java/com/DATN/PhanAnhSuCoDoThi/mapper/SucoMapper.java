package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCRespomse;
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
