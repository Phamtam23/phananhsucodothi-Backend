package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhieuPhanCongRepository extends JpaRepository<PhieuPhanCongEntity, String> {
    List<PhieuPhanCongEntity> findAllBySuCo_MaSuCoIn(List<String> su_MaSuCo);

    List<PhieuPhanCongEntity> findAllBySuCo_MaSuCo(String su_MaSuCo);
    @Override
    Optional<PhieuPhanCongEntity> findById(String s);

    Page<PhieuPhanCongEntity> findAllByDonViXuLy_MaDonViXuLy(String maDonViXuLy, Pageable pageable);

}

