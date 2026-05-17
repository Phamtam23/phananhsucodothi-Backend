package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Builder
@Data
public class NhatKyResponse {
    private String maTaiKhoan;
    private SucoSummaryResponse sucoResponse;
    private TrangThaiSuCo hanhDong;
    private LocalDateTime thoiGianTao;
}
