package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    private String accessToken;
    private String maTaiKhoan;
    private String email;
    private String hoTen;
    private String soDienThoai;
    private String cccd;
    private String diaChi;
    private String anhDaiDien;
    private String role;
}
