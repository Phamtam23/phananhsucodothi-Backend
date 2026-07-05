package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuDanhGiaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface PhieuDanhGiaRepository extends JpaRepository<PhieuDanhGiaEntity, String> {

    PhieuDanhGiaEntity findByKetQuaXuLy_MaKetQua(String maKetQuaXuLy);

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

    @Query("""
    SELECT pdg.ketQuaXuLy.maKetQua
    FROM PhieuDanhGiaEntity pdg
    WHERE pdg.nguoiDan.maNguoiDan = :maNguoiDan
""")
    Set<String> findMaKetQuaDaDanhGia(
            @Param("maNguoiDan") String maNguoiDan
    );

    @Query("""
    SELECT pd FROM PhieuDanhGiaEntity pd
    JOIN pd.ketQuaXuLy kq
    JOIN kq.chiTietPhanCong ctpc
    JOIN ctpc.phieuPhanCong pc
    WHERE pc.maPhieuPhanCong IN :maPhanCongs
    AND pd.nguoiDan.maNguoiDan = :maNguoiDan
    AND kq.trangThai = com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKetQua.DA_DUYET
""")
    List<PhieuDanhGiaEntity> findByPhanCongsAndNguoiDan(
            @Param("maPhanCongs") List<String> maPhanCongs,
            @Param("maNguoiDan") String maNguoiDan
    );
}
