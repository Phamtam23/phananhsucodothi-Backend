package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.NhanVienDonViMapper;
import com.DATN.PhanAnhSuCoDoThi.mapper.NhanVienMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDonViRepository;
import com.DATN.PhanAnhSuCoDoThi.service.INhanVienDonVi;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NhanVienDonViService implements INhanVienDonVi {
    private final NhanVienDonViRepository nhanVienDonViRepository;
    private final NhanVienMapper nhanVienMapper;
    @Override
    public List<NhanVienDonViResponse> findAllToPhanCong(String maTruongDonVi) {

        NhanVienDonViEntity truongDonVi = nhanVienDonViRepository.findById(maTruongDonVi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trưởng đơn vị"));
        System.out.println(truongDonVi);
       return nhanVienDonViRepository.findNhanVienDangHoatDong(truongDonVi.getDonVi().getMaDonViXuLy()).stream()
               .map(nhanVienMapper::toResponseNhanVienDonVi)
               .toList();
    }

    @Override
    public List<NhanVienDonViResponse> findAllByDonVi(String donVi) {


        return nhanVienDonViRepository.findNhanVienDangHoatDongDonVi(donVi).stream()
                .map(nhanVienMapper::toResponseNhanVienDonVi)
                .toList();
    }
}
