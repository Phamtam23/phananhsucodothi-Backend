package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.*;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.mapper.KetQuaXuLyMapper;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuPhanCongMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuPhanCong;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class PhieuPhanCongService implements IPhieuPhanCong {

    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final KetQuaXuLyMapper ketQuaXuLyMapper;
    private final SucoRepository sucoRepository;
    private final DonViXuLyRepository donViXuLyRepository;
    private final NhanVienDieuPhoiRepository  nhanVienDieuPhoiRepository;
    private final PhieuPhanCongMapper phieuPhanCongMapper;
    private final TepSuCoRepository tepSuCoRepository;
    private final KetQuaXuLyRepository ketQuaXuLyRepository;
    private final TepKetQuaRepository tepKetQuaRepository;
    private final PhieuDanhGiaRepository phieuDanhGiaRepository;
    private final PhieuMoLaiRepository phieuMoLaiRepository;
    @Override
    public List<PhieuPhanCongResponse> create(
            CreatePhieuPhanCongRequest request,
            String maNhanVienDieuPhoi
    ) {

        SucoEntity sucoEntity = sucoRepository
                .findById(request.getMaSuCo())
                .orElseThrow(() ->
                        new RuntimeException("Không tìm thấy sự cố"));

        sucoEntity.setTrangThai(TrangThaiSuCo.DANG_XU_LY);
        sucoRepository.save(sucoEntity);

        NhanVienDieuPhoiEntity nhanVienDieuPhoiEntity =
                nhanVienDieuPhoiRepository
                        .findById(maNhanVienDieuPhoi)
                        .orElseThrow(() ->
                                new RuntimeException("Không tìm thấy nhân viên điều phối"));

        return request.getMaDonViXuLy()
                .stream()
                .map(maDonVi -> {

                    DonViXuLyEntity donViXuLyEntity =
                            donViXuLyRepository
                                    .findById(maDonVi)
                                    .orElseThrow(() ->
                                            new RuntimeException(
                                                    "Không tìm thấy đơn vị xử lý: " + maDonVi
                                            ));

                    String maPhieuPhanCong =
                            IdGenerator.generateMaPhieuPhanCong(
                                    request.getMaSuCo(),
                                    maDonVi
                            );

                    PhieuPhanCongEntity phieuPhanCongEntity =
                            PhieuPhanCongEntity.builder()
                                    .maPhieuPhanCong(maPhieuPhanCong)
                                    .suCo(sucoEntity)
                                    .donViXuLy(donViXuLyEntity)
                                    .nhanVienDieuPhoi(nhanVienDieuPhoiEntity)
                                    .trangThai(TrangThaiPhanCong.CHO_XAC_NHAN)
                                    .build();

                    return phieuPhanCongRepository.save(
                            phieuPhanCongEntity
                    );

                })
                .map(phieuPhanCongMapper::toResponse)
                .toList();
    }

    @Override
    public PhieuPhanCongResponse update(
            String maPhieuPhanCong,
            UpdatePhieuPhanCongRequest request
    ) {

        PhieuPhanCongEntity phieuPhanCongEntity =
                phieuPhanCongRepository.findById(maPhieuPhanCong)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Không tìm thấy phiếu phân công"
                                )
                        );

        if (request.getTrangThai() != null) {
            phieuPhanCongEntity.setTrangThai(
                    request.getTrangThai()
            );
        }

        if (request.getGhiChu() != null) {
            phieuPhanCongEntity.setGhiChu(
                    request.getGhiChu()
            );
        }

        if (request.getLyDoTuChoi() != null) {
            phieuPhanCongEntity.setLyDoTuChoi(
                    request.getLyDoTuChoi()
            );
        }

        phieuPhanCongRepository.save(phieuPhanCongEntity);

        return phieuPhanCongMapper.toResponse(
                phieuPhanCongEntity
        );
    }

    @Override
    public PhieuPhanCongResponse findById(String ma) {
        PhieuPhanCongEntity phieuPhanCongEntity = phieuPhanCongRepository.findById(ma)
                .orElseThrow(() -> new RuntimeException("Không tìm thâý phiếu phân công"));

        return phieuPhanCongMapper.toResponse (phieuPhanCongEntity);
    }

    @Override
    public PageResponse<PhieuPhanCongResponse> findAllByDonVi(String maDonVi, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());

        Page<PhieuPhanCongEntity> pageResult =
                phieuPhanCongRepository.findAllByDonViXuLy_MaDonViXuLy(maDonVi, pageable);

        List<PhieuPhanCongEntity> list = pageResult.getContent();
        if (list.isEmpty()) {
            return PageResponse.of(Page.empty(pageable));
        }
        List<String> maSuCos = list.stream()
                .map(p -> p.getSuCo().getMaSuCo())
                .distinct()
                .toList();

        List<TepSuCoEntity> medias =
                tepSuCoRepository.findBySuCo_MaSuCoIn(maSuCos);

        Page<PhieuPhanCongResponse> mappedPage = pageResult.map(p ->
                phieuPhanCongMapper.toResponse(p)
        );
        return PageResponse.of(mappedPage);
    }

    @Override
    public List<PhieuPhanCongSCResponse> findAllBySuCo(
            String maSuCo,
            String maNguoiDan
    ) {

        List<PhieuPhanCongEntity> phieuPhanCongs =
                phieuPhanCongRepository.findAllBySuCo_MaSuCo(maSuCo);

        if (phieuPhanCongs.isEmpty()) {
            return Collections.emptyList();
        }

        List<String> maPhieus = phieuPhanCongs.stream()
                .map(PhieuPhanCongEntity::getMaPhieuPhanCong)
                .toList();

        Map<String, KetQuaXuLyDetailResponse> ketQuaTheoPhieu =
                getKetQuaTheoPhieu(phieuPhanCongs);

        Set<String> daDanhGiaSet =
                phieuDanhGiaRepository.findMaPhieuDaDanhGia(
                        maNguoiDan,
                        maPhieus
                );

        Set<String> daMoLaiSet =
                phieuMoLaiRepository.findMaPhieuDaMoLai(
                        maNguoiDan,
                        maPhieus
                );

        return phieuPhanCongs.stream()
                .map(p -> {

                    String maPhieu = p.getMaPhieuPhanCong();

                    boolean laNguoiGui =
                            p.getSuCo()
                                    .getNguoiDan()
                                    .getMaNguoiDan()
                                    .equals(maNguoiDan);

                    boolean coKetQua =
                            ketQuaTheoPhieu.containsKey(maPhieu);

                    boolean daDanhGia =
                            daDanhGiaSet.contains(maPhieu);

                    boolean canDanhGia =
                            laNguoiGui
                                    && coKetQua
                                    && !daDanhGia;

                    boolean daMoLai =
                            daMoLaiSet.contains(maPhieu);

                    boolean canMoLai =
                            laNguoiGui
                                    && coKetQua
                                    && !daMoLai;

                    PhieuTrangThaiResponse trangThai =
                            PhieuTrangThaiResponse.builder()
                                    .canDanhGia(canDanhGia)
                                    .daDanhGia(daDanhGia)
                                    .canMoLai(canMoLai)
                                    .daMoLai(daMoLai)
                                    .build();

                    return phieuPhanCongMapper.toResponse(
                            p,
                            ketQuaTheoPhieu.get(maPhieu),
                            trangThai
                    );
                })
                .toList();
    }

    private Map<String, KetQuaXuLyDetailResponse> getKetQuaTheoPhieu(
            List<PhieuPhanCongEntity> phieuPhanCongs
    ) {

        List<String> maPhieus = phieuPhanCongs.stream()
                .map(PhieuPhanCongEntity::getMaPhieuPhanCong)
                .toList();

        List<KetQuaXuLyEntity> ketQuas =
                ketQuaXuLyRepository.findByPhieuPhanCongIn(maPhieus);

        if (ketQuas.isEmpty()) {
            return Collections.emptyMap();
        }

        Map<String, List<MediaResponse>> tepTheoKetQua =
                getMediaTheoKetQua(ketQuas);

        return ketQuas.stream()
                .collect(Collectors.toMap(

                        k -> k.getChiTietPhanCong()
                                .getPhieuPhanCong()
                                .getMaPhieuPhanCong(),

                        k -> {

                            KetQuaXuLyDetailResponse response =
                                    ketQuaXuLyMapper.toDetailResponse(k);

                            response.setMedias(
                                    tepTheoKetQua.getOrDefault(
                                            k.getMaKetQua(),
                                            Collections.emptyList()
                                    )
                            );

                            return response;
                        },

                        (a, b) -> a
                ));
    }

    private Map<String, List<MediaResponse>> getMediaTheoKetQua(
            List<KetQuaXuLyEntity> ketQuas
    ) {

        List<String> maKetQuas = ketQuas.stream()
                .map(KetQuaXuLyEntity::getMaKetQua)
                .toList();

        List<TepKetQuaEntity> teps =
                tepKetQuaRepository.findAllByKetQua_MaKetQuaIn(maKetQuas);

        return teps.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getKetQua().getMaKetQua(),

                        Collectors.mapping(
                                t -> MediaResponse.builder()
                                        .url(t.getUrl())
                                        .loai(t.getLoai())
                                        .build(),

                                Collectors.toList()
                        )
                ));
    }
}
