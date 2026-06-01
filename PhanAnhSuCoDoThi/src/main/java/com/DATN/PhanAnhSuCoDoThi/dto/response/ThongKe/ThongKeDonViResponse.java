package com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThongKeDonViResponse {
    private long tongSuCoTatCa;
    private long tongSuCoTrongNam;
    private List<ThongKeThangItem> suCoTheoThang;
    private List<ThongKeNhanVienItem> suCoNhanVien;
}
