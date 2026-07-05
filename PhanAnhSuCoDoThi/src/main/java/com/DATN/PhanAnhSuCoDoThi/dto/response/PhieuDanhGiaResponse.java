package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.enums.MucDoDanhGia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhieuDanhGiaResponse {
    String maXuLyKetQua;
    MucDoDanhGia mucDoDanhGia;
}
