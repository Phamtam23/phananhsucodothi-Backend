package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
@Builder
@Getter
public class ChiTietPhanCongResponse {

    private String maChiTietPhanCong;

    private PhieuPhanCongEntity phieuPhanCong;

    private NhanVienDonViEntity nhanVienXuLy;

    private TrangThaiChiTietPhanCong trangThai;

    private LocalDateTime thoiGianTao;
}
