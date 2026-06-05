package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.SuCoFilterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.UpdateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;

public interface ISucoService {

    PageResponse<SucoSummaryResponse> findAll(SuCoFilterRequest filter, int page, int size);

    PageResponse<SucoSummaryResponse> findForMap(String maLoai, TrangThaiSuCo trangThai, int page, int size);

    SucoDetailResponse findById(String id, String maNguoiDan);

    SucoDetailResponse create(CreateSucoRequest createSucoRequest, String maNguoiDan);

    SucoDetailResponse update(UpdateSucoRequest updateSucoRequest, String maNguoiDan);

    PageResponse<SucoSummaryResponse> findByNguoiDan(String maNguoiDan,SuCoFilterRequest filter, int page, int size);

}
