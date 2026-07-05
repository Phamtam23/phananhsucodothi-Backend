package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.ThongBaoEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ThongBaoRepository extends JpaRepository<ThongBaoEntity, String> {
    Page<ThongBaoEntity> findAllByTaiKhoan_MaTaiKhoanOrderByThoiGianTaoDesc(String maTaiKhoan, Pageable pageable);

    @Query("SELECT COUNT(t) FROM ThongBaoEntity t WHERE t.taiKhoan.maTaiKhoan = :maTaiKhoan AND t.daDoc = false")
    long countUnreadByTaiKhoan(@Param("maTaiKhoan") String maTaiKhoan);
}
