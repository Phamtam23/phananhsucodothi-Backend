package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanLoaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanLoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.service.IPhanLoaiService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phieu-phan-loai")
@RequiredArgsConstructor
public class PhieuPhanLoaiController {

    private final IPhanLoaiService phanLoaiService;

    @PostMapping
    public ApiSuccessResponse<PhieuPhanLoaiResponse> create(
            @RequestBody
            PhieuPhanLoaiRequest request
    ) {

        return ApiSuccessResponse.<PhieuPhanLoaiResponse>builder()
                .data(phanLoaiService.create(request)
                )
                .message("Phân loại thành công")
                .build();
    }

    @DeleteMapping
    public ApiSuccessResponse<Void> delete(
            @RequestParam
            String maSuCo,

            @RequestParam
            String maLoai
    ) {

        phanLoaiService.delete(
                maSuCo,
                maLoai
        );

        return ApiSuccessResponse.<Void>builder()
                .message("Xóa phân loại thành công")
                .build();
    }

    @GetMapping("/{maSuCo}")
    public ApiSuccessResponse<List<LoaiResponse>> getByMaSuCo(
            @PathVariable
            String maSuCo
    ) {

        return ApiSuccessResponse
                .<List<LoaiResponse>>builder()
                .data(
                        phanLoaiService
                                .getByMaSuCo(maSuCo)
                )
                .message(
                        "Lấy danh sách phân loại thành công"
                )
                .build();
    }

}