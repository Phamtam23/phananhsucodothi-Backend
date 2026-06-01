package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienChucVuEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDieuPhoiEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface NhanVienChucVuRepsitory extends JpaRepository<NhanVienChucVuEntity,String> {

    Optional<NhanVienChucVuEntity> findByNhanVien_MaNhanVienAndNgayKetThucIsNull(String maNhanVien);
}
