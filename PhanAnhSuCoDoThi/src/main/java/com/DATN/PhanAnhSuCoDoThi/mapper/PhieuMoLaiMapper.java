package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuMoLai.PhieuMoLaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuMoLaiEntity;
import org.springframework.stereotype.Component;

@Component
public class PhieuMoLaiMapper {
   public PhieuMoLaiResponse toResponse(PhieuMoLaiEntity phieuMoLaiEntity) {
       return PhieuMoLaiResponse.builder()
               .maPhieuMoLai(phieuMoLaiEntity.getMaPhieuMoLai())
               .lyDo(phieuMoLaiEntity.getLyDo())
               .maKetQuaXuLy(phieuMoLaiEntity.getKetQuaXuLy().getMaKetQua())
               .trangThaiMoLai(phieuMoLaiEntity.getTrangThaiMoLai())
               .thoiGianTao(phieuMoLaiEntity.getThoiGianTao())
               .build();
   }
}
