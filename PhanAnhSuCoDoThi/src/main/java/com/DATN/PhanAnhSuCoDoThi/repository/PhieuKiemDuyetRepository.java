package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuKiemDuyetEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuKiemDuyetRepository extends JpaRepository<PhieuKiemDuyetEntity,String> {

    List<PhieuKiemDuyetEntity> findBySuCo_MaSuCo(String maSuCo);
}
