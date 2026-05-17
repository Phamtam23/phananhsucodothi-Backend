package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy.CreateDonViXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy.UpdateDonViXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.DonViXuLyResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.DonViXuLyEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiDonVi;
import com.DATN.PhanAnhSuCoDoThi.mapper.DonViXuLyMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.DonViXuLyRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IDonViXuLyService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DonViXuLyService implements IDonViXuLyService {

    private final DonViXuLyRepository donViXuLyRepository;
    private final DonViXuLyMapper donViXuLyMapper;

    @Override
    public DonViXuLyResponse create(CreateDonViXuLyRequest request) {
        DonViXuLyEntity entity = new DonViXuLyEntity();
        entity.setMaDonViXuLy(IdGenerator.generateMaDonViSuCo());
        entity.setTenDonVi(request.getTenDonVi());
        entity.setKhuVuc(request.getKhuVuc());
        entity.setMoTa(request.getMoTa());
        entity.setDiaChi(request.getDiaChi());
        entity.setSdt(request.getSdt());
        entity.setEmail(request.getEmail());
        entity.setTrangThai(TrangThaiDonVi.HOAT_DONG);

        DonViXuLyEntity saved = donViXuLyRepository.save(entity);

        return donViXuLyMapper.toResponse(saved);
    }

    @Override
    public DonViXuLyResponse findById(String maDonVi) {

        DonViXuLyEntity entity = donViXuLyRepository.findById(maDonVi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị xử lý"));

        return donViXuLyMapper.toResponse(entity);
    }

    @Override
    public DonViXuLyResponse update(String maDonVi,UpdateDonViXuLyRequest request) {

        DonViXuLyEntity entity = donViXuLyRepository
                .findById(maDonVi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị xử lý"));

        entity.setTenDonVi(request.getTenDonVi());
        entity.setKhuVuc(request.getKhuVuc());
        entity.setMoTa(request.getMoTa());
        entity.setDiaChi(request.getDiaChi());
        entity.setSdt(request.getSdt());
        entity.setEmail(request.getEmail());
        entity.setTrangThai(request.getTrangThai());

        DonViXuLyEntity updated = donViXuLyRepository.save(entity);

        return donViXuLyMapper.toResponse(updated);
    }

    @Override
    public List<DonViXuLyResponse> findAll() {

        return donViXuLyRepository.findAll()
                .stream()
                .map(donViXuLyMapper::toResponse)
                .collect(Collectors.toList());
    }
}