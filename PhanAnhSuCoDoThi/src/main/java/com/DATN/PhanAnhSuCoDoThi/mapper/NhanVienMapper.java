package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDieuPhoiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDieuPhoiEntity;
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
}
