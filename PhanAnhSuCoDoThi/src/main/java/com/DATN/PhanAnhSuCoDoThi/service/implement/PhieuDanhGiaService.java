package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuDanhGiaRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuDanhGiaResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuDanhGiaEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuDanhGiaMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.KetQuaXuLyRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuDanhGiaRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuDanhGiaService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuDanhGiaService implements IPhieuDanhGiaService {

    PhieuDanhGiaRepository phieuDanhGiaRepository;
    PhieuDanhGiaMapper phieuDanhGiaMapper;
    KetQuaXuLyRepository ketQuaXuLyRepository;

    @Override
    public PhieuDanhGiaResponse create(PhieuDanhGiaRequest phieuDanhGiaRequest) {

        KetQuaXuLyEntity ketQuaXuLyEntity = ketQuaXuLyRepository.findById(phieuDanhGiaRequest.getMaKetQuaXuLy())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả xử lý"));

        PhieuDanhGiaEntity phieuDanhGiaEntity = new PhieuDanhGiaEntity();
        phieuDanhGiaEntity.setMaDanhGia(IdGenerator.generateMaPhieuDanhGia(phieuDanhGiaRequest.getMaKetQuaXuLy()));
        phieuDanhGiaEntity.setKetQuaXuLy(ketQuaXuLyEntity);
        phieuDanhGiaEntity.setMucDoHaiLong(phieuDanhGiaEntity.getMucDoHaiLong());
        phieuDanhGiaRepository.save(phieuDanhGiaEntity);

        return phieuDanhGiaMapper.toResponse(phieuDanhGiaEntity);
    }

    @Override
    public List<PhieuDanhGiaResponse> findByKetQuaXuLy(String maKetQuaXyLy) {
        return phieuDanhGiaRepository
                .findByKetQuaXuLy_MaKetQua(maKetQuaXyLy)
                .stream()
                .map(phieuDanhGiaMapper::toResponse)
                .toList();
    }

}
