package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.service.ILoaiService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loai")
@RequiredArgsConstructor
public class LoaiController {

    private final ILoaiService loaiService;

    @PostMapping
    public ApiSuccessResponse<LoaiResponse> create(
            @RequestParam
            String tenLoai
    ) {

        return ApiSuccessResponse.created(loaiService.create(tenLoai));
    }

    @PutMapping("/{maLoai}")
    public ApiSuccessResponse<LoaiResponse> update(
            @PathVariable
            String maLoai,
            @RequestParam
            String tenLoai
    ) {

        return ApiSuccessResponse.ok(loaiService.update(maLoai, tenLoai));
    }

//    @DeleteMapping("/{maLoai}")
//    public ApiSuccessResponse<Void> delete(
//            @PathVariable
//            String maLoai
//    ) {
//
//        loaiService.delete(maLoai);
//
//        return ApiSuccessResponse.ok()
//    }

    @GetMapping("/{maLoai}")
    public ApiSuccessResponse<LoaiResponse> findById(
            @PathVariable
            String maLoai
    ) {

        return ApiSuccessResponse.ok(loaiService.findById(maLoai));
    }

    @GetMapping
    public ApiSuccessResponse<List<LoaiResponse>> findAll() {

        return ApiSuccessResponse.ok(loaiService.findAll());
    }
}