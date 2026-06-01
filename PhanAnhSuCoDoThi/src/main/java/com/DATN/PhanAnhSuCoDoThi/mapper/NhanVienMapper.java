package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDieuPhoiResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDieuPhoiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import org.springframework.stereotype.Component;

@Component
public class NhanVienMapper {
    public NhanVienDieuPhoiResponse toResponseNhanVienDieuPhoi(NhanVienDieuPhoiEntity e) {
        if (e == null) return null;

        return NhanVienDieuPhoiResponse.builder()
                .maNhanVienDieuPhoi(e.getMaNhanVienDieuPhoi())
                .trangThai(e.getTrangThai())
                .ngayBatDau(e.getNgayBatDau())
                .ngaySinh(e.getNgaySinh())
                .ngayKetThuc(e.getNgayKetThuc())
                .build();
    }

    public NhanVienDonViResponse toResponseNhanVienDonVi(NhanVienDonViEntity e) {
        if (e == null) return null;

        return NhanVienDonViResponse.builder()
                .hoTen(e.getTaiKhoan().getHoTen())
                .anhDaiDien(e.getTaiKhoan().getAnhDaiDien())
                .maNhanVien(e.getMaNhanVien())
                .ngaySinh(e.getNgaySinh())
                .build();
    }
}
