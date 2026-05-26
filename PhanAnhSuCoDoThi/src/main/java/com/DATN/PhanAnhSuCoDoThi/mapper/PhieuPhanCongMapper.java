package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLySCResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuTrangThaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PhieuPhanCongMapper {

    public PhieuPhanCongResponse toResponse(
            PhieuPhanCongEntity e
    ) {
        if (e == null) return null;

        return PhieuPhanCongResponse.builder()
                .maPhieuPhanCong(e.getMaPhieuPhanCong())
                .trangThai(e.getTrangThai())
                .ghiChu(e.getGhiChu())
                .lyDoTuChoi(e.getLyDoTuChoi())
                .thoiGianTao(e.getThoiGianTao())

                .maNhanVienDieuPhoi(
                        e.getNhanVienDieuPhoi().getMaNhanVienDieuPhoi()
                )

                .maDonViXuLy(
                      e.getDonViXuLy().getMaDonViXuLy()
                )

                .maSuCo(
                        e.getSuCo().getMaSuCo()
                )
                .build();
    }


    public PhieuPhanCongSCResponse toResponseSC(
            PhieuPhanCongEntity entity
    ) {

        return PhieuPhanCongSCResponse.builder()
                .maPhieuPhanCong(entity.getMaPhieuPhanCong())
                .maSuCo(entity.getSuCo().getMaSuCo())
                .maNhanVienDieuPhoi(
                        entity.getNhanVienDieuPhoi()
                                .getMaNhanVienDieuPhoi()
                )
                .trangThai(entity.getTrangThai())
                .thoiGianTao(entity.getThoiGianTao())

                .donViXuLy(
                        DonViXuLySCResponse.builder()
                                .maDonViXuLy(
                                        entity.getDonViXuLy().getMaDonViXuLy()
                                )
                                .tenDonVi(
                                        entity.getDonViXuLy().getTenDonVi()
                                )
                                .build()
                )
                .build();
    }


}