package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuDanhGiaResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuChiDaoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuDanhGiaEntity;
import org.springframework.stereotype.Component;

@Component
public class PhieuDanhGiaMapper {

    public PhieuDanhGiaResponse toResponse(PhieuDanhGiaEntity phieuDanhGiaEntity) {
       return PhieuDanhGiaResponse.builder()
               .maXuLyKetQua(phieuDanhGiaEntity.getKetQuaXuLy().getMaKetQua())
               .mucDoDanhGia(phieuDanhGiaEntity.getMucDoHaiLong())
               .build();
    }
}
