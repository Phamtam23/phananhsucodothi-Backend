package com.DATN.PhanAnhSuCoDoThi.dto.request.Suco;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateSucoRequest {
    private String maSuCo;
    private String noiDung;
    private List<String> mediaUrls;
}
