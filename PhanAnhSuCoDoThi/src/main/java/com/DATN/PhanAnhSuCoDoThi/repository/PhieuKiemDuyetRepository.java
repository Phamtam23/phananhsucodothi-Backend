package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuKiemDuyetEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PhieuKiemDuyetRepository extends JpaRepository<PhieuKiemDuyetEntity,String> {

    List<PhieuKiemDuyetEntity> findBySuCo_MaSuCo(String maSuCo);
    Page<PhieuKiemDuyetEntity> findByNhanVienDieuPhoi_MaNhanVienDieuPhoi(String maNhanVienDieuPhoi, Pageable pageable);

    @Query("""
    SELECT p FROM PhieuKiemDuyetEntity p
    WHERE p.nhanVienDieuPhoi.maNhanVienDieuPhoi = :maNhanVien
      AND (:tuNgay IS NULL OR CAST(p.thoiGianTao AS date) >= :tuNgay)
      AND (:denNgay IS NULL OR CAST(p.thoiGianTao AS date) <= :denNgay)
    """)
    Page<PhieuKiemDuyetEntity> findAllByFilter(
            @Param("maNhanVien") String maNhanVien,
            @Param("tuNgay") LocalDate tuNgay,
            @Param("denNgay") LocalDate denNgay,
            Pageable pageable
    );
}
