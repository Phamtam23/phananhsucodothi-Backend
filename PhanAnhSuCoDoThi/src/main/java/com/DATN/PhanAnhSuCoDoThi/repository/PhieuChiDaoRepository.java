package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuChiDaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PhieuChiDaoRepository extends JpaRepository<PhieuChiDaoEntity, String> {

  Optional<PhieuChiDaoEntity> findByMaChiDaoAndDeletedAtIsNull(String maChiDao);

    Page<PhieuChiDaoEntity>
    findByTruongDonVi_MaNhanVien(String maNhanVien, Pageable pageable);

    List<PhieuChiDaoEntity> findByChiTietPhanCong_MaChiTietPhanCongAndDeletedAtIsNull(String maChiTietPhanCong);
}
