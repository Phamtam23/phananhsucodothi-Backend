package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaiKhoanResponse {
    private String maTaiKhoan;
    private String email;
    private String hoTen;
    private String soDienThoai;
    private String cccd;
    private String diaChi;
    private String anhDaiDien;
    private String vaiTro;
    private String trangThai;
}
