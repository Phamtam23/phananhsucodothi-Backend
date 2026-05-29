package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLyResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLySCResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.DonViXuLyEntity;
import org.springframework.stereotype.Component;

@Component
public class DonViXuLyMapper {
    public DonViXuLyResponse toResponse(DonViXuLyEntity donViXuLyEntity) {

        if (donViXuLyEntity == null) return null;

      return   DonViXuLyResponse.builder()
                .maDonViXuLy(donViXuLyEntity.getMaDonViXuLy())
                .moTa(donViXuLyEntity.getMoTa())
                .tenDonVi(donViXuLyEntity.getTenDonVi())
                .email(donViXuLyEntity.getEmail())
                .sdt(donViXuLyEntity.getSdt())
                .diaChi(donViXuLyEntity.getDiaChi())
                .khuVuc(donViXuLyEntity.getKhuVuc())
                .trangThai(donViXuLyEntity.getTrangThai())
                .build();
    }

    public DonViXuLySCResponse toResponseSC(DonViXuLyEntity donViXuLyEntity) {

        if (donViXuLyEntity == null) return null;

        return   DonViXuLySCResponse.builder()
                .maDonViXuLy(donViXuLyEntity.getMaDonViXuLy())
                .tenDonVi(donViXuLyEntity.getTenDonVi())
                .build();
    }
}
