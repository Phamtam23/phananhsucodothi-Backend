package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiMoLai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdatePhieuMoLai {
    String maPhieuMoLai;
    TrangThaiMoLai trangThaiMoLai;
}
