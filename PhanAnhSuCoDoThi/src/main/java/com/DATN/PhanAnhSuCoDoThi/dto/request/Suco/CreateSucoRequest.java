package com.DATN.PhanAnhSuCoDoThi.dto.request.Suco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateSucoRequest {
    @NotNull
    private String maNguoiDan;
    @NotBlank
    private Double kinhDo;
    @NotBlank
    private Double viDo;
    @NotBlank
    private String diaDiem;
    @NotBlank
    private String noiDung;
    @NotBlank
    private List<String> mediaUrls;
}
