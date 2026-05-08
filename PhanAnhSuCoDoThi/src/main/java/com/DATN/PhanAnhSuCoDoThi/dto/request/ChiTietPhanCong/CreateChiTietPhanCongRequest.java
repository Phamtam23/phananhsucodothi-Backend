package com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChiTietPhanCongRequest {
    private String maPhieuPhanCong;

    private String maNhanVienXuLy;

    private TrangThaiChiTietPhanCong trangThai;

    private LocalDateTime thoiGianTao;
}
