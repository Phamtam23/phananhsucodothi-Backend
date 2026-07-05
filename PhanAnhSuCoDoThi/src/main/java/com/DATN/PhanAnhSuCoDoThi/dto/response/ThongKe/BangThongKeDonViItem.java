package com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BangThongKeDonViItem {
    private String tenDonVi;
    private long tongSuCo;
    private long dangXuLy;
    private long hoanThanh;
    private double tiLeHoanThanh;
    private double tiLeDanhGiaTot;
    private double tiLeMoLai;
}
