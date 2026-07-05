package com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ThongKeLoaiItem {
    private String tenLoai;
    private long soLuong;
}
