package com.DATN.PhanAnhSuCoDoThi.repository;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ChiTietPhanCongRepository extends JpaRepository<ChiTietPhanCongEntity,String> {

    Page<ChiTietPhanCongEntity>
    findByNhanVienXuLy_MaNhanVien(
            String maNhanVien,
            Pageable pageable
    );

    @Query("""
    SELECT c FROM ChiTietPhanCongEntity c
    JOIN c.phieuPhanCong p
    JOIN p.suCo s
    WHERE c.nhanVienXuLy.maNhanVien = :maNhanVien
      AND (:keyword IS NULL OR
           LOWER(s.tieuDe) LIKE LOWER(CONCAT('%', :keyword, '%')))
      AND (:tuNgay IS NULL OR CAST(c.thoiGianTao AS date) >= :tuNgay)
      AND (:denNgay IS NULL OR CAST(c.thoiGianTao AS date) <= :denNgay)
    """)
    Page<ChiTietPhanCongEntity> findAllByFilter(
            @Param("maNhanVien") String maNhanVien,
            @Param("keyword") String keyword,
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            Pageable pageable
    );

    List<ChiTietPhanCongEntity> findByPhieuPhanCong_MaPhieuPhanCong(String maPhieuPhanCong);

    @Query("SELECT nv.taiKhoan.hoTen, COUNT(c) FROM NhanVienDonViEntity nv LEFT JOIN ChiTietPhanCongEntity c ON c.nhanVienXuLy.maNhanVien = nv.maNhanVien AND c.trangThai = :trangThai WHERE nv.donVi.maDonViXuLy = :maDonVi GROUP BY nv.taiKhoan.hoTen")
    List<Object[]> countHoanThanhByNhanVien(@Param("maDonVi") String maDonVi, @Param("trangThai") com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong trangThai);
}
