package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.CreateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao.UpdateChiDaoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuChiDaoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuChiDaoEntity;
import com.DATN.PhanAnhSuCoDoThi.mapper.PhieuChiDaoMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.ChiTietPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDonViRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuChiDaoRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IPhieuChiDaoService;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class PhieuChiDaoServiceService implements IPhieuChiDaoService {
    private final PhieuChiDaoRepository phieuChiDaoRepository;
    private final PhieuChiDaoMapper  phieuChiDaoMapper;
    private final NhanVienDonViRepository nhanVienDonViRepository;
    private final ChiTietPhanCongRepository chiTietPhanCongRepository;

    @Override
    public PhieuChiDaoResponse create(CreateChiDaoRequest createChiDaoRequest,String maTruongDonVi) {
        ChiTietPhanCongEntity chiTietPhanCongEntity = chiTietPhanCongRepository.findById(createChiDaoRequest.getMaChiTietPhanCong())
                .orElseThrow(() -> new RuntimeException("Không timg thấy chi tiết phân công"));

        NhanVienDonViEntity truongDonViEntity = nhanVienDonViRepository.findById(maTruongDonVi)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy trưởng đơn vị"));

        PhieuChiDaoEntity phieuChiDaoEntity = new PhieuChiDaoEntity();

        phieuChiDaoEntity.setMaChiDao(IdGenerator.generateMaChiDao(createChiDaoRequest.getMaChiTietPhanCong()));
        phieuChiDaoEntity.setTruongDonVi(truongDonViEntity);
        phieuChiDaoEntity.setChiTietPhanCong(chiTietPhanCongEntity);
        phieuChiDaoEntity.setNoiDung(createChiDaoRequest.getNoiDung());
        phieuChiDaoEntity.setNgayChiDao(LocalDate.now());
        phieuChiDaoRepository.save(phieuChiDaoEntity);

        return phieuChiDaoMapper.toResponse(phieuChiDaoEntity);
    }

    @Override
    public PhieuChiDaoResponse update(String maChiDao, UpdateChiDaoRequest updateChiDaoRequest) {
        PhieuChiDaoEntity phieuChiDaoEntity = phieuChiDaoRepository.findByMaChiDaoAndDeletedAtIsNull(maChiDao)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chỉ đạo"));
        phieuChiDaoEntity.setNoiDung(updateChiDaoRequest.getNoiDung());

        return phieuChiDaoMapper.toResponse(phieuChiDaoEntity);
    }

    @Override
    public String deleteById(String maChiDao) {
        PhieuChiDaoEntity phieuChiDaoEntity = phieuChiDaoRepository.findByMaChiDaoAndDeletedAtIsNull(maChiDao)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chỉ đạo"));
        phieuChiDaoEntity.setDeletedAt(LocalDateTime.now());

        return "Xóa thành công";
    }

    @Override
    public PhieuChiDaoResponse findById(String maChiDao) {
        PhieuChiDaoEntity phieuChiDaoEntity = phieuChiDaoRepository.findByMaChiDaoAndDeletedAtIsNull(maChiDao)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chỉ đạo"));
        return phieuChiDaoMapper.toResponse(phieuChiDaoEntity);
    }

    @Override
    public Page<PhieuChiDaoResponse> findByChiTietPhanCong(String maChiTietPhanCong, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<PhieuChiDaoEntity> pageResult = phieuChiDaoRepository.findByChiTietPhanCong_MaChiTietPhanCongAndDeletedAtIsNull(
                maChiTietPhanCong,
                pageable);

        Page<PhieuChiDaoResponse> mapper = pageResult.map(phieuChiDaoEntity ->  phieuChiDaoMapper.toResponse(phieuChiDaoEntity));

        return mapper;

    }
}
