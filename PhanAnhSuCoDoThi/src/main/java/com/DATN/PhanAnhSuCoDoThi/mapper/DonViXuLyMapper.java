package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLyResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.DonViXuLyEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import org.springframework.stereotype.Component;

@Component
public class DonViXuLyMapper {
    public DonViXuLyResponse toResponse(DonViXuLyEntity donViXuLyEntity) {

        if (donViXuLyEntity == null) return null;

      return   DonViXuLyResponse.builder()
                .maDonViXuLy(donViXuLyEntity.getMaDonViXuLy())
                .moTa(donViXuLyEntity.getMoTa())
                .email(donViXuLyEntity.getEmail())
                .sdt(donViXuLyEntity.getSdt())
                .diaChi(donViXuLyEntity.getDiaChi())
                .khuVuc(donViXuLyEntity.getKhuVuc())
                .build();
    }
}
