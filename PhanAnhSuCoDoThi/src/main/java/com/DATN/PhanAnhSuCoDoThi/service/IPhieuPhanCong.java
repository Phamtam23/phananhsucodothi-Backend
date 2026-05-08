package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;

import java.util.List;

public interface IPhieuPhanCong {

    PhieuPhanCongResponse create(CreatePhieuPhanCongRequest createPhieuPhanCongRequest, String maNhanVienDieuPhoi);

    PhieuPhanCongResponse update(String maPhieuPhanCong,UpdatePhieuPhanCongRequest updatePhieuPhanCongRequest);

    PhieuPhanCongResponse findById(String ma);

    PageResponse<PhieuPhanCongResponse> findAllByDonVi(String maDonVi, int page, int size );

}
