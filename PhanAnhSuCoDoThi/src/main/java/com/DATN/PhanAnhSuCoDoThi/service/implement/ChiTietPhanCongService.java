package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.CreateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.UpdateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import com.DATN.PhanAnhSuCoDoThi.mapper.ChiTietPhanCongMapper;
import com.DATN.PhanAnhSuCoDoThi.repository.ChiTietPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.NhanVienDonViRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.PhieuPhanCongRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IChiTietPhanCong;
import com.DATN.PhanAnhSuCoDoThi.util.IdGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.time.LocalDateTime;

@RequiredArgsConstructor
public class ChiTietPhanCongService implements IChiTietPhanCong {

    ChiTietPhanCongRepository chiTietPhanCongRepository;
    ChiTietPhanCongMapper chiTietPhanCongMapper;
    PhieuPhanCongRepository phieuPhanCongRepository;
    NhanVienDonViRepository nhanVienDonViRepository;

    @Override
    public PageResponse<ChiTietPhanCongEntity> FindAllByNhanVienXuLy(String nhanVienXuLy, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("thoiGianTao").descending());
        PageResponse<ChiTietPhanCongEntity> chiTietPhanCongEntityPageResponse = chiTietPhanCongRepository.findByNhanVienXuLy_maNhanVienXuLy(nhanVienXuLy, pageable);

        return chiTietPhanCongEntityPageResponse;
    }

    @Override
    public ChiTietPhanCongResponse FindById(String maChiTietPhanCong) {

        ChiTietPhanCongEntity  chiTietPhanCong = chiTietPhanCongRepository.findById(maChiTietPhanCong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy chi tiết phân công"));
        return chiTietPhanCongMapper.toResponse(chiTietPhanCong);
    }

    @Override
    public ChiTietPhanCongResponse Create(CreateChiTietPhanCongRequest createChiTietPhanCongRequest, String maTruongDonVi) {

        PhieuPhanCongEntity phieuPhanCongEntity = new PhieuPhanCongEntity();

        phieuPhanCongEntity = phieuPhanCongRepository.findById(createChiTietPhanCongRequest.getMaPhieuPhanCong())
                .orElseThrow(()-> new RuntimeException("Không tìm thấy phiếu phân công"));

        NhanVienDonViEntity nhanVienDonViEntity = new NhanVienDonViEntity();

        nhanVienDonViEntity = nhanVienDonViRepository.findById(createChiTietPhanCongRequest.getMaNhanVienXuLy())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên xử lý"));

        ChiTietPhanCongEntity chiTietPhanCongEntity  = new ChiTietPhanCongEntity();
        chiTietPhanCongEntity.setMaChiTietPhanCong(IdGenerator.geMaChiTietPhanCong(createChiTietPhanCongRequest.getMaPhieuPhanCong(), maTruongDonVi));
        chiTietPhanCongEntity.setTrangThai(TrangThaiChiTietPhanCong.DANGCHO);
        chiTietPhanCongEntity.setThoiGianTao(LocalDateTime.now());
        chiTietPhanCongEntity.setPhieuPhanCong(phieuPhanCongEntity);
        chiTietPhanCongEntity.setNhanVienXuLy(nhanVienDonViEntity);
        chiTietPhanCongRepository.save(chiTietPhanCongEntity);

        return chiTietPhanCongMapper.toResponse(chiTietPhanCongEntity);
    }

    @Override
    public ChiTietPhanCongResponse update(UpdateChiTietPhanCongRequest updateChiTietPhanCongRequest, String maChiTietPhanCong) {

        ChiTietPhanCongEntity chiTietPhanCongEntity = new ChiTietPhanCongEntity();
        chiTietPhanCongEntity = chiTietPhanCongRepository.findById(maChiTietPhanCong)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bản chi tiết phân công"));

        chiTietPhanCongEntity.setTrangThai(updateChiTietPhanCongRequest.getTrangThai());

        return chiTietPhanCongMapper.toResponse(chiTietPhanCongEntity);

    }

}
