package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoaiResponse {
    private String maLoai;

    private String tenLoaiSuCo;
}
