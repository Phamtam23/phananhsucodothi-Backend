package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuDanhGiaRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuDanhGiaResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuDanhGiaService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phieu-danh-gia")
@RequiredArgsConstructor
public class PhieuDanhGiaController {

    private final IPhieuDanhGiaService phieuDanhGiaService;

    @PostMapping
    public ApiSuccessResponse<PhieuDanhGiaResponse> create(
            @Valid @RequestBody PhieuDanhGiaRequest request
    ) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.created(
                phieuDanhGiaService.create(request,maNguoiDan)
        );
    }

    @GetMapping("/ket-qua-xu-ly/{maKetQuaXuLy}")
    public ApiSuccessResponse<PhieuDanhGiaResponse> findByKetQuaXuLy(
            @PathVariable String maKetQuaXuLy
    ) {

        return ApiSuccessResponse.ok(
                phieuDanhGiaService.findByKetQuaXuLy(maKetQuaXuLy)
        );
    }
}