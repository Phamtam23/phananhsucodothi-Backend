package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.CreatePhieuMoLaiRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai.UpdatePhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuMoLai.PhieuMoLaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiMoLai;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuMoLaiMapper;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuPhanCongMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuMoLai;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhieuMoLaiService implements IPhieuMoLai {

    private final PhieuMoLaiRepository phieuMoLaiRepository;
    private final KetQuaXuLyRepository ketQuaXuLyRepository;
    private final PhieuMoLaiMapper phieuMoLaiMapper;
    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final NguoidanRepository  nguoidanRepository;
    private final SucoRepository sucoRepository;

    @Override
    public PhieuMoLaiResponse create(
            CreatePhieuMoLaiRequest request, String maNguoiDan
    ) {

        KetQuaXuLyEntity ketQuaXuLyEntity =
                ketQuaXuLyRepository
                        .findById(request.getMaKetQuaXuLy())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy kết quả xử lý"
                                )
                        );
        NguoidanEntity nguoiDan = nguoidanRepository
                .findById(maNguoiDan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dân"));


        PhieuMoLaiEntity entity = new PhieuMoLaiEntity();
        entity.setNguoiDan(nguoiDan);
        entity.setMaPhieuMoLai(
                IdGenerator.generateMaPhieuMoLai(
                        request.getMaKetQuaXuLy()
                )
        );

        entity.setKetQuaXuLy(ketQuaXuLyEntity);
        entity.setTrangThaiMoLai(TrangThaiMoLai.CHO_PHAN_HOI);
        entity.setLyDo(
                request.getLyDo()
        );

        PhieuPhanCongEntity phieuPhanCongEntity = entity.getKetQuaXuLy().getChiTietPhanCong().getPhieuPhanCong();

        phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.YEU_CAU_MO_LAI);

        phieuPhanCongRepository.save(phieuPhanCongEntity);
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
    public PhieuMoLaiResponse findAllByPhanCong(String maNguoiDan,String maPhanCong) {
        PhieuMoLaiEntity phieuMoLaiEntity = phieuMoLaiRepository.findAllByPhanCongSC(maNguoiDan, maPhanCong);
        return phieuMoLaiMapper.toResponse(phieuMoLaiEntity);
    }

    @Override
    public PageResponse<PhieuMoLaiResponse> findAllByDonVi(String maDonVi, int page, int size) {
        org.springframework.data.domain.Pageable pageable = org.springframework.data.domain.PageRequest.of(page, size);
        org.springframework.data.domain.Page<PhieuMoLaiEntity> pageResult = phieuMoLaiRepository.findAllByDonVi(maDonVi, pageable);
        return PageResponse.of(pageResult.map(phieuMoLaiMapper::toResponse));
    }

    @Transactional
    @Override
    public PhieuMoLaiResponse duyetPhieuMoLai(String maPhieuMoLai, boolean isApproved, String lyDoTuChoi) {
        PhieuMoLaiEntity entity = phieuMoLaiRepository.findById(maPhieuMoLai)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phiếu mở lại"));

        ChiTietPhanCongEntity chiTiet = entity.getKetQuaXuLy().getChiTietPhanCong();
        PhieuPhanCongEntity phieuPhanCongEntity = chiTiet.getPhieuPhanCong();

        if (isApproved) {
            entity.setTrangThaiMoLai(TrangThaiMoLai.CHAP_NHAN);
            chiTiet.setTrangThai(TrangThaiChiTietPhanCong.XU_LY_LAI);
            phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.XU_LY_LAI);
        } else {
            entity.setTrangThaiMoLai(TrangThaiMoLai.TU_CHOI);
            phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.KET_THUC);
            entity.setLyDoTuChoi(lyDoTuChoi);
        }

        phieuPhanCongRepository.save(phieuPhanCongEntity);
        phieuMoLaiRepository.save(entity);
        return phieuMoLaiMapper.toResponse(entity);
    }
}