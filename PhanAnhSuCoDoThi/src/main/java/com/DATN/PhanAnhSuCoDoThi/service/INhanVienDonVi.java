package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;

import java.util.List;

public interface INhanVienDonVi {
    List<NhanVienDonViResponse> findAllToPhanCong(String maTruongDonVi);
    List<NhanVienDonViResponse> findAllByDonVi(String maDonVi);
}
