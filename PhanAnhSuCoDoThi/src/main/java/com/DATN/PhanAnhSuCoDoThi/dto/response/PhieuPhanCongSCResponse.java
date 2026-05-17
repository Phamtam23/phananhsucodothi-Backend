package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
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
public class PhieuPhanCongSCResponse {
    private String maPhieuPhanCong;

    private DonViXuLySCResponse donViXuLy;

    private  String maNhanVienDieuPhoi;

    private TrangThaiPhanCong trangThai;

    private LocalDateTime thoiGianTao;

    private String maSuCo;

    private KetQuaXuLyDetailResponse ketQuaXuLyDetailResponse;

   private PhieuTrangThaiResponse phieuTrangThaiResponse;
}
