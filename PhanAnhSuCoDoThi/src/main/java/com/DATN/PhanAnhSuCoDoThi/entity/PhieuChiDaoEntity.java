package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUCHIDAO")
@Getter
@Setter
public class PhieuChiDaoEntity {

    @Id
    @Column(name = "maChiDao", length = 10, nullable = false)
    private String maChiDao;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChiTietPhanCong")
    private ChiTietPhanCongEntity chiTietPhanCong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTruongDonVi")
    private NhanVienDonViEntity truongDonVi;

    @Column(name = "noiDung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "ngayChiDao")
    private LocalDate ngayChiDao;

    @Column(name ="deletedAt")
    private LocalDateTime deletedAt;
}
