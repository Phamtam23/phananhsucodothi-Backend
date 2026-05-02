package com.DATN.PhanAnhSuCoDoThi.dto.response;

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

    private String trangThai;
}