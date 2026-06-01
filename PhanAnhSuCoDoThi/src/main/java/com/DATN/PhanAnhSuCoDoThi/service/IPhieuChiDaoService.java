package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.CreateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.UpdateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuChiDaoResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IPhieuChiDaoService {

    PhieuChiDaoResponse create (CreateChiDaoRequest createChiDaoRequest,String maTruongDonVi);

    PhieuChiDaoResponse update (String maChiDao, UpdateChiDaoRequest updateChiDaoRequest);

    String deleteById (String maChiDao);

    PhieuChiDaoResponse findById (String maChiDao);

    List<PhieuChiDaoResponse> findByChiTietPhanCong(String maChiTietPhanCong) ;
}
