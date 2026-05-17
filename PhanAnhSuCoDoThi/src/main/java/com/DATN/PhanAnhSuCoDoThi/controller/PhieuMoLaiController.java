package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.CreatePhieuMoLaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.UpdatePhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuMoLai.PhieuMoLaiResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuMoLai;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phieu-mo-lai")
@RequiredArgsConstructor
public class PhieuMoLaiController {

    private final IPhieuMoLai phieuMoLaiService;

    // Tạo phiếu mở lại
    @PostMapping
    public ResponseEntity<PhieuMoLaiResponse> create(
            @RequestBody CreatePhieuMoLaiRequest request
    ) {
        return ResponseEntity.ok(
                phieuMoLaiService.create(request)
        );
    }

    // Cập nhật phiếu mở lại
    @PutMapping
    public ResponseEntity<PhieuMoLaiResponse> update(
            @RequestBody UpdatePhieuMoLai request
    ) {
        return ResponseEntity.ok(
                phieuMoLaiService.update(request)
        );
    }

    // Lấy chi tiết phiếu mở lại
    @GetMapping("/{maPhieuMoLai}")
    public ResponseEntity<PhieuMoLaiResponse> findById(
            @PathVariable String maPhieuMoLai
    ) {
        return ResponseEntity.ok(
                phieuMoLaiService.findById(maPhieuMoLai)
        );
    }

    // Lấy danh sách phiếu mở lại theo chi tiết phân công
    @GetMapping("/phan-cong/{phanCong}")
    public ResponseEntity<List<PhieuMoLaiResponse>> findAllByPhanCong(
            @PathVariable String maChiTietPhanCong
    ) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ResponseEntity.ok(
                phieuMoLaiService.findAllByPhanCong(maNguoiDan,maChiTietPhanCong)
        );
    }
}