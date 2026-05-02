package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;

import java.util.List;

public interface INguoiDanService {

    PageResponse<SucoResponse> findAll(int page, int size);
}
