package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreatePhieuPhanCongRequest {
    @NotBlank
    private String maSuCo;

    @NotBlank
    private List<String> maDonViXuLy;

    private String ghiChu;
}
