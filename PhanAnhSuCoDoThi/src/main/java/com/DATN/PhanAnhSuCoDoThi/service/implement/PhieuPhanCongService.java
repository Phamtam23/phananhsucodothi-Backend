package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.CreatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong.UpdatePhieuPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuKiemDuyetResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
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

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PhieuPhanCongService implements IPhieuPhanCong {

    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final SucoRepository sucoRepository;
    private final DonViXuLyRepository donViXuLyRepository;
    private final NhanVienDieuPhoiRepository  nhanVienDieuPhoiRepository;
    private final PhieuPhanCongMapper phieuPhanCongMapper;
    private final TepSuCoRepository tepSuCoRepository;
    @Override
    public PhieuPhanCongResponse create(CreatePhieuPhanCongRequest createPhieuPhanCongRequest, String maNhanVienDieuPhoi) {
        PhieuPhanCongEntity phieuPhanCongEntity = new PhieuPhanCongEntity();
        SucoEntity sucoEntity = sucoRepository
                .findById(createPhieuPhanCongRequest.getMaSuCo())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy sự cố"));
        DonViXuLyEntity donViXuLyEntity = donViXuLyRepository
                .findById(createPhieuPhanCongRequest.getMaDonViXuLy())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn vị xử lý"));
        NhanVienDieuPhoiEntity nhanVienDieuPhoiEntity = nhanVienDieuPhoiRepository
                .findById(maNhanVienDieuPhoi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên điều phối"));

        String maPhieuPhanCong = IdGenerator.generateMaPhieuPhanCong(createPhieuPhanCongRequest.getMaSuCo(),createPhieuPhanCongRequest.getMaDonViXuLy());
        phieuPhanCongEntity.setMaPhieuPhanCong(maPhieuPhanCong);
        phieuPhanCongEntity.setSuCo(sucoEntity);
        phieuPhanCongEntity.setDonViXuLy(donViXuLyEntity);
        phieuPhanCongEntity.setNhanVienDieuPhoi(nhanVienDieuPhoiEntity);
        phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.CHO_XAC_NHAN);
        phieuPhanCongRepository.save(phieuPhanCongEntity);
        return phieuPhanCongMapper.toDetail (phieuPhanCongEntity,null);
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

        return phieuPhanCongMapper.toDetail(
                phieuPhanCongEntity,
                null
        );
    }

    @Override
    public PhieuPhanCongResponse findById(String ma) {
        PhieuPhanCongEntity phieuPhanCongEntity = phieuPhanCongRepository.findById(ma)
                .orElseThrow(() -> new RuntimeException("Không tìm thâý phiếu phân công"));
        List<TepSuCoEntity> medias =
                tepSuCoRepository.findBySuCo_MaSuCo(phieuPhanCongEntity.getSuCo().getMaSuCo());

        return phieuPhanCongMapper.toDetail (phieuPhanCongEntity,medias);
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

        Map<String, List<TepSuCoEntity>> mediaMap = medias.stream()
                .collect(Collectors.groupingBy(m -> m.getSuCo().getMaSuCo()));

        Page<PhieuPhanCongResponse> mappedPage = pageResult.map(p ->
                phieuPhanCongMapper.toDetail(
                        p,
                        mediaMap.getOrDefault(p.getSuCo().getMaSuCo(), List.of())
                )
        );
        return PageResponse.of(mappedPage);
    }
}
