package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuDanhGiaRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuDanhGiaResponse;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IPhieuDanhGiaService {

    PhieuDanhGiaResponse create (PhieuDanhGiaRequest phieuDanhGiaRequest);

   List<PhieuDanhGiaResponse> findByKetQuaXuLy(String maKetQuaXyLy);

}
