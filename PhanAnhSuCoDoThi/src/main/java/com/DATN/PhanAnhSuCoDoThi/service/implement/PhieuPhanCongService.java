package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCongFilterRequest;
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

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class PhieuPhanCongService implements IPhieuPhanCong {

    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final SucoRepository sucoRepository;
    private final DonViXuLyRepository donViXuLyRepository;
    private final NhanVienDieuPhoiRepository  nhanVienDieuPhoiRepository;
    private final PhieuPhanCongMapper phieuPhanCongMapper;
    private final TepSuCoRepository tepSuCoRepository;
    private final PhieuDanhGiaRepository phieuDanhGiaRepository;
    private final KetQuaXuLyService ketQuaXuLyService;
    private final PhieuMoLaiRepository  phieuMoLaiRepository;
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
                                    .thoiGianTao(LocalDateTime.now())
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

        List<String> maPhanCongs = phieuPhanCongs.stream()
                .map(PhieuPhanCongEntity::getMaPhieuPhanCong)
                .toList();

        Set<String> daDanhGiaSet = phieuDanhGiaRepository
                .findByPhanCongsAndNguoiDan(maPhanCongs, maNguoiDan)
                .stream()
                .map(pd -> pd.getKetQuaXuLy()
                        .getChiTietPhanCong()
                        .getPhieuPhanCong()
                        .getMaPhieuPhanCong())
                .collect(Collectors.toSet());

        Set<String> daMoLaiSet = phieuMoLaiRepository
                .findByPhanCongsAndNguoiDan(maPhanCongs, maNguoiDan)
                .stream()
                .map(pd -> pd.getKetQuaXuLy()
                        .getChiTietPhanCong()
                        .getPhieuPhanCong()
                        .getMaPhieuPhanCong())
                .collect(Collectors.toSet());

        Map<String, List<KetQuaXuLyDetailResponse>> ketQuaMap =
                ketQuaXuLyService.findByPhanCong(maPhanCongs);

        return phieuPhanCongs.stream()
                .map(pc -> {
                    PhieuPhanCongSCResponse response = phieuPhanCongMapper.toResponseSC(pc);
                    if (response == null) return null;

                    String maPc = pc.getMaPhieuPhanCong();
                    boolean daDanhGia = daDanhGiaSet.contains(maPc);
                    boolean daMoLai = daMoLaiSet.contains(maPc);
                    List<KetQuaXuLyDetailResponse> ketQuas =
                            ketQuaMap.getOrDefault(maPc, Collections.emptyList());

                    PhieuTrangThaiResponse trangThaiResponse = PhieuTrangThaiResponse.builder()
                            .canDanhGia(!daDanhGia && !ketQuas.isEmpty())
                            .daDanhGia(daDanhGia)
                            .canMoLai(!daMoLai && !ketQuas.isEmpty())
                            .daMoLai(daMoLai)
                            .build();

                    response.setKetQuaXuLyDetailResponse(ketQuas);
                    response.setPhieuTrangThaiResponse(trangThaiResponse);

                    return response;
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public PageResponse<PhieuPhanCongLSResponse> findAllByNhanVien(
            String maNhanVien,
            PhieuPhanCongFilterRequest request
    ) {
        Pageable pageable = PageRequest.of(
                request.getPage(),
                request.getSize(),
                Sort.by("thoiGianTao").descending()
        );

        Page<PhieuPhanCongEntity> pageResult = phieuPhanCongRepository.findAllByFilter(
                maNhanVien,
                request.getTuNgay(),
                request.getDenNgay(),
                request.getMaDonVi(),
                request.getMaLoai(),
                pageable
        );

        return PageResponse.of(pageResult.map(phieuPhanCongMapper::toLSResponse));
    }


}
