package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuChiDaoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuChiDaoEntity;
import org.springframework.stereotype.Component;


@Component
public class PhieuChiDaoMapper {

    public PhieuChiDaoResponse toResponse (PhieuChiDaoEntity  phieuChiDaoEntity) {
        return PhieuChiDaoResponse.builder()
                .maChiDao(phieuChiDaoEntity.getMaChiDao())
                .ngayChiDao(phieuChiDaoEntity.getNgayChiDao())
                .noiDung(phieuChiDaoEntity.getNoiDung())
                .maTruongDonVi(phieuChiDaoEntity.getTruongDonVi().getMaNhanVien())
                .maChiTietPhanCong(phieuChiDaoEntity.getChiTietPhanCong().getMaChiTietPhanCong())
                .build();
    }
}
