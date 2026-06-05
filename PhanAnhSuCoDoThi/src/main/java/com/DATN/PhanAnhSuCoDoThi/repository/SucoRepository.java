package com.DATN.PhanAnhSuCoDoThi.repository;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;

import java.util.List;


@Repository
public interface SucoRepository extends JpaRepository<SucoEntity,String> {

    @Query("""
    SELECT DISTINCT s
    FROM SucoEntity s
    LEFT JOIN s.danhSachLoai pl
    WHERE
        (
            :keyword IS NULL OR
            LOWER(s.tieuDe) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(s.noiDung) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    AND
        (
            :trangThai IS NULL OR s.trangThai IN :trangThai
        )
    AND
        (
            :maLoai IS NULL OR pl.maLoaiSuCo = :maLoai
        )
    AND
        (
            :diaDiem IS NULL OR
            LOWER(s.diaDiem) LIKE LOWER(CONCAT('%', :diaDiem, '%'))
        )
""")
    Page<SucoEntity> findAllFilter(
            @Param("keyword") String keyword,
            @Param("trangThai") List<TrangThaiSuCo> trangThai,
            @Param("maLoai") String maLoai,
            @Param("diaDiem") String diaDiem,
            Pageable pageable
    );

    @Query("""
    SELECT DISTINCT s
    FROM SucoEntity s
    LEFT JOIN s.danhSachLoai pl
    WHERE
        s.nguoiDan.maNguoiDan = :maNguoiDan
    AND
        (
            :keyword IS NULL OR
            LOWER(s.tieuDe) LIKE LOWER(CONCAT('%', :keyword, '%')) OR
            LOWER(s.noiDung) LIKE LOWER(CONCAT('%', :keyword, '%'))
        )
    AND
        (
            :trangThai IS NULL OR s.trangThai IN :trangThai
        )
    AND
        (
            :maLoai IS NULL OR pl.maLoaiSuCo = :maLoai
        )
    AND
        (
            :diaDiem IS NULL OR
            LOWER(s.diaDiem) LIKE LOWER(CONCAT('%', :diaDiem, '%'))
        )
""")
    Page<SucoEntity> findAllByNguoiDanFilter(
            @Param("maNguoiDan") String maNguoiDan,
            @Param("keyword") String keyword,
            @Param("trangThai") List<TrangThaiSuCo> trangThai,
            @Param("maLoai") String maLoai,
            @Param("diaDiem") String diaDiem,
            Pageable pageable
    );

    @Query("""
    SELECT DISTINCT s
    FROM SucoEntity s
    LEFT JOIN s.danhSachLoai pl
    WHERE
        s.trangThai IN :trangThais
    AND
        (
            :maLoai IS NULL OR
            pl.maLoaiSuCo = :maLoai
        )
    """)
    Page<SucoEntity> findAllForMap(
            @Param("trangThais") List<TrangThaiSuCo> trangThais,
            @Param("maLoai") String maLoai,
            Pageable pageable
    );
}
