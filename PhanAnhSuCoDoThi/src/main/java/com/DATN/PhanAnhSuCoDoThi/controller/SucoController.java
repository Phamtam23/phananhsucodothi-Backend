package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/suco")
@RequiredArgsConstructor
public class SucoController {

    private final ISucoService sucoService;

    @GetMapping
    public ApiSuccessResponse<PageResponse<SucoSummaryResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.ok(sucoService.findAll(page,size));
    }

    @GetMapping("/{ma}")
    public ApiSuccessResponse<SucoDetailResponse> getById(@PathVariable String ma) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(sucoService.findById(ma,maNguoiDan));
    }

    @PostMapping
    public ApiSuccessResponse<SucoDetailResponse> create(@RequestBody CreateSucoRequest request) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.created(sucoService.create(request, maNguoiDan));
    }

    @GetMapping("/nguoi-dan")
    public ApiSuccessResponse<PageResponse<SucoSummaryResponse>> getAllByNguoiDan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size )
        {
            String maNguoiDan = SecurityUtils.getCurrentRefMa();
            return ApiSuccessResponse.ok(sucoService.findByNguoiDan(maNguoiDan,page,size));
        }

    @GetMapping("/trang-thai/{trangThaiSuCo}")
    public ApiSuccessResponse<PageResponse<SucoSummaryResponse>> getByTrangThai(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @PathVariable TrangThaiSuCo trangThaiSuCo
    ) {
        return ApiSuccessResponse.ok(
                sucoService.findByTrangThai(page, size, trangThaiSuCo)
        );
    }
}