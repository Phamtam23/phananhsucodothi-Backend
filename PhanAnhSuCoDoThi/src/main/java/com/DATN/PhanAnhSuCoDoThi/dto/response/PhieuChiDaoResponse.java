package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Builder
@Getter
public class PhieuChiDaoResponse {
    private String maChiDao;

    private ChiTietPhanCongEntity chiTietPhanCong;

    private NhanVienDonViEntity truongDonVi;

    private String noiDung;

    private LocalDate ngayChiDao;
}
