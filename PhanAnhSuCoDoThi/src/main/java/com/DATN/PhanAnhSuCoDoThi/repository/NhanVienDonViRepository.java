package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.NhanVienDonViEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NhanVienDonViRepository extends JpaRepository<NhanVienDonViEntity,String> {

    Optional<NhanVienDonViEntity> findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);
    @Query("""
    SELECT nv
    FROM NhanVienDonViEntity nv
    JOIN NhanVienChucVuEntity nvcv
        ON nvcv.nhanVien.maNhanVien = nv.maNhanVien
    JOIN ChucVuEntity cv
        ON cv.maChucVu = nvcv.chucVu.maChucVu
    WHERE nv.donVi.maDonViXuLy = :maDonVi
        AND (
            nvcv.ngayKetThuc IS NULL
            OR nvcv.ngayKetThuc > CURRENT_DATE
        )
        AND cv.maChucVu = 'C_NDVXL000'
""")
    List<NhanVienDonViEntity> findNhanVienDangHoatDong(
            @Param("maDonVi") String maDonVi
    );

    @Query("""
    SELECT nv
    FROM NhanVienDonViEntity nv
    JOIN NhanVienChucVuEntity nvcv
        ON nvcv.nhanVien.maNhanVien = nv.maNhanVien
    JOIN ChucVuEntity cv
        ON cv.maChucVu = nvcv.chucVu.maChucVu
    WHERE nv.donVi.maDonViXuLy = :maDonVi
        AND (
            nvcv.ngayKetThuc IS NULL
            OR nvcv.ngayKetThuc > CURRENT_DATE
        )
""")
    List<NhanVienDonViEntity> findNhanVienDangHoatDongDonVi(
            @Param("maDonVi") String maDonVi
    );
}

