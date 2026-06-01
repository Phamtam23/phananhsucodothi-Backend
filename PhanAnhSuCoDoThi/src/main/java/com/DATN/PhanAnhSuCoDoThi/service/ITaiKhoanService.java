package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.CreateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.UpdateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.TaiKhoanResponse;

import java.util.List;

public interface ITaiKhoanService {

    List<TaiKhoanResponse> findAll();

    TaiKhoanResponse findById(String maTaiKhoan);

    TaiKhoanResponse create(CreateTaiKhoanRequest request);

    TaiKhoanResponse update(String maTaiKhoan, UpdateTaiKhoanRequest request);

    TaiKhoanResponse khoa(String maTaiKhoan);

    TaiKhoanResponse moKhoa(String maTaiKhoan);
}
