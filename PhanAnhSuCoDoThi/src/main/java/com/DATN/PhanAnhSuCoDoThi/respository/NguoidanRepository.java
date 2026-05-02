package com.DATN.PhanAnhSuCoDoThi.respository;

import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NguoidanRepository extends JpaRepository<NguoidanEntity, String> {

    Optional<NguoidanEntity> findByTaiKhoan_MaTaiKhoan(String maTaiKhoan);
}
