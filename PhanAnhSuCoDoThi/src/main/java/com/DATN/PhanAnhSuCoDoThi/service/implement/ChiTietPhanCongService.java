package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.CreateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.UpdateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongLSResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.NhanVienDonVi.NhanVienDonViResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import com.DATN.PhanAnhSuCoDoThi.mapper.ChiTietPhanCongMapper;
import com.DATN.PhanAnhSuCoDoThi.mapper.NhanVienDonViMapper;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuPhanCongMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.ChiTietPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDonViRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IChiTietPhanCongService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@Service
@RequiredArgsConstructor
@org.springframework.transaction.annotation.Transactional
public class ChiTietPhanCongService implements IChiTietPhanCongService {

    private final ChiTietPhanCongRepository chiTietPhanCongRepository;
    private final ChiTietPhanCongMapper chiTietPhanCongMapper;
    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final NhanVienDonViRepository nhanVienDonViRepository;
    private final NhanVienDonViMapper nhanVienDonViMapper;
    private final PhieuPhanCongMapper  phieuPhanCongMapper;
    @Override
    public PageResponse<ChiTietPhanCongLSResponse> FindAllByNhanVienXuLy(
            String nhanVienXuLy,
            String keyword,
            LocalDate tuNgay,
            LocalDate denNgay,
            int page,
            int size
    ) {

        Pageable pageable = PageRequest.of(
                page,
                size,
                Sort.by("thoiGianTao").descending()
        );

        Page<ChiTietPhanCongEntity> pageResult =
                chiTietPhanCongRepository
                        .findAllByFilter(
                                nhanVienXuLy,
                                keyword,
                                tuNgay,
                                denNgay,
                                pageable
                        );

        Page<ChiTietPhanCongLSResponse> responsePage = pageResult.map(entity -> {

            NhanVienDonViResponse nv = nhanVienDonViMapper.toResponsePC(entity.getNhanVienXuLy());
            PhieuPhanCongResponse ppc = phieuPhanCongMapper.toResponse(entity.getPhieuPhanCong());
            return chiTietPhanCongMapper.toLSResponse(entity, ppc, nv);
        });

        return PageResponse.of(responsePage);
    }

    @Override
    public ChiTietPhanCongResponse FindById(String maChiTietPhanCong) {

        ChiTietPhanCongEntity  chiTietPhanCong = chiTietPhanCongRepository.findById(maChiTietPhanCong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết phân công"));

        NhanVienDonViResponse nhanVienDonViResponse = nhanVienDonViMapper.toResponsePC(chiTietPhanCong.getNhanVienXuLy());

        PhieuPhanCongResponse phieuPhanCongResponse = phieuPhanCongMapper.toResponse(chiTietPhanCong.getPhieuPhanCong());

        return chiTietPhanCongMapper.toResponse(chiTietPhanCong,phieuPhanCongResponse,nhanVienDonViResponse);
    }

    @Override
    public ChiTietPhanCongResponse Create(CreateChiTietPhanCongRequest createChiTietPhanCongRequest, String maTruongDonVi) {

        PhieuPhanCongEntity phieuPhanCongEntity = phieuPhanCongRepository.findById(createChiTietPhanCongRequest.getMaPhieuPhanCong())
                .orElseThrow(()-> new RuntimeException("Không tìm thấy phiếu phân công"));

        NhanVienDonViEntity nhanVienDonViEntity = new NhanVienDonViEntity();

        nhanVienDonViEntity = nhanVienDonViRepository.findById(createChiTietPhanCongRequest.getMaNhanVienXuLy())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên xử lý"));

        ChiTietPhanCongEntity chiTietPhanCongEntity  = new ChiTietPhanCongEntity();
        chiTietPhanCongEntity.setMaChiTietPhanCong(IdGenerator.geMaChiTietPhanCong(createChiTietPhanCongRequest.getMaPhieuPhanCong(), maTruongDonVi));
        chiTietPhanCongEntity.setTrangThai(TrangThaiChiTietPhanCong.DANG_XU_LY);
        chiTietPhanCongEntity.setThoiGianTao(LocalDateTime.now());
        chiTietPhanCongEntity.setPhieuPhanCong(phieuPhanCongEntity);
        chiTietPhanCongEntity.setNhanVienXuLy(nhanVienDonViEntity);
        chiTietPhanCongRepository.save(chiTietPhanCongEntity);

        phieuPhanCongEntity.setTrangThai(TrangThaiPhanCong.DANG_XU_LY);
        phieuPhanCongRepository.save(phieuPhanCongEntity);


        NhanVienDonViResponse nhanVienDonViResponse = nhanVienDonViMapper.toResponsePC(chiTietPhanCongEntity.getNhanVienXuLy());

        PhieuPhanCongResponse phieuPhanCongResponse = phieuPhanCongMapper.toResponse(chiTietPhanCongEntity.getPhieuPhanCong());

        return chiTietPhanCongMapper.toResponse(chiTietPhanCongEntity, phieuPhanCongResponse, nhanVienDonViResponse);
    }

    @Override
    public ChiTietPhanCongResponse update(UpdateChiTietPhanCongRequest updateChiTietPhanCongRequest, String maChiTietPhanCong) {

        ChiTietPhanCongEntity chiTietPhanCongEntity = chiTietPhanCongRepository.findById(maChiTietPhanCong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản chi tiết phân công"));

        chiTietPhanCongEntity.setTrangThai(updateChiTietPhanCongRequest.getTrangThai());
        chiTietPhanCongEntity = chiTietPhanCongRepository.save(chiTietPhanCongEntity);

        return chiTietPhanCongMapper.toResponse(chiTietPhanCongEntity,phieuPhanCongMapper.toResponse(chiTietPhanCongEntity.getPhieuPhanCong()),nhanVienDonViMapper.toResponsePC(chiTietPhanCongEntity.getNhanVienXuLy()));

    }
    
    @Override
    public List<ChiTietPhanCongResponse> FindAllByPhanCongId(String maPhieuPhanCong) {
            List<ChiTietPhanCongEntity> list = chiTietPhanCongRepository.findByPhieuPhanCong_MaPhieuPhanCong(maPhieuPhanCong);

        return list.stream().map(chiTietPhanCongEntity -> {
            NhanVienDonViResponse nhanVienDonViResponse = nhanVienDonViMapper.toResponsePC(chiTietPhanCongEntity.getNhanVienXuLy());
            PhieuPhanCongResponse phieuPhanCongResponse = phieuPhanCongMapper.toResponse(chiTietPhanCongEntity.getPhieuPhanCong());
            return chiTietPhanCongMapper.toResponse(chiTietPhanCongEntity, phieuPhanCongResponse, nhanVienDonViResponse);
        }).collect(toList());
    }

}
