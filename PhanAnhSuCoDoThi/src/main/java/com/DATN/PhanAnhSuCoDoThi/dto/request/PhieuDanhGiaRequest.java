package com.DATN.PhanAnhSuCoDoThi.dto.request;

import com.DATN.PhanAnhSuCoDoThi.enums.MucDoDanhGia;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhieuDanhGiaRequest {
    String maKetQuaXuLy;
    MucDoDanhGia mucDoHaiLong;
}
