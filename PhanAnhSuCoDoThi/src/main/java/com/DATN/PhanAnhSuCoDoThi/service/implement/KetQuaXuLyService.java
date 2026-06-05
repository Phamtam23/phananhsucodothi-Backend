package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.CreateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy.UpdateKetQuaXuLyRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLySummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKetQua;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.mapper.KetQuaXuLyMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.IKetQuaXuLyService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class KetQuaXuLyService implements IKetQuaXuLyService {

    private final KetQuaXuLyRepository ketQuaXuLyRepository;
    private final ChiTietPhanCongRepository chiTietPhanCongRepository;
    private final KetQuaXuLyMapper ketQuaXuLyMapper;
    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final TepKetQuaRepository tepKetQuaRepository;
    private final TepKetQuaService tepKetQuaService;
    private final SucoRepository sucoRepository;
    @Override
    @Transactional(readOnly = true)
    public List<KetQuaXuLyDetailResponse> findByChiTietPhanCong(
            String maChiTietPhanCong
    ) {

        List<KetQuaXuLyEntity> entities =
                ketQuaXuLyRepository
                        .findByChiTietPhanCong_MaChiTietPhanCong(
                                maChiTietPhanCong
                        );

        if (entities.isEmpty()) {
            return List.of();
        }

        List<KetQuaXuLyDetailResponse> ketQuaXuLyDetailResponses = entities.stream()
                .map(kq -> {
                            KetQuaXuLyDetailResponse res = ketQuaXuLyMapper.toDetailResponse(kq);
                            if (res != null)
                                res.setMedias(tepKetQuaService.getMediasByMaKetQua(kq.getMaKetQua()));
                            return res;
                }).toList();


        return ketQuaXuLyDetailResponses;
    }

    public Map<String, List<KetQuaXuLyDetailResponse>> findByPhanCong(
           List<String> maPhanCongs
    ) {
        List<KetQuaXuLyEntity> ketQuaXuLyEntities = ketQuaXuLyRepository.findByPhieuPhanCongIn(maPhanCongs);
        if (ketQuaXuLyEntities.isEmpty()) {
            return Map.of();
        }

        List<String> maketQuas = ketQuaXuLyEntities.stream()
                .map(KetQuaXuLyEntity::getMaKetQua)
                .toList();

        Map<String,List<MediaResponse>> mediaMap = tepKetQuaService.getMediasByKetQuas(maketQuas);

        return ketQuaXuLyEntities.stream()
                .collect(Collectors.groupingBy(

                        entity -> entity.getChiTietPhanCong().getPhieuPhanCong().getMaPhieuPhanCong(),

                        Collectors.mapping(

                                entity -> {
                                    KetQuaXuLyDetailResponse response = ketQuaXuLyMapper.toDetailResponse(entity);
                                    response.setMedias(
                                            mediaMap != null
                                                    ? mediaMap.getOrDefault(entity.getMaKetQua(), Collections.emptyList())
                                                    : Collections.emptyList()
                                    );
                                    return response;
                                },

                                Collectors.toList()
                        )
                ));
    }


    @Override
    @Transactional
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

        List<KetQuaXuLyEntity> existingEntities = ketQuaXuLyRepository
                .findByChiTietPhanCong_MaChiTietPhanCong(request.getMaChiTietPhanCong());
        if (existingEntities != null && !existingEntities.isEmpty()) {
            for (KetQuaXuLyEntity oldEntity : existingEntities) {
                List<TepKetQuaEntity> teps = tepKetQuaRepository.findAllByKetQua_MaKetQuaIn(List.of(oldEntity.getMaKetQua()));
                tepKetQuaRepository.deleteAll(teps);
                ketQuaXuLyRepository.delete(oldEntity);
            }
            ketQuaXuLyRepository.flush();
        }

        KetQuaXuLyEntity entity = new KetQuaXuLyEntity();

        entity.setMaKetQua(IdGenerator.generateMaKetQuaXuLy(request.getMaChiTietPhanCong()));
        entity.setChiTietPhanCong(chiTietPhanCong);
        entity.setThoiGianNop(LocalDateTime.now());
        entity.setTrangThai(TrangThaiKetQua.CHO_DUYET);
        entity.setNoiDungThucHien(request.getNoiDungThucHien());

        chiTietPhanCong.setTrangThai(TrangThaiChiTietPhanCong.CHO_DUYET);
        ketQuaXuLyRepository.save(entity);

        PhieuPhanCongEntity phieuPhanCongEntity = chiTietPhanCong.getPhieuPhanCong();
        phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.CHO_DUYET_KET_QUA);

        phieuPhanCongRepository.save(phieuPhanCongEntity);

        if (request.getMediaUrls() != null && !request.getMediaUrls().isEmpty()) {
            for (String url : request.getMediaUrls()) {
                TepKetQuaEntity tep = new TepKetQuaEntity();
                tep.setMaTepKetQua(IdGenerator.generateMaTep());
                tep.setKetQua(entity);
                tep.setUrl(url);
                String loai = "IMAGE";
                if (url.toLowerCase().matches(".*\\.(mp4|mov|avi|wmv|flv|mkv|webm)$")) {
                    loai = "VIDEO";
                }
                tep.setLoai(loai);
                tepKetQuaRepository.save(tep);
            }
        }

        KetQuaXuLyDetailResponse response = ketQuaXuLyMapper.toDetailResponse(entity);
        if (response != null && request.getMediaUrls() != null) {
            response.setMedias(request.getMediaUrls().stream()
                    .map(url -> MediaResponse.builder()
                            .url(url)
                            .loai(url.toLowerCase().matches(".*\\.(mp4|mov|avi|wmv|flv|mkv|webm)$") ? "VIDEO" : "IMAGE")
                            .build())
                    .toList());
        }

        return response;
    }

    @Override
    @org.springframework.transaction.annotation.Transactional(readOnly = true)
    public KetQuaXuLyDetailResponse findById(String maKetQuaXuLyId) {

        KetQuaXuLyEntity entity =
                ketQuaXuLyRepository.findById(maKetQuaXuLyId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy kết quả xử lý"
                                )
                        );

        KetQuaXuLyDetailResponse response = ketQuaXuLyMapper.toDetailResponse(entity);
        if (response != null) {
            List<TepKetQuaEntity> teps = tepKetQuaRepository.findAllByKetQua_MaKetQuaIn(List.of(entity.getMaKetQua()));
            response.setMedias(teps.stream()
                    .map(t -> MediaResponse.builder()
                            .url(t.getUrl())
                            .loai(t.getLoai())
                            .build())
                    .toList());
        }
        return response;
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

            if (request.getTrangThaiKetQua()== TrangThaiKetQua.DA_DUYET) {
                ChiTietPhanCongEntity chiTietPhanCongEntity = entity.getChiTietPhanCong();
                chiTietPhanCongEntity.setTrangThai(TrangThaiChiTietPhanCong.HOAN_THANH);
                chiTietPhanCongRepository.save(chiTietPhanCongEntity);

               PhieuPhanCongEntity phieuPhanCongEntity = chiTietPhanCongEntity.getPhieuPhanCong();
               phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.HOAN_THANH);
               phieuPhanCongRepository.save(phieuPhanCongEntity);
            }

            if (request.getTrangThaiKetQua()== TrangThaiKetQua.TU_CHOI) {
                ChiTietPhanCongEntity chiTietPhanCongEntity = entity.getChiTietPhanCong();
            }
        }

        KetQuaXuLyDetailResponse response = ketQuaXuLyMapper.toDetailResponse(entity);
        if (response != null) {
            List<TepKetQuaEntity> teps = tepKetQuaRepository.findAllByKetQua_MaKetQuaIn(List.of(entity.getMaKetQua()));
            response.setMedias(teps.stream()
                    .map(t -> MediaResponse.builder()
                            .url(t.getUrl())
                            .loai(t.getLoai())
                            .build())
                    .toList());
        }
        return response;
    }

    @Override
    public KetQuaXuLyDetailResponse duyetKetQua(String maKetQua, boolean isApproved, String lyDoTuChoi) {
        KetQuaXuLyEntity entity = ketQuaXuLyRepository.findById(maKetQua)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy kết quả xử lý"));
        ChiTietPhanCongEntity chiTietPhanCongEntity = entity.getChiTietPhanCong();
        PhieuPhanCongEntity phieuPhanCongEntity = chiTietPhanCongEntity.getPhieuPhanCong();
        if (isApproved) {
            entity.setTrangThai(TrangThaiKetQua.DA_DUYET);
            chiTietPhanCongEntity.setTrangThai(TrangThaiChiTietPhanCong.HOAN_THANH);
            phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.HOAN_THANH);
            SucoEntity sucoEntity = entity.getChiTietPhanCong().getPhieuPhanCong().getSuCo();
            sucoEntity.setTrangThai(TrangThaiSuCo.DA_XU_LY);
            sucoRepository.save(sucoEntity);
        } else {
            entity.setTrangThai(TrangThaiKetQua.TU_CHOI);
            entity.setLyDoTuChoi(lyDoTuChoi);
            chiTietPhanCongEntity.setTrangThai(TrangThaiChiTietPhanCong.TU_CHOI);
            phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.TU_CHOI);
        }
        chiTietPhanCongRepository.save(chiTietPhanCongEntity);
        ketQuaXuLyRepository.save(entity);

        KetQuaXuLyDetailResponse response = ketQuaXuLyMapper.toDetailResponse(entity);
        if (response != null) {
            List<TepKetQuaEntity> teps = tepKetQuaRepository.findAllByKetQua_MaKetQuaIn(List.of(entity.getMaKetQua()));
            response.setMedias(teps.stream()
                    .map(t -> MediaResponse.builder()
                            .url(t.getUrl())
                            .loai(t.getLoai())
                            .build())
                    .toList());
        }
        return response;
    }
}