package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.CreatePhieuMoLaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.UpdatePhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
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
    private final com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDonViRepository nhanVienDonViRepository;

    @PostMapping
    public com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse<PhieuMoLaiResponse> create(
            @RequestBody CreatePhieuMoLaiRequest request
    ) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse.ok(
                phieuMoLaiService.create(request,maNguoiDan)
        );
    }

    @PutMapping
    public ApiSuccessResponse<PhieuMoLaiResponse> update(
            @RequestBody UpdatePhieuMoLai request
    ) {
        return ApiSuccessResponse.ok(
                phieuMoLaiService.update(request)
        );
    }

    @GetMapping("/{maPhieuMoLai}")
    public ApiSuccessResponse<PhieuMoLaiResponse> findById(
            @PathVariable("maPhieuMoLai") String maPhieuMoLai
    ) {
        return ApiSuccessResponse.ok(
                phieuMoLaiService.findById(maPhieuMoLai)
        );
    }

    @GetMapping("/phan-cong/{maChiTietPhanCong}")
    public ApiSuccessResponse<PhieuMoLaiResponse> findByPhanCong(
            @PathVariable("maChiTietPhanCong") String maChiTietPhanCong
    ) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(
                phieuMoLaiService.findAllByPhanCong(maNguoiDan,maChiTietPhanCong)
        );
    }

    @GetMapping("/don-vi")
    public ApiSuccessResponse<PageResponse<PhieuMoLaiResponse>> getByDonVi(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        String maNhanVien = SecurityUtils.getCurrentRefMa();
        String maDonVi = nhanVienDonViRepository.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin nhân viên đơn vị"))
                .getDonVi().getMaDonViXuLy();

        return ApiSuccessResponse.ok(
                phieuMoLaiService.findAllByDonVi(maDonVi, page, size)
        );
    }

    @PutMapping("/duyet/{maPhieuMoLai}")
    public ApiSuccessResponse<PhieuMoLaiResponse> duyetPhieuMoLai(
            @PathVariable("maPhieuMoLai") String maPhieuMoLai,
            @RequestParam("isApproved") boolean isApproved,
            @RequestParam(value = "lyDoTuChoi", required = false) String lyDoTuChoi
    ) {
        return ApiSuccessResponse.ok(
                phieuMoLaiService.duyetPhieuMoLai(maPhieuMoLai, isApproved, lyDoTuChoi)
        );
    }
}