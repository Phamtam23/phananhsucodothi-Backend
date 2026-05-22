package com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateTaiKhoanRequest {
    private String hoTen;
    private String soDienThoai;
    private String diaChi;
    private String vaiTro;
}
