package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuPhanCong;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/phancong")
@RequiredArgsConstructor
public class PhieuPhanCongController {

    private final IPhieuPhanCong phieuPhanCongService;

    @PostMapping
    public ApiSuccessResponse<PhieuPhanCongResponse> create(
            @RequestBody CreatePhieuPhanCongRequest request
    ) {
        String maNhanVien = SecurityUtils.getCurrentRefMa();

        return ApiSuccessResponse.created(
                phieuPhanCongService.create(request, maNhanVien)
        );
    }

    @PutMapping("/{maPhieu}")
    public ApiSuccessResponse<PhieuPhanCongResponse> update(
            @PathVariable String maPhieu,
            @RequestBody UpdatePhieuPhanCongRequest request
    ) {
        return ApiSuccessResponse.ok(
                phieuPhanCongService.update(maPhieu, request)
        );
    }

    @GetMapping("/{maPhieu}")
    public ApiSuccessResponse<PhieuPhanCongResponse> getById(
            @PathVariable String maPhieu
    ) {
        return ApiSuccessResponse.ok(
                phieuPhanCongService.findById(maPhieu)
        );
    }

    //Còn has quyền với lấy laại mã đơn vị chứ này đang lấy mã của nhân viên
    @GetMapping("/donvi")
    public ApiSuccessResponse<PageResponse<PhieuPhanCongResponse>> getByDonVi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        String maDonVi = SecurityUtils.getCurrentRefMa();

        return ApiSuccessResponse.ok(
                phieuPhanCongService.findAllByDonVi(maDonVi, page, size)
        );
    }
}