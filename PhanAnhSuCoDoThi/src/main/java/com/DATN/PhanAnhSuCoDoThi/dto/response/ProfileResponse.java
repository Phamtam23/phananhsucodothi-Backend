package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Data
public class ProfileResponse {
    private String maTaiKhoan;
    private String email;
    private String hoTen;
    private String soDienThoai;
    private String cccd;
    private String diaChi;
    private String anhDaiDien;
    private LocalDate ngayBatDau;
    private String chucVu;
}
