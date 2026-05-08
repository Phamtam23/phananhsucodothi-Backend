package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import org.springframework.stereotype.Component;

@Component
public class ChiTietPhanCongMapper {

    public ChiTietPhanCongResponse toResponse(ChiTietPhanCongEntity chiTietPhanCongEntity) {

        return ChiTietPhanCongResponse.builder()
                .maChiTietPhanCong(chiTietPhanCongEntity.getMaChiTietPhanCong())
                .phieuPhanCong(chiTietPhanCongEntity.getPhieuPhanCong())
                .nhanVienXuLy(chiTietPhanCongEntity.getNhanVienXuLy())
                .trangThai(chiTietPhanCongEntity.getTrangThai())
                .thoiGianTao(chiTietPhanCongEntity.getThoiGianTao())
                .build();

    }
}
