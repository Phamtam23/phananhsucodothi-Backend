package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuDanhGiaRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuDanhGiaResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuDanhGiaEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuDanhGiaMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.KetQuaXuLyRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.NguoidanRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuDanhGiaRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuDanhGiaService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuDanhGiaService implements IPhieuDanhGiaService {

    private final PhieuDanhGiaRepository phieuDanhGiaRepository;
    private final PhieuDanhGiaMapper phieuDanhGiaMapper;
    private final KetQuaXuLyRepository ketQuaXuLyRepository;
    private final NguoidanRepository nguoidanRepository;

    @Override
    public PhieuDanhGiaResponse create(PhieuDanhGiaRequest phieuDanhGiaRequest, String maNguoiDan) {

        try {

            KetQuaXuLyEntity ketQuaXuLyEntity = ketQuaXuLyRepository
                    .findById(phieuDanhGiaRequest.getMaKetQuaXuLy())
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả xử lý"));

            PhieuDanhGiaEntity phieuDanhGiaEntity = new PhieuDanhGiaEntity();

            phieuDanhGiaEntity.setMaDanhGia(
                    IdGenerator.generateMaPhieuDanhGia(
                            phieuDanhGiaRequest.getMaKetQuaXuLy()
                    )
            );

            phieuDanhGiaEntity.setKetQuaXuLy(ketQuaXuLyEntity);

            phieuDanhGiaEntity.setMucDoHaiLong(
                    phieuDanhGiaRequest.getMucDoHaiLong()
            );

            phieuDanhGiaEntity.setThoiGianTao(LocalDateTime.now());
            NguoidanEntity nguoiDan = nguoidanRepository
                    .findById(maNguoiDan)
                    .orElseThrow(() -> new RuntimeException("Không tìm thấy người dân"));

            phieuDanhGiaEntity.setNguoiDan(nguoiDan);
            phieuDanhGiaRepository.save(phieuDanhGiaEntity);

            return phieuDanhGiaMapper.toResponse(phieuDanhGiaEntity);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }
    }

    @Override
    public PhieuDanhGiaResponse findByKetQuaXuLy(String maKetQuaXyLy) {

        PhieuDanhGiaEntity phieuDanhGiaEntity = phieuDanhGiaRepository
                .findByKetQuaXuLy_MaKetQua(maKetQuaXyLy);

        return PhieuDanhGiaResponse.builder()
                .mucDoDanhGia(phieuDanhGiaEntity.getMucDoHaiLong())
                .maXuLyKetQua(phieuDanhGiaEntity.getKetQuaXuLy().getMaKetQua())
                .build();


    }

}
