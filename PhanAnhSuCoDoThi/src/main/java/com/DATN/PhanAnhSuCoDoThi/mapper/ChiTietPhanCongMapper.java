package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import org.springframework.stereotype.Component;

@Component
public class ChiTietPhanCongMapper {

    public ChiTietPhanCongResponse toResponse(ChiTietPhanCongEntity chiTietPhanCongEntity, PhieuPhanCongResponse phieuPhanCongResponse, NhanVienDonViResponse nhanVienDonViResponse) {

        return ChiTietPhanCongResponse.builder()
                .maChiTietPhanCong(chiTietPhanCongEntity.getMaChiTietPhanCong())
                .phieuPhanCong(phieuPhanCongResponse)
                .nhanVienXuLy(nhanVienDonViResponse)
                .trangThai(chiTietPhanCongEntity.getTrangThai())
                .thoiGianTao(chiTietPhanCongEntity.getThoiGianTao())
                .build();
    }
}
