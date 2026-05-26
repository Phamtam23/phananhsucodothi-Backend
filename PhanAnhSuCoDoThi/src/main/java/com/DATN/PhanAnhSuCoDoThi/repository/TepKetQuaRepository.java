package com.DATN.PhanAnhSuCoDoThi.repository;
import com.DATN.PhanAnhSuCoDoThi.entity.TepKetQuaEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TepKetQuaRepository extends JpaRepository<TepKetQuaEntity,String> {

    List<TepKetQuaEntity> findAllByKetQua_MaKetQuaIn(List<String> maKetQuas);

    List<TepKetQuaEntity> findAllByKetQua_MaKetQua(String maKetQua);
}
