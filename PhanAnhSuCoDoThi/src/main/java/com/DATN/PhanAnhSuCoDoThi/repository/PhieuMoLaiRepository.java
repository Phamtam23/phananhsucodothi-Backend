package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuDanhGiaEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuMoLaiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Set;

@Repository
public interface PhieuMoLaiRepository extends JpaRepository<PhieuMoLaiEntity,String> {
    @Query("""
    SELECT pml.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.maPhieuPhanCong
    FROM PhieuMoLaiEntity pml
    WHERE pml.nguoiDan.maNguoiDan = :maNguoiDan
    AND pml.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.maPhieuPhanCong IN :maPhieus
""")
    Set<String> findMaPhieuDaMoLai(
            @Param("maNguoiDan") String maNguoiDan,
            @Param("maPhieus") List<String> maPhieus
    );

    @Query("""
    SELECT pml
    FROM PhieuMoLaiEntity pml
    WHERE pml.nguoiDan.maNguoiDan = :maNguoiDan
    AND pml.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.maPhieuPhanCong = :maPhieu
    """)
    List<PhieuMoLaiEntity> findAllByPhanCong(
            @Param("maNguoiDan") String maNguoiDan,
            @Param("maPhieu") String maPhieu
    );

    @Query("""
    SELECT pml
    FROM PhieuMoLaiEntity pml
    WHERE pml.nguoiDan.maNguoiDan = :maNguoiDan
    AND pml.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.maPhieuPhanCong = :maPhieu
    """)
    PhieuMoLaiEntity findAllByPhanCongSC(
            @Param("maNguoiDan") String maNguoiDan,
            @Param("maPhieu") String maPhieu
    );

    @Query("""
    SELECT pml
    FROM PhieuMoLaiEntity pml
    WHERE pml.ketQuaXuLy.chiTietPhanCong.phieuPhanCong.donViXuLy.maDonViXuLy = :maDonVi
    ORDER BY pml.thoiGianTao DESC
    """)
    org.springframework.data.domain.Page<PhieuMoLaiEntity> findAllByDonVi(
            @Param("maDonVi") String maDonVi,
            org.springframework.data.domain.Pageable pageable
    );

        @Query("""
        SELECT pd FROM PhieuMoLaiEntity pd
        JOIN pd.ketQuaXuLy kq
        JOIN kq.chiTietPhanCong ctpc
        JOIN ctpc.phieuPhanCong pc
        WHERE pc.maPhieuPhanCong IN :maPhanCongs
        AND pd.nguoiDan.maNguoiDan = :maNguoiDan
        AND kq.trangThai = com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKetQua.DA_DUYET
    """)
        List<PhieuMoLaiEntity> findByPhanCongsAndNguoiDan(
                @Param("maPhanCongs") List<String> maPhanCongs,
                @Param("maNguoiDan") String maNguoiDan
        );
}
