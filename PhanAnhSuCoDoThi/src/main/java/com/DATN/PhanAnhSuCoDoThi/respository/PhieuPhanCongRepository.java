package com.DATN.PhanAnhSuCoDoThi.respository;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
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

    List<PhieuPhanCongEntity> findAllByDonViXuLy_MaDonViXuLy(String maDonViXuLy);

}

