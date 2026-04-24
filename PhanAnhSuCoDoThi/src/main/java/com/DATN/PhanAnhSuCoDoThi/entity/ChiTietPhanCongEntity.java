package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * ChiTietPhanCong - Chi tiết phân công cho nhân viên xử lý cụ thể
 * Bảng: CHITIETPHANCONG
 * PK: maChiTietPhanCong VARCHAR(10)
 * FK: phieuPhanCong → PHIEUPHANCONG
 * FK: nhanVienXuLy  → NHANVIEN_DONVI (nhân viên đơn vị thực hiện)
 */
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

    @Size(max = 20)
    @Column(name = "trangThai", length = 20)
    private String trangThai;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
