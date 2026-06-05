package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.DonViXuLyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DonViXuLyRepository extends JpaRepository<DonViXuLyEntity, String> {
    List<DonViXuLyEntity> findAllByMaDonViXuLyIn(List<String> maDonViXuLys);
}
