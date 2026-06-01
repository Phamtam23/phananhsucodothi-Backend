package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.CreateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.UpdateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLySummaryResponse;

import java.util.List;

public interface IKetQuaXuLyService {

  List<KetQuaXuLyDetailResponse> findByChiTietPhanCong(String maChiTietPhanCong);

  KetQuaXuLyDetailResponse create(CreateKetQuaXuLyRequest createKetQuaXuLyRequest);

  KetQuaXuLyDetailResponse findById(String maKetQuaXuLyId);

  KetQuaXuLyDetailResponse update(UpdateKetQuaXuLyRequest updateKetQuaXuLyRequest);

  KetQuaXuLyDetailResponse duyetKetQua(String maKetQua, boolean isApproved, String lyDoTuChoi);
 }
