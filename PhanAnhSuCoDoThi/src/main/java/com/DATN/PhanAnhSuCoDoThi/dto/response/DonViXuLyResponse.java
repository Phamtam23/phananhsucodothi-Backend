package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiDonVi;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonViXuLyResponse {

    private String maDonViXuLy;

    private String tenDonVi;

    private String khuVuc;

    private String moTa;

    private String diaChi;

    private String sdt;

    private String email;

    private TrangThaiDonVi trangThai;
}