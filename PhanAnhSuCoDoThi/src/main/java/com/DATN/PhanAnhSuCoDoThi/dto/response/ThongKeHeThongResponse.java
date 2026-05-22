package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThongKeHeThongResponse {

    private long tongSoSuCo;
    private long suCoChuaXuLy;
    private long suCoDangXuLy;
    private long suCoDaXuLy;
    private long tongSoTaiKhoan;
    private long tongSoDonVi;
    private long tongSoLoai;

    private List<ThongKeLoaiItem> suCoTheoLoai;
    private List<ThongKeTrangThaiItem> suCoTheoTrang;
    private List<ThongKeThangItem> suCoTheoThang;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThongKeLoaiItem {
        private String tenLoai;
        private long soLuong;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThongKeTrangThaiItem {
        private String trangThai;
        private long soLuong;
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ThongKeThangItem {
        private String thang;
        private long soLuong;
    }
}
