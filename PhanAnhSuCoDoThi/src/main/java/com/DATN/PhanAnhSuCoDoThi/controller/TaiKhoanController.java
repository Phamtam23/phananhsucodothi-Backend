package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.CreateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Taikhoan.UpdateTaiKhoanRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.TaiKhoanResponse;
import com.DATN.PhanAnhSuCoDoThi.service.ITaiKhoanService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tai-khoan")
@RequiredArgsConstructor
public class TaiKhoanController {

    private final ITaiKhoanService taiKhoanService;

    @GetMapping
    public ApiSuccessResponse<List<TaiKhoanResponse>> findAll() {
        return ApiSuccessResponse.ok(taiKhoanService.findAll());
    }

    @GetMapping("/{maTaiKhoan}")
    public ApiSuccessResponse<TaiKhoanResponse> findById(@PathVariable String maTaiKhoan) {
        return ApiSuccessResponse.ok(taiKhoanService.findById(maTaiKhoan));
    }

    @PostMapping
    public ApiSuccessResponse<TaiKhoanResponse> create(
            @Valid @RequestBody CreateTaiKhoanRequest request) {
        return ApiSuccessResponse.created(taiKhoanService.create(request));
    }

    @PutMapping("/{maTaiKhoan}")
    public ApiSuccessResponse<TaiKhoanResponse> update(
            @PathVariable String maTaiKhoan,
            @Valid @RequestBody UpdateTaiKhoanRequest request) {
        return ApiSuccessResponse.ok(taiKhoanService.update(maTaiKhoan, request));
    }

    @PutMapping("/{maTaiKhoan}/khoa")
    public ApiSuccessResponse<TaiKhoanResponse> khoa(@PathVariable String maTaiKhoan) {
        return ApiSuccessResponse.ok(taiKhoanService.khoa(maTaiKhoan));
    }

    @PutMapping("/{maTaiKhoan}/mo-khoa")
    public ApiSuccessResponse<TaiKhoanResponse> moKhoa(@PathVariable String maTaiKhoan) {
        return ApiSuccessResponse.ok(taiKhoanService.moKhoa(maTaiKhoan));
    }
}
