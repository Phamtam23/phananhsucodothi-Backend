package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.LoaiEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoaiRepository extends JpaRepository<LoaiEntity,String> {
    Optional<LoaiEntity>
    findByMaLoaiSuCoAndDeletedAtIsNull(String maLoai);

    List<LoaiEntity> findAllByDeletedAtIsNull();


}
