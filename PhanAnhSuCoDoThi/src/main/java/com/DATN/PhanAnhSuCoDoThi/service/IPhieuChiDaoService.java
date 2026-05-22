package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.CreateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.UpdateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuChiDaoResponse;
import org.springframework.data.domain.Page;

public interface IPhieuChiDaoService {

    PhieuChiDaoResponse create (CreateChiDaoRequest createChiDaoRequest,String maTruongDonVi);

    PhieuChiDaoResponse update (String maChiDao, UpdateChiDaoRequest updateChiDaoRequest);

    String deleteById (String maChiDao);

    PhieuChiDaoResponse findById (String maChiDao);

    Page<PhieuChiDaoResponse> findByChiTietPhanCong (String maChiTietPhanCong , int page, int size);
}
