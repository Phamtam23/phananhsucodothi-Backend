package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.CreateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.UpdateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IChiTietPhanCongService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chi-tiet-phan-cong")
@RequiredArgsConstructor
public class ChiTietPhanCongController {

    private final IChiTietPhanCongService chiTietPhanCongService;

    @GetMapping("/nhan-vien")
    public ApiSuccessResponse<PageResponse<ChiTietPhanCongEntity>> findAllByNhanVienXuLy(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        String maNhanVien = SecurityUtils.getCurrentRefMa();

        return ApiSuccessResponse.ok(
                chiTietPhanCongService.FindAllByNhanVienXuLy(
                        maNhanVien,
                        page,
                        size
                )
        );
    }

    @GetMapping("/{maChiTietPhanCong}")
    public ApiSuccessResponse<ChiTietPhanCongResponse> findById(
            @PathVariable String maChiTietPhanCong
    ) {

        return ApiSuccessResponse.ok(
                chiTietPhanCongService.FindById(maChiTietPhanCong)
        );
    }

    @PostMapping
    public ApiSuccessResponse<ChiTietPhanCongResponse> create(
            @Valid @RequestBody CreateChiTietPhanCongRequest request
    ) {

        String maTruongDonVi = SecurityUtils.getCurrentRefMa();

        return ApiSuccessResponse.created(
                chiTietPhanCongService.Create(
                        request,
                        maTruongDonVi
                )
        );
    }

    @PutMapping("/{maChiTietPhanCong}")
    public ApiSuccessResponse<ChiTietPhanCongResponse> update(
            @PathVariable String maChiTietPhanCong,
            @Valid @RequestBody UpdateChiTietPhanCongRequest request
    ) {

        return ApiSuccessResponse.ok(
                chiTietPhanCongService.update(
                        request,
                        maChiTietPhanCong
                )
        );
    }
}