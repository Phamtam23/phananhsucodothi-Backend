package com.DATN.PhanAnhSuCoDoThi.controller;
import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.service.INhanVienDonVi;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/nhan-vien-don-vi")
@RequiredArgsConstructor
public class NhanVienDonViController {
    private final INhanVienDonVi nhanVienDonViService;

    @GetMapping("/phan-cong/{maDonVi}")
    public ResponseEntity<List<NhanVienDonViResponse>> findAllToPhanCong(
            @PathVariable String maDonVi
    ) {
        return ResponseEntity.ok(
                nhanVienDonViService.findAllToPhanCong(maDonVi)
        );
    }

    @GetMapping("/don-vi/{maDonVi}")
    public ResponseEntity<List<NhanVienDonViResponse>> findAllByDonVi(
            @PathVariable String maDonVi
    ) {
        return ResponseEntity.ok(
                nhanVienDonViService.findAllByDonVi(maDonVi)
        );
    }
}
