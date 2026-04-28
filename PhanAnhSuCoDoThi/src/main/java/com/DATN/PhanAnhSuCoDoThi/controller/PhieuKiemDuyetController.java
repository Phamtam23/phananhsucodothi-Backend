package com.DATN.PhanAnhSuCoDoThi.controller;

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
    public ResponseEntity<PhieuKiemDuyetResponse> create(
            @Valid @RequestBody CreatePhieuKiemDuyetRequest request) {
        return ResponseEntity.ok(phieuKiemDuyetService.create(request));
    }

    // Lấy danh sách phiếu theo mã
    @GetMapping("/{maKiemDuyet}")
    @PreAuthorize("hasAnyRole('NV_DIEUPHOI', 'ADMIN')")
    public ResponseEntity<PhieuKiemDuyetResponse> getByMa(
            @PathVariable String maKiemDuyet) {
        return ResponseEntity.ok(phieuKiemDuyetService.getByMa(maKiemDuyet));
    }

    // Lấy danh sách phiếu theo mã sự cố
    @GetMapping("/su-co/{maSuCo}")
    @PreAuthorize("hasAnyRole('NV_DIEUPHOI', 'ADMIN')")
    public ResponseEntity<List<PhieuKiemDuyetResponse>> getByMaSuCo(
            @PathVariable String maSuCo) {
        return ResponseEntity.ok(phieuKiemDuyetService.getByMaSuCo(maSuCo));
    }
}
