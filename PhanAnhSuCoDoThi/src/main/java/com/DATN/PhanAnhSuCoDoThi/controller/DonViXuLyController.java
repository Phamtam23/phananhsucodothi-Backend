package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy.CreateDonViXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy.UpdateDonViXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLyResponse;
import com.DATN.PhanAnhSuCoDoThi.service.IDonViXuLyService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/don-vi-xu-ly")
@RequiredArgsConstructor
public class DonViXuLyController {

    private final IDonViXuLyService donViXuLyService;

    @PostMapping
    public ApiSuccessResponse<DonViXuLyResponse> create(
            @Valid @RequestBody CreateDonViXuLyRequest request
    ) {
        return ApiSuccessResponse.created(donViXuLyService.create(request));
    }

    @GetMapping("/{maDonVi}")
    public ApiSuccessResponse<DonViXuLyResponse> findById(
            @PathVariable String maDonVi
    ) {
        return   ApiSuccessResponse.ok(donViXuLyService.findById(maDonVi));
    }

    @GetMapping
    public  ApiSuccessResponse<List<DonViXuLyResponse>> findAll() {

        return  ApiSuccessResponse.ok(donViXuLyService.findAll());
    }

    @PutMapping("/{maDonVi}")
    public  ApiSuccessResponse<DonViXuLyResponse> update(
            @PathVariable String maDonVi,
            @Valid @RequestBody UpdateDonViXuLyRequest request
    ) {
        return  ApiSuccessResponse.ok(donViXuLyService.update(maDonVi, request));
    }
}