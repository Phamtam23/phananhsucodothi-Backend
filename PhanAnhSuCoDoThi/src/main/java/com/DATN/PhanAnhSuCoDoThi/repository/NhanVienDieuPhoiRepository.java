package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDieuPhoiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienDieuPhoiRepository extends JpaRepository<NhanVienDieuPhoiEntity,String> {

    Optional<NhanVienDieuPhoiEntity> findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);
}
