package com.DATN.PhanAnhSuCoDoThi.controller;
import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.INhanVienDonVi;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;

@RestController
@RequestMapping("/nhan-vien-don-vi")
@RequiredArgsConstructor
public class NhanVienDonViController {
    private final INhanVienDonVi nhanVienDonViService;

    @GetMapping("/phan-cong")
    public ApiSuccessResponse<List<NhanVienDonViResponse>> findAllToPhanCong(
    ) {
        String maTruongDonVi = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(
                nhanVienDonViService.findAllToPhanCong(maTruongDonVi)
        );
    }

    @GetMapping("/don-vi/{maDonVi}")
    public ApiSuccessResponse<List<NhanVienDonViResponse>> findAllByDonVi(
            @PathVariable String maDonVi
    ) {
        return ApiSuccessResponse.ok(
                nhanVienDonViService.findAllByDonVi(maDonVi)
        );
    }
}
