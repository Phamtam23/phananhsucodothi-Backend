package com.DATN.PhanAnhSuCoDoThi.respository;

import com.DATN.PhanAnhSuCoDoThi.entity.QuyenTruyCapEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuyenTruyCapRepository extends JpaRepository<QuyenTruyCapEntity, String> {
}
