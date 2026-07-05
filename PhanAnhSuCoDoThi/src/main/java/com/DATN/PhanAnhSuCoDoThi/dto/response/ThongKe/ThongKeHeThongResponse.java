package com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class
ThongKeHeThongResponse {

    private long tongSoSuCo;
    private long tongSuCoTrongNam;
    private long tongSuCoTrongThang;
    
    private long suCoChuaXuLy;
    private long suCoDangXuLy;
    private long suCoDaXuLy;
    private long tongSoTaiKhoan;
    private long tongSoDonVi;
    private long tongSoLoai;

    private List<ThongKeLoaiItem> suCoTheoLoai;
    private List<ThongKeTrangThaiItem> suCoTheoTrang;
    private List<ThongKeThangItem> suCoTheoThang;

    private List<BangThongKeDonViItem> bangThongKeDonVi;

    private List<BieuDoDonViItem> bieuDoDonVi;



}
