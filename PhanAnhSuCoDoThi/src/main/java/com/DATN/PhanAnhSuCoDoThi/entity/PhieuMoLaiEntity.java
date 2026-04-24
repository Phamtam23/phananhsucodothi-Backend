package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PhieuMoLai - Phiếu yêu cầu mở lại sự cố khi người dân không đồng ý kết quả
 * Bảng: PHIEUMOLAI
 * PK: maPhieuMoLai VARCHAR(10)
 * FK: nguoiDan → NGUOIDAN
 * FK: ketQua   → KETQUAXULY
 */
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
    private KetQuaXuLyEntity ketQua;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;

    @Column(name = "lyDo", columnDefinition = "TEXT")
    private String lyDo;
}
