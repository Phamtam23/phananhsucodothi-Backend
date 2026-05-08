package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuPhanCongSCRespomse {
    private String maPhieuPhanCong;

    private DonViXuLyResponse donViXuLy;

    private  NhanVienDieuPhoiResponse nhanVienDieuPhoi;

    private String trangThai;

    private LocalDateTime thoiGianTao;

    private SucoSummaryResponse suCo;
}
