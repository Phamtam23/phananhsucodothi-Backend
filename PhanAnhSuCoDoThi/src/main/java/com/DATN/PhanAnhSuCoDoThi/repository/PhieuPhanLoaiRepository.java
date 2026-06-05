package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhieuPhanLoaiRepository extends JpaRepository<PhieuPhanLoaiEntity, PhieuPhanLoaiId> {

    long deleteByMaSuCoAndMaLoaiSuCo(String maSuCo, String maLoaiSuCo);

    List<PhieuPhanLoaiEntity> findAllByMaSuCo(String maSuCo);

    boolean existsByMaSuCoAndMaLoaiSuCo(String maSuCo, String maLoaiSuCo);

    List<PhieuPhanLoaiEntity> findBySuCo_MaSuCoIn(List<String> maSuCos);

}
