package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class PhieuPhanLoaiResponse {

    private String maSuCo;

    private List<LoaiResponse> dsLoai;

    private LocalDateTime thoiGianPhanLoai;
}
