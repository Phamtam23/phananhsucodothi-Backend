package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuKiemDuyet.CreatePhieuKiemDuyetRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuKiemDuyetResponse;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuKiemDuyetService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phieu-kiem-duyet")
@RequiredArgsConstructor
public class PhieuKiemDuyetController {

    private final IPhieuKiemDuyetService phieuKiemDuyetService;

    // Tạo phiếu kiểm duyệt - chỉ nhân viên điều phối
    @PostMapping
    @PreAuthorize("hasRole('NV_DIEUPHOI')")
    public ApiSuccessResponse<PhieuKiemDuyetResponse> create(
            @Valid @RequestBody CreatePhieuKiemDuyetRequest request) {
        return ApiSuccessResponse.created(phieuKiemDuyetService.create(request));
    }

    // Lấy danh sách phiếu theo mã
    @GetMapping("/{maKiemDuyet}")
    @PreAuthorize("hasAnyRole('NV_DIEUPHOI', 'ADMIN')")
    public ApiSuccessResponse< PhieuKiemDuyetResponse> getByMa(
            @PathVariable String maKiemDuyet) {
        return ApiSuccessResponse.ok(phieuKiemDuyetService.getByMa(maKiemDuyet));
    }

    // Lấy danh sách phiếu theo mã sự cố
    @GetMapping("/su-co/{maSuCo}")
    @PreAuthorize("hasAnyRole('NV_DIEUPHOI', 'ADMIN')")
    public ApiSuccessResponse<List<PhieuKiemDuyetResponse>> getByMaSuCo(
            @PathVariable String maSuCo) {
        return ApiSuccessResponse.ok(phieuKiemDuyetService.getByMaSuCo(maSuCo));
    }
}
