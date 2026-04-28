package com.DATN.PhanAnhSuCoDoThi.respository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;


@Repository
public interface SucoRespository extends JpaRepository<SucoEntity,String> {
}
