package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.CreatePhieuMoLaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.UpdatePhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuMoLai.PhieuMoLaiResponse;

import java.util.List;

public interface IPhieuMoLai {
    PhieuMoLaiResponse create(CreatePhieuMoLaiRequest phieuMoLaiRequest);
    PhieuMoLaiResponse update(UpdatePhieuMoLai  updatePhieuMoLai);
    PhieuMoLaiResponse findById(String maPhieuMoLai);
    List<PhieuMoLaiResponse> findAllByPhanCong(String maNguoiDan, String maPhanCong);
}
