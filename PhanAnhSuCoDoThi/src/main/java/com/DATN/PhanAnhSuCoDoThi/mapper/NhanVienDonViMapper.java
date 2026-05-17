package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import org.springframework.stereotype.Component;

@Component
public class NhanVienDonViMapper {

  public NhanVienDonViResponse toResponsePC (NhanVienDonViEntity nhanVienDonViEntity){
     return NhanVienDonViResponse.builder()
                .hoTen(nhanVienDonViEntity.getTaiKhoan().getHoTen())
                .maNhanVien(nhanVienDonViEntity.getMaNhanVien())
                .ngaySinh(nhanVienDonViEntity.getNgaySinh())
                .anhDaiDien(nhanVienDonViEntity.getTaiKhoan().getAnhDaiDien())
                .build();
    }
}
