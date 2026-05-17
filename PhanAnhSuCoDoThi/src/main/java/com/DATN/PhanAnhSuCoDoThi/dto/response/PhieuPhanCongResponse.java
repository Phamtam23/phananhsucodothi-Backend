package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
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

    private String maSuCo;

    private String maDonViXuLy;

    private  String maNhanVienDieuPhoi;

    private TrangThaiPhanCong trangThai;

    private String ghiChu;

    private String lyDoTuChoi;

    private LocalDateTime thoiGianTao;

}


