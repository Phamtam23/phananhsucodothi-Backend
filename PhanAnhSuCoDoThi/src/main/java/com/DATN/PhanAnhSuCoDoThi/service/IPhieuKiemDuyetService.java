package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuKiemDuyet.CreatePhieuKiemDuyetRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuKiemDuyetResponse;

import java.util.List;

public interface IPhieuKiemDuyetService {

    PhieuKiemDuyetResponse create(CreatePhieuKiemDuyetRequest request);

    PhieuKiemDuyetResponse getByMa(String maKiemDuyet);

    List<PhieuKiemDuyetResponse> getByMaSuCo(String maSuCo);

}
