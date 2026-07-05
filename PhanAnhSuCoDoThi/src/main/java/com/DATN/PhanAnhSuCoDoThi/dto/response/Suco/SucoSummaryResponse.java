package com.DATN.PhanAnhSuCoDoThi.dto.response.Suco;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SucoSummaryResponse extends SucoBaseResponse {

    private String thumbnail;
}
