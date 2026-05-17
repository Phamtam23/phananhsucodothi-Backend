package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.MucDoDanhGia;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUDANHGIA")
@Getter
@Setter
public class PhieuDanhGiaEntity {

    @Id
    @Column(name = "maDanhGia", length = 10, nullable = false)
    private String maDanhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKetQua")
    private KetQuaXuLyEntity ketQuaXuLy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan")
    private NguoidanEntity nguoiDan;

    @Enumerated(EnumType.STRING)
    @Size(max = 20)
    @Column(name = "mucDoHaiLong", length = 20)
    private MucDoDanhGia mucDoHaiLong;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
