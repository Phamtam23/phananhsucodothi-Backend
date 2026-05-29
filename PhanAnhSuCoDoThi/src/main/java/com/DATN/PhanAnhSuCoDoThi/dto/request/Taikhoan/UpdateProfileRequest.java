package com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProfileRequest {
    private String soDienThoai;
    private String diaChi;
    private String email;
    private String anhDaiDien;
    private String hoTen;
}
