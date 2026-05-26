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
    SELECT kq FROM KetQuaXuLyEntity kq
    JOIN kq.chiTietPhanCong ctpc
    JOIN ctpc.phieuPhanCong pc
    WHERE pc.maPhieuPhanCong IN :maPhanCongs
""")
    List<KetQuaXuLyEntity> findByPhieuPhanCongIn(
            @Param("maPhanCongs") List<String> maPhanCongs
    );


}
