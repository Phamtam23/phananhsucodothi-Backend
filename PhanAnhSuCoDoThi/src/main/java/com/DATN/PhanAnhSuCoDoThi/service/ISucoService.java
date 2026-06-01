package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;

public interface ISucoService {

    PageResponse<SucoSummaryResponse> findAll(int page, int size);

    SucoDetailResponse findById(String id, String maNguoiDan);

    SucoDetailResponse create(CreateSucoRequest createSucoRequest, String maNguoiDan);

    PageResponse<SucoSummaryResponse> findByNguoiDan(String maNguoiDan, int page, int size);


    PageResponse<SucoSummaryResponse> findByTrangThai(int page, int size, TrangThaiSuCo trangThaiSuCo);
}
