package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCongFilterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongLSResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCResponse;

import java.time.LocalDate;
import java.util.List;

public interface IPhieuPhanCong {

    List<PhieuPhanCongResponse> create(CreatePhieuPhanCongRequest createPhieuPhanCongRequest, String maNhanVienDieuPhoi);

    PhieuPhanCongResponse update(String maPhieuPhanCong,UpdatePhieuPhanCongRequest updatePhieuPhanCongRequest);

    PhieuPhanCongResponse findById(String ma);

    PageResponse<PhieuPhanCongResponse> findAllByDonVi(String maDonVi, int page, int size );

    List<PhieuPhanCongSCResponse> findAllBySuCo(String maSuCo, String maNguoiDan);

    PageResponse<PhieuPhanCongLSResponse> findAllByNhanVien(String maNhanVien, PhieuPhanCongFilterRequest request);
}
