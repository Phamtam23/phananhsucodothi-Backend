package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PhieuPhanLoai - Phiếu phân loại sự cố
 * Bảng: PHIEUPHANLOAI
 * PK ghép: (maSuCo, maLoaiSuCo) — 1 sự cố có thể thuộc nhiều loại
 * FK: suCo     → SUCO
 * FK: loai     → LOAI
 */
@Entity
@Table(name = "PHIEUPHANLOAI")
@IdClass(PhieuPhanLoaiId.class)
@Getter
@Setter
public class PhieuPhanLoaiEntity {

    @Id
    @Column(name = "maSuCo", length = 10, nullable = false)
    private String maSuCo;

    @Id
    @Column(name = "maLoaiSuCo", length = 10, nullable = false)
    private String maLoaiSuCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maSuCo")
    @JoinColumn(name = "maSuCo", insertable = false, updatable = false)
    private SucoEntity suCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @MapsId("maLoaiSuCo")
    @JoinColumn(name = "maLoaiSuCo", insertable = false, updatable = false)
    private LoaiEntity loai;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
