package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;

import java.util.List;

public interface ISucoService {

    List<SucoResponse> findAll();

    SucoResponse findById(String id);

    SucoResponse create(CreateSucoRequest createSucoRequest);

}
