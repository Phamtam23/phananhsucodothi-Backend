package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.AuthResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.TaikhoanEntity;
import org.springframework.stereotype.Component;

@Component
public class AuthMapper {

    public AuthResponse toResponse(TaikhoanEntity tk, String token) {
        if (tk == null) return null;

        return AuthResponse.builder()
                .accessToken(token)
                .maTaiKhoan(tk.getMaTaiKhoan())
                .email(tk.getEmail())
                .hoTen(tk.getHoTen())
                .soDienThoai(tk.getSoDienThoai())
                .cccd(tk.getCccd())
                .diaChi(tk.getDiaChi())
                .anhDaiDien(tk.getAnhDaiDien())
                .build();
    }
}
