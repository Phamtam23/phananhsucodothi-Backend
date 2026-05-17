package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
@Data
public class ChiTietPhanCongResponse {

    private String maChiTietPhanCong;

    private PhieuPhanCongResponse phieuPhanCong;

    private NhanVienDonViResponse nhanVienXuLy;

    private TrangThaiChiTietPhanCong trangThai;

    private LocalDateTime thoiGianTao;
}
