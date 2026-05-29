package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NhanVienDieuPhoiResponse {

    private String maNhanVienDieuPhoi;

    private TaiKhoanResponse taiKhoan;

    private String trangThai;

    private LocalDate ngayBatDau;

    private LocalDate ngaySinh;

    private LocalDate ngayKetThuc;
}