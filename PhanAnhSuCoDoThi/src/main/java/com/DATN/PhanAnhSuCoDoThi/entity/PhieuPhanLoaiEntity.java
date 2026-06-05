package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "maSuCo", insertable = false, updatable = false)
    private SucoEntity suCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maLoaiSuCo", insertable = false, updatable = false)
    private LoaiEntity loai;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
