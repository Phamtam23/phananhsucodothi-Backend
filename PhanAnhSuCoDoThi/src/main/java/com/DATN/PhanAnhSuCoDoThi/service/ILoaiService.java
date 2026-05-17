package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;

import java.util.List;

public interface ILoaiService {

    LoaiResponse create (String tenLoai);

    LoaiResponse update (String tenLoai,String maLoai);

    Void delete(String maLoai);

    LoaiResponse findById(String maLoai);

    List<LoaiResponse> findAll();
}
