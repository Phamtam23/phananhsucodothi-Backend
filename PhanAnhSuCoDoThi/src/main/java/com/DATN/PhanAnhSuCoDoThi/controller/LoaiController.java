package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.service.ILoaiService;

import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/loai")
@RequiredArgsConstructor
public class LoaiController {

    private final ILoaiService loaiService;

    @PostMapping
    public ApiSuccessResponse<LoaiResponse> create(
            @RequestParam
            String tenLoai
    ) {

        return ApiSuccessResponse
                .<LoaiResponse>builder()
                .data(
                        loaiService.create(tenLoai)
                )
                .message(
                        "Tạo loại thành công"
                )
                .build();
    }

    @PutMapping("/{maLoai}")
    public ApiSuccessResponse<LoaiResponse> update(
            @PathVariable
            String maLoai,

            @RequestParam
            String tenLoai
    ) {

        return ApiSuccessResponse
                .<LoaiResponse>builder()
                .data(
                        loaiService.update(
                                tenLoai,
                                maLoai
                        )
                )
                .message(
                        "Cập nhật loại thành công"
                )
                .build();
    }

    @DeleteMapping("/{maLoai}")
    public ApiSuccessResponse<Void> delete(
            @PathVariable
            String maLoai
    ) {

        loaiService.delete(maLoai);

        return ApiSuccessResponse
                .<Void>builder()
                .message(
                        "Xóa loại thành công"
                )
                .build();
    }

    @GetMapping("/{maLoai}")
    public ApiSuccessResponse<LoaiResponse> findById(
            @PathVariable
            String maLoai
    ) {

        return ApiSuccessResponse
                .<LoaiResponse>builder()
                .data(
                        loaiService.findById(maLoai)
                )
                .message(
                        "Lấy loại thành công"
                )
                .build();
    }

    @GetMapping
    public ApiSuccessResponse<List<LoaiResponse>> findAll() {

        return ApiSuccessResponse
                .<List<LoaiResponse>>builder()
                .data(
                        loaiService.findAll()
                )
                .message(
                        "Lấy danh sách loại thành công"
                )
                .build();
    }
}