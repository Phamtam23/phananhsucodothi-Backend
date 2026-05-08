package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuChiDaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PhieuChiDaoRepository extends JpaRepository<PhieuChiDaoEntity, String> {

  Optional<PhieuChiDaoEntity> findByIdAndDeletedAtIsNull(String maChiDao);

  Page<PhieuChiDaoEntity> findByNhanVienDonVi_maNhanVienXuLy(String maNhanVienXuLy, Pageable pageable);
}
