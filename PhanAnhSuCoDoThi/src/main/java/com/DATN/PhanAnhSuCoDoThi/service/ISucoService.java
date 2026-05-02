package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;

import java.util.List;

public interface ISucoService {

    PageResponse<SucoResponse> findAll(int page, int size);

    SucoResponse findById(String id);

    SucoResponse create(CreateSucoRequest createSucoRequest, String maNguoiDan);

    PageResponse<SucoResponse> findByNguoiDan(String maNguoiDan, int page, int size);
}
