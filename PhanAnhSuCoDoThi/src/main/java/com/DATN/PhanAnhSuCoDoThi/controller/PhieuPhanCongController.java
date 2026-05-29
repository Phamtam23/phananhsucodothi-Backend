package com.DATN.PhanAnhSuCoDoThi.controller;

import com.DATN.PhanAnhSuCoDoThi.dto.ApiSuccessResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCongFilterRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongLSResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCResponse;
import com.DATN.PhanAnhSuCoDoThi.security.SecurityUtils;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuPhanCong;
import com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDonViRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/phancong")
@RequiredArgsConstructor
public class PhieuPhanCongController {

    private final IPhieuPhanCong phieuPhanCongService;
    private final NhanVienDonViRepository nhanVienDonViRepository;

    @PostMapping
    public ApiSuccessResponse<List<PhieuPhanCongResponse>> create(
            @RequestBody CreatePhieuPhanCongRequest request
    ) {
        String maNhanVien = SecurityUtils.getCurrentRefMa();

        return ApiSuccessResponse.created(
                phieuPhanCongService.create(request, maNhanVien)
        );
    }

    @PutMapping("/{maPhieu}")
    public ApiSuccessResponse<PhieuPhanCongResponse> update(
            @PathVariable String maPhieu,
            @RequestBody UpdatePhieuPhanCongRequest request
    ) {
        return ApiSuccessResponse.ok(
                phieuPhanCongService.update(maPhieu, request)
        );
    }

    @GetMapping("/{maPhieu}")
    public ApiSuccessResponse<PhieuPhanCongResponse> getById(
            @PathVariable String maPhieu
    ) {
        return ApiSuccessResponse.ok(
                phieuPhanCongService.findById(maPhieu)
        );
    }

    @GetMapping("/don-vi")
    public ApiSuccessResponse<PageResponse<PhieuPhanCongResponse>> getByDonVi(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {

        String maNhanVien = SecurityUtils.getCurrentRefMa();
        String maDonVi = nhanVienDonViRepository.findById(maNhanVien)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin nhân viên đơn vị"))
                .getDonVi().getMaDonViXuLy();

        return ApiSuccessResponse.ok(
                phieuPhanCongService.findAllByDonVi(maDonVi, page, size)
        );
    }

    @GetMapping("/su-co/{maSuCo}")
    public ApiSuccessResponse<List<PhieuPhanCongSCResponse>> getBySuCo(
            @PathVariable String maSuCo
    ) {
        String maNguoiDan = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(
                phieuPhanCongService.findAllBySuCo(maSuCo,maNguoiDan)
        );
    }

    @GetMapping("/nhan-vien")
    public ApiSuccessResponse<PageResponse<PhieuPhanCongLSResponse>> findAllByNhanVien(
            @ModelAttribute PhieuPhanCongFilterRequest request
    ) {
        String maNhanVien = SecurityUtils.getCurrentRefMa();
        return ApiSuccessResponse.ok(
                phieuPhanCongService.findAllByNhanVien(maNhanVien, request)
        );
    }
}