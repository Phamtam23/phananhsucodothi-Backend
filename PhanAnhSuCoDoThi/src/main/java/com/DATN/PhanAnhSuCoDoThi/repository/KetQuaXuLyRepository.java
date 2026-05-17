package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KetQuaXuLyRepository extends JpaRepository<KetQuaXuLyEntity,String> {
    List<KetQuaXuLyEntity>
    findByChiTietPhanCong_MaChiTietPhanCong(
            String maChiTietPhanCong
    );

    @Query("""
        SELECT k FROM KetQuaXuLyEntity k
        LEFT JOIN FETCH k.chiTietPhanCong ct
        WHERE ct.phieuPhanCong.maPhieuPhanCong IN :maPhieus
    """)
    List<KetQuaXuLyEntity> findByPhieuPhanCongIn(@Param("maPhieus") List<String> maPhieus);
}
