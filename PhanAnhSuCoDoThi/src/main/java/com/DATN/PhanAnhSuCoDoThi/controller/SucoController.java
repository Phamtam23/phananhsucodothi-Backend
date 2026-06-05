package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.SuCoFilterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.UpdateSucoRequest;
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

    @PostMapping("/all")
    public ApiSuccessResponse<PageResponse<SucoSummaryResponse>> getAll(
            @RequestBody SuCoFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.ok(sucoService.findAll(filter, page, size));
    }
     @GetMapping("/ban-do")
    public ApiSuccessResponse<PageResponse<SucoSummaryResponse>> getByBanDo(
            @RequestParam(required = false) String maLoai,
            @RequestParam(required = false) TrangThaiSuCo trangThai,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.ok(sucoService.findForMap(maLoai, trangThai, page, size));
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

    @PutMapping
    public ApiSuccessResponse<SucoDetailResponse> update(@RequestBody UpdateSucoRequest request) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.created(sucoService.update(request,maNguoiDan));
    }

    @PostMapping("/nguoi-dan")
    public ApiSuccessResponse<PageResponse<SucoSummaryResponse>> getAllByNguoiDan(
            @RequestBody SuCoFilterRequest filter,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size )
        {
            String maNguoiDan = SecurityUtils.getCurrentRefMa();
            return ApiSuccessResponse.ok(sucoService.findByNguoiDan(maNguoiDan,filter,page,size));
        }


}