package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "CHITIETPHANCONG")
@Getter
@Setter
public class ChiTietPhanCongEntity {

    @Id
    @Column(name = "maChiTietPhanCong", length = 10, nullable = false)
    private String maChiTietPhanCong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhieuPhanCong")
    private PhieuPhanCongEntity phieuPhanCong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhanVienXuLy")
    private NhanVienDonViEntity nhanVienXuLy;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", length = 20)
    private TrangThaiChiTietPhanCong trangThai;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
