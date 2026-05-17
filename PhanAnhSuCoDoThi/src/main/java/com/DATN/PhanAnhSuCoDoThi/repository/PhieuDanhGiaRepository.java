package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuDanhGiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PhieuDanhGiaRepository extends JpaRepository<PhieuDanhGiaEntity, String> {

    List<PhieuDanhGiaEntity> findByKetQuaXuLy_MaKetQua(String maKetQuaXuLy);

    @Query("""
    SELECT pdg.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.maPhieuPhanCong
    FROM PhieuDanhGiaEntity pdg
    WHERE pdg.nguoiDan.maNguoiDan = :maNguoiDan
    AND pdg.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.maPhieuPhanCong IN :maPhieus
""")
    Set<String> findMaPhieuDaDanhGia(
            @Param("maNguoiDan") String maNguoiDan,
            @Param("maPhieus") List<String> maPhieus
    );
}
