package com.DATN.PhanAnhSuCoDoThi.respository;

import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NhanVienDonViRepository extends JpaRepository<NhanVienDonViEntity,String> {

    Optional<NhanVienDonViEntity> findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);
}

