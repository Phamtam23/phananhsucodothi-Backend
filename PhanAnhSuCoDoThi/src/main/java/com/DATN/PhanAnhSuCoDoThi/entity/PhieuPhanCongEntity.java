package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PhieuPhanCong - Phiếu phân công xử lý sự cố cho đơn vị
 * Bảng: PHIEUPHANCONG
 * PK: maPhieuPhanCong VARCHAR(10)
 * FK: suCo             → SUCO
 * FK: donViXuLy        → DONVIXULY
 * FK: nhanVienDieuPhoi → NHANVIENDIEUPHOI (người tạo phân công)
 */
@Entity
@Table(name = "PHIEUPHANCONG")
@Getter
@Setter
public class PhieuPhanCongEntity {

    @Id
    @Column(name = "maPhieuPhanCong", length = 10, nullable = false)
    private String maPhieuPhanCong;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSuCo")
    private SucoEntity suCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonViXuLy")
    private DonViXuLyEntity donViXuLy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhanVienDieuPhoi")
    private NhanVienDieuPhoiEntity nhanVienDieuPhoi;

    @Size(max = 20)
    @Column(name = "trangThai", length = 20)
    private String trangThai;

    @Size(max = 255)
    @Column(name = "ghiChu", columnDefinition = "nvarchar(255)")
    private String ghiChu;

    @Column(name = "lyDoTuChoi", columnDefinition = "TEXT")
    private String lyDoTuChoi;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
