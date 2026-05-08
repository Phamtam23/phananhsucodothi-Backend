package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChiTietPhanCongRepository extends CrudRepository<ChiTietPhanCongEntity,String> {

    PageResponse<ChiTietPhanCongEntity> findByNhanVienXuLy_maNhanVienXuLy(String maNhanVienXuLy, Pageable pageable);
}
