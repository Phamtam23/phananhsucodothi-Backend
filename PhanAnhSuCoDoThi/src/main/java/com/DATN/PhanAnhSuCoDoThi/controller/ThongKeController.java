package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKeHeThongResponse;
import com.DATN.PhanAnhSuCoDoThi.service.IThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thong-ke")
@RequiredArgsConstructor
public class ThongKeController {

    private final IThongKeService thongKeService;

    @GetMapping
    public ApiSuccessResponse<ThongKeHeThongResponse> thongKeHeThong() {
        return ApiSuccessResponse.ok(thongKeService.thongKeHeThong());
    }
}
