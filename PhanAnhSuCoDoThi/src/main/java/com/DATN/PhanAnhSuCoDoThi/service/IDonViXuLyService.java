package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy.CreateDonViXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy.UpdateDonViXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLyResponse;

import java.util.List;

public interface IDonViXuLyService {

    DonViXuLyResponse create (CreateDonViXuLyRequest donViXuLyRequest);

    DonViXuLyResponse findById(String maDonVi);

    DonViXuLyResponse update(String maDonVi, UpdateDonViXuLyRequest updateDonViXuLyRequest);

    List<DonViXuLyResponse> findAll();

    Void delete(String maDonVi);
}
