package com.DATN.PhanAnhSuCoDoThi.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;


@Repository
public interface SucoRepository extends JpaRepository<SucoEntity,String> {

    Page<SucoEntity> findAllByNguoiDan_MaNguoiDan(String nguoiDan, Pageable pageables);
}
