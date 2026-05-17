package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.CreateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.UpdateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLySummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.service.IKetQuaXuLyService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/ket-qua-xu-ly")
@RequiredArgsConstructor
public class KetQuaXuLyController {
    private final IKetQuaXuLyService ketQuaXuLyService;
    @GetMapping("/chi-tiet-phan-cong/{maChiTietPhanCong}")
    public ApiSuccessResponse<List<KetQuaXuLySummaryResponse>> getByChiTietPhanCong(
            @PathVariable String maChiTietPhanCong) {
        return ApiSuccessResponse.ok(ketQuaXuLyService.findByChiTietPhanCong(maChiTietPhanCong));
    }

    @GetMapping("/{ma}")
    public ApiSuccessResponse<KetQuaXuLyDetailResponse> getById(@PathVariable String ma) {
        return ApiSuccessResponse.ok(ketQuaXuLyService.findById(ma));
    }

    @PostMapping
    public ApiSuccessResponse<KetQuaXuLyDetailResponse> create(
            @RequestBody CreateKetQuaXuLyRequest request) {
        return ApiSuccessResponse.created(ketQuaXuLyService.create(request));
    }

    @PutMapping
    public ApiSuccessResponse<KetQuaXuLyDetailResponse> update(
            @RequestBody UpdateKetQuaXuLyRequest request) {
        return ApiSuccessResponse.ok(ketQuaXuLyService.update(request));
    }
}
