package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.CreateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.UpdateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLySummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKetQua;
import com.DATN.PhanAnhSuCoDoThi.mapper.KetQuaXuLyMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.ChiTietPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.KetQuaXuLyRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IKetQuaXuLy;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class KetQuaXuLyService implements IKetQuaXuLy {

    private final KetQuaXuLyRepository ketQuaXuLyRepository;
    private final ChiTietPhanCongRepository chiTietPhanCongRepository;
    private final KetQuaXuLyMapper ketQuaXuLyMapper;

    @Override
    public List<KetQuaXuLySummaryResponse> findByChiTietPhanCong(
            String maChiTietPhanCong
    ) {

        List<KetQuaXuLyEntity> entities =
                ketQuaXuLyRepository
                        .findByChiTietPhanCong_MaChiTietPhanCong(
                                maChiTietPhanCong
                        );

        return entities.stream()
                .map(ketQuaXuLyMapper::toSummaryResponse)
                .toList();
    }

    @Override
    public KetQuaXuLyDetailResponse create(
            CreateKetQuaXuLyRequest request
    ) {

        ChiTietPhanCongEntity chiTietPhanCong =
                chiTietPhanCongRepository
                        .findById(request.getMaChiTietPhanCong())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy chi tiết phân công"
                                )
                        );

        KetQuaXuLyEntity entity = new KetQuaXuLyEntity();

        entity.setMaKetQua(IdGenerator.generateMaKetQuaXuLy(request.getMaChiTietPhanCong()));
        entity.setChiTietPhanCong(chiTietPhanCong);
        entity.setThoiGianNop(LocalDateTime.now());
        entity.setTrangThai(TrangThaiKetQua.CHO_DUYET);
        entity.setNoiDungThucHien(request.getNoiDungThucHien());

        ketQuaXuLyRepository.save(entity);

        return ketQuaXuLyMapper.toDetailResponse(entity);
    }

    @Override
    public KetQuaXuLyDetailResponse findById(String maKetQuaXuLyId) {

        KetQuaXuLyEntity entity =
                ketQuaXuLyRepository.findById(maKetQuaXuLyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy kết quả xử lý"
                                )
                        );

        return ketQuaXuLyMapper.toDetailResponse(entity);
    }

    @Override
    public KetQuaXuLyDetailResponse update(
            UpdateKetQuaXuLyRequest request
    ) {

        KetQuaXuLyEntity entity =
                ketQuaXuLyRepository.findById(request.getMaKetQuaXuLy())
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy kết quả xử lý"
                                )
                        );

        if (request.getNoiDungThucHien() != null) {
            entity.setNoiDungThucHien(request.getNoiDungThucHien());
        }

        if (request.getTrangThaiKetQua() != null) {
            entity.setTrangThai(request.getTrangThaiKetQua());
        }

        ketQuaXuLyRepository.save(entity);

        return ketQuaXuLyMapper.toDetailResponse(entity);
    }

}