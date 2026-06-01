package com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BieuDoDonViItem {
    private String tenDonVi;
    private long soLuong;
}
