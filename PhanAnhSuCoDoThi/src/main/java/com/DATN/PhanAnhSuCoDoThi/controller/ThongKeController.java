package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe.ThongKeDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe.ThongKeHeThongResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/thong-ke")
@RequiredArgsConstructor
public class ThongKeController {

    private final IThongKeService thongKeService;

    @GetMapping
    public ApiSuccessResponse<ThongKeHeThongResponse> thongKeHeThong(
            @RequestParam(required = false) Integer nam,
            @RequestParam(required = false) Integer thang
    ) {
        if (nam == null) {
            nam = LocalDate.now().getYear();
        }
        return ApiSuccessResponse.ok(thongKeService.thongKeHeThong(nam, thang));
    }

    @GetMapping("/don-vi")
    public ApiSuccessResponse<ThongKeDonViResponse> thongKeDonVi(
            @RequestParam(required = false) Integer nam
    ) {
        String maTaiKhoan = SecurityUtils.getCurrentMaTaiKhoan();
        if (nam == null) {
            nam = LocalDate.now().getYear();
        }
        return ApiSuccessResponse.ok(thongKeService.thongKeDonVi(maTaiKhoan, nam));
    }
}
