package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanLoaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanLoaiResponse;

import java.util.List;

public interface IPhanLoaiService {
    PhieuPhanLoaiResponse create(PhieuPhanLoaiRequest request);

    void delete(String maSuCo, String maLoai);

    List<LoaiResponse> getByMaSuCo(String maSuCo);
}
