package com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UpdateChiTietPhanCongRequest {
    private TrangThaiChiTietPhanCong trangThai;
}
