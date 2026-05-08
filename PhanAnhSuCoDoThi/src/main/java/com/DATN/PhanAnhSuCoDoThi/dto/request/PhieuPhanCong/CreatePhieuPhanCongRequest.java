package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhieuPhanCongRequest {
    @NotBlank
    private String maSuCo;

    @NotBlank
    private String maDonViXuLy;

    private String ghiChu;
}
