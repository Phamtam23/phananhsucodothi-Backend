package com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDate;

@Data
@Builder
@Getter
public class NhanVienDonViResponse {
    String maNhanVien;
    String hoTen;
    LocalDate ngaySinh;
    String anhDaiDien;
}
