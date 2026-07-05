package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiMoLai;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUMOLAI")
@Getter
@Setter
public class PhieuMoLaiEntity {

    @Id
    @Column(name = "maPhieuMoLai", length = 10, nullable = false)
    private String maPhieuMoLai;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan")
    private NguoidanEntity nguoiDan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKetQua")
    private KetQuaXuLyEntity ketQuaXuLy;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;

    @Column(name = "lyDo", columnDefinition = "TEXT")
    private String lyDo;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThaiMoLai", length = 20)
    private TrangThaiMoLai trangThaiMoLai;

    @Column(name = "lyDoTuChoi", columnDefinition = "TEXT")
    private String lyDoTuChoi;
}
