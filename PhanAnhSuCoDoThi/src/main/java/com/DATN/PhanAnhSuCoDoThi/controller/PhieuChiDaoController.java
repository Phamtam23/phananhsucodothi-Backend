package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.CreateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.UpdateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuChiDaoResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuChiDaoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phieu-chi-dao")
@RequiredArgsConstructor
public class PhieuChiDaoController {

    private final IPhieuChiDaoService phieuChiDaoService;

    @PostMapping
    public ApiSuccessResponse<PhieuChiDaoResponse> create(
            @Valid @RequestBody CreateChiDaoRequest request
    ) {
        String maTruongDonVi = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.created(
                phieuChiDaoService.create(request, maTruongDonVi)
        );
    }

    @PutMapping("/{maChiDao}")
    public ApiSuccessResponse<PhieuChiDaoResponse> update(
            @PathVariable String maChiDao,
            @Valid @RequestBody UpdateChiDaoRequest request
    ) {
        return ApiSuccessResponse.ok(
                phieuChiDaoService.update(maChiDao, request)
        );
    }

    @DeleteMapping("/{maChiDao}")
    public ApiSuccessResponse<String> delete(
            @PathVariable String maChiDao
    ) {
        return ApiSuccessResponse.ok(
                phieuChiDaoService.deleteById(maChiDao)
        );
    }

    @GetMapping("/{maChiDao}")
    public ApiSuccessResponse<PhieuChiDaoResponse> findById(
            @PathVariable String maChiDao
    ) {
        return ApiSuccessResponse.ok(
                phieuChiDaoService.findById(maChiDao)
        );
    }

    @GetMapping("/chi-tiet-phan-cong/{maChiTietPhanCong}")
    public ApiSuccessResponse<PageResponse<PhieuChiDaoResponse>> findByChiTietPhanCong(
            @PathVariable String maChiTietPhanCong,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        return ApiSuccessResponse.ok(
                PageResponse.of(
                        phieuChiDaoService.findByChiTietPhanCong(
                                maChiTietPhanCong,
                                page,
                                size
                        )
                )
        );
    }
}