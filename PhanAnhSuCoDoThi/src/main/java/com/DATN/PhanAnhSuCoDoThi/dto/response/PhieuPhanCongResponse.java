package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PhieuPhanCongResponse {
    private String maPhieuPhanCong;

    private SucoDetailResponse suCo;

    private DonViXuLyResponse donViXuLy;

    private  NhanVienDieuPhoiResponse nhanVienDieuPhoi;

    private String trangThai;

    private String ghiChu;

    private String lyDoTuChoi;

    private LocalDateTime thoiGianTao;

}


