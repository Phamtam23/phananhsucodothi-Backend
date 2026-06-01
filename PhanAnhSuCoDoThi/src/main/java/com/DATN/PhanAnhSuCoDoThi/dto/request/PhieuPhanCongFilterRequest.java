package com.DATN.PhanAnhSuCoDoThi.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PhieuPhanCongFilterRequest {
    private int page = 0;
    private int size = 10;
    private LocalDate tuNgay;
    private LocalDate denNgay;
    private String maDonVi;
    private String maLoai;
}
