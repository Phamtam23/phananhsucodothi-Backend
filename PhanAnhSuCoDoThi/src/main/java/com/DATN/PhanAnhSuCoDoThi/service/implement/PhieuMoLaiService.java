package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.CreatePhieuMoLaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.UpdatePhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuMoLai.PhieuMoLaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuMoLaiEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuMoLaiMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.KetQuaXuLyRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuMoLaiRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuMoLaiService implements IPhieuMoLai {

    private final PhieuMoLaiRepository phieuMoLaiRepository;
    private final KetQuaXuLyRepository ketQuaXuLyRepository;
    private final PhieuMoLaiMapper phieuMoLaiMapper;

    @Override
    public PhieuMoLaiResponse create(
            CreatePhieuMoLaiRequest request
    ) {

        KetQuaXuLyEntity ketQuaXuLyEntity =
                ketQuaXuLyRepository
                        .findById(request.getMaKetQuaXuLy())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy kết quả xử lý"
                                )
                        );

        PhieuMoLaiEntity entity = new PhieuMoLaiEntity();

        entity.setMaPhieuMoLai(
                IdGenerator.generateMaPhieuMoLai(
                        request.getMaKetQuaXuLy()
                )
        );

        entity.setKetQuaXuLy(ketQuaXuLyEntity);

        entity.setLyDo(
                request.getLyDo()
        );

        phieuMoLaiRepository.save(entity);

        return phieuMoLaiMapper.toResponse(entity);
    }

    @Override
    public PhieuMoLaiResponse update(
            UpdatePhieuMoLai request
    ) {

        PhieuMoLaiEntity entity =
                phieuMoLaiRepository
                        .findById(request.getMaPhieuMoLai())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy phiếu mở lại"
                                )
                        );

        if (request.getTrangThaiMoLai()!= null) {
            entity.setTrangThaiMoLai(
                    request.getTrangThaiMoLai()
            );
        }

        phieuMoLaiRepository.save(entity);

        return phieuMoLaiMapper.toResponse(entity);
    }

    @Override
    public PhieuMoLaiResponse findById(
            String maPhieuMoLai
    ) {

        PhieuMoLaiEntity entity =
                phieuMoLaiRepository
                        .findById(maPhieuMoLai)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy phiếu mở lại"
                                )
                        );

        return phieuMoLaiMapper.toResponse(entity);
    }

    @Override
    public List<PhieuMoLaiResponse> findAllByPhanCong(String maNguoiDan,String maPhanCong) {
        List<PhieuMoLaiEntity> phieuMoLaiEntityList = phieuMoLaiRepository.findAllByPhanCong(maNguoiDan, maPhanCong);
        return phieuMoLaiEntityList.stream()
                .map(phieuMoLaiMapper::toResponse)
                .toList();
    }
}