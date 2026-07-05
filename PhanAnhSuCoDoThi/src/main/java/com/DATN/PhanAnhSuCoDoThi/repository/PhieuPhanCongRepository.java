package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhieuPhanCongRepository extends JpaRepository<PhieuPhanCongEntity, String> {
    List<PhieuPhanCongEntity> findAllBySuCo_MaSuCoIn(List<String> su_MaSuCo);

    List<PhieuPhanCongEntity> findAllBySuCo_MaSuCo(String su_MaSuCo);

    Page<PhieuPhanCongEntity> findAllByDonViXuLy_MaDonViXuLy(String maDonViXuLy, Pageable pageable);

    @Query("""
    SELECT DISTINCT p FROM PhieuPhanCongEntity p
    JOIN p.suCo s
    LEFT JOIN PhieuPhanLoaiEntity ppl ON ppl.maSuCo = s.maSuCo
    WHERE p.nhanVienDieuPhoi.maNhanVienDieuPhoi = :maNhanVien
      AND (:tuNgay IS NULL OR CAST(p.thoiGianTao AS date) >= :tuNgay)
      AND (:denNgay IS NULL OR CAST(p.thoiGianTao AS date) <= :denNgay)
      AND (:maDonVi IS NULL OR p.donViXuLy.maDonViXuLy = :maDonVi)
      AND (:maLoai IS NULL OR ppl.maLoaiSuCo = :maLoai)
    """)
    Page<PhieuPhanCongEntity> findAllByFilter(
            @Param("maNhanVien") String maNhanVien,
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            @Param("maDonVi") String maDonVi,
            @Param("maLoai") String maLoai,
            Pageable pageable
    );

    long countByDonViXuLy_MaDonViXuLy(String maDonViXuLy);

    @Query("SELECT COUNT(p) FROM PhieuPhanCongEntity p WHERE p.donViXuLy.maDonViXuLy = :maDonVi AND YEAR(p.thoiGianTao) = :nam")
    long countTongSuCoTrongNam(@Param("maDonVi") String maDonVi, @Param("nam") int nam);

    @Query("SELECT MONTH(p.thoiGianTao), COUNT(p) FROM PhieuPhanCongEntity p WHERE p.donViXuLy.maDonViXuLy = :maDonVi AND YEAR(p.thoiGianTao) = :nam GROUP BY MONTH(p.thoiGianTao)")
    List<Object[]> countSuCoTheoThang(@Param("maDonVi") String maDonVi, @Param("nam") int nam);
}

