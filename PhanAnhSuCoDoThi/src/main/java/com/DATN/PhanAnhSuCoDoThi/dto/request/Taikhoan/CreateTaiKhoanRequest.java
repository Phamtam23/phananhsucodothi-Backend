package com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateTaiKhoanRequest {

    @NotBlank
    @Email
    private String email;

    @NotBlank
    @Size(min = 6)
    private String matKhau;

    @NotBlank
    private String hoTen;

    @NotBlank
    @Size(min = 10, max = 10)
    private String soDienThoai;

    @NotBlank
    @Size(min = 12, max = 12)
    private String cccd;

    private String diaChi;

    // vaiTro: R_USER, R_NVXULY, R_TXULY, R_DIEUPHOI, R_Admin
    @NotBlank
    private String vaiTro;

    private String maDonVi;
}
