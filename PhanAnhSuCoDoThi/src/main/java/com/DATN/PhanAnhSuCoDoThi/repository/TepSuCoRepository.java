package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TepSuCoRepository extends JpaRepository<TepSuCoEntity,String> {

    List<TepSuCoEntity> findBySuCo_MaSuCo(String maSuCo);

    List<TepSuCoEntity> findBySuCo_MaSuCoIn(List<String> maSuCos);
}
