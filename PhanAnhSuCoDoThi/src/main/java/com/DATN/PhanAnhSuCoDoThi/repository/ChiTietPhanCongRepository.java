package com.DATN.PhanAnhSuCoDoThi.repository;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhanCongRepository extends JpaRepository<ChiTietPhanCongEntity,String> {

    Page<ChiTietPhanCongEntity>
    findByNhanVienXuLy_MaNhanVien(
            String maNhanVien,
            Pageable pageable
    );
}
