package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KetQuaXuLyRepository extends JpaRepository<KetQuaXuLyEntity,String> {
    List<KetQuaXuLyEntity>
    findByChiTietPhanCong_MaChiTietPhanCong(
            String maChiTietPhanCong
    );
}
