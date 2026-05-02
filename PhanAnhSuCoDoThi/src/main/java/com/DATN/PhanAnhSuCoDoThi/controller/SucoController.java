package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/suco")
@RequiredArgsConstructor
public class SucoController {

    private final ISucoService sucoService;

    @GetMapping
    public ApiSuccessResponse<PageResponse<SucoResponse>> getAll(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ApiSuccessResponse.ok(sucoService.findAll(page,size));
    }

    @GetMapping("/{ma}")
    public ApiSuccessResponse<SucoResponse> getById(@PathVariable String ma) {
        return ApiSuccessResponse.ok(sucoService.findById(ma));
    }

    @PostMapping
    public ApiSuccessResponse<SucoResponse> create(@RequestBody CreateSucoRequest request) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.created(sucoService.create(request, maNguoiDan));
    }

    @GetMapping("/nguoi-dan")
    public ApiSuccessResponse<PageResponse<SucoResponse>> getAllByNguoiDan(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size )
        {
            String maNguoiDan = SecurityUtils.getCurrentRefMa();
            return ApiSuccessResponse.ok(sucoService.findByNguoiDan(maNguoiDan,page,size));
        }
}