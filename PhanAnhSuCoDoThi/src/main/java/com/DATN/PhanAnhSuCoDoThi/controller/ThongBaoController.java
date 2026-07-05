package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongBaoResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IThongBaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/thong-bao")
@RequiredArgsConstructor
public class ThongBaoController {

    private final IThongBaoService thongBaoService;

    @GetMapping
    public ApiSuccessResponse<PageResponse<ThongBaoResponse>> getThongBao(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        String maTaiKhoan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(thongBaoService.getThongBaoByTaiKhoan(maTaiKhoan, page, size));
    }

    @GetMapping("/unread-count")
    public ApiSuccessResponse<Long> getUnreadCount() {
        String maTaiKhoan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(thongBaoService.getUnreadCount(maTaiKhoan));
    }

    @PutMapping("/{maThongBao}/read")
    public ApiSuccessResponse<Void> markAsRead(@PathVariable String maThongBao) {
        String maTaiKhoan = SecurityUtils.getCurrentRefMa();
        thongBaoService.markAsRead(maThongBao, maTaiKhoan);
        return ApiSuccessResponse.ok(null);
    }

    @PutMapping("/read-all")
    public ApiSuccessResponse<Void> markAllAsRead() {
        String maTaiKhoan = SecurityUtils.getCurrentRefMa();
        thongBaoService.markAllAsRead(maTaiKhoan);
        return ApiSuccessResponse.ok(null);
    }
}
