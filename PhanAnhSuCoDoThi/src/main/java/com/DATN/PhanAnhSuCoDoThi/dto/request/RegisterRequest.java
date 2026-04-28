package com.DATN.PhanAnhSuCoDoThi.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
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
}
