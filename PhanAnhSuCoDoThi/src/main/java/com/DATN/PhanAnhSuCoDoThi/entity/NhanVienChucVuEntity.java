package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * NhanVienChucVu - Lịch sử chức vụ của nhân viên đơn vị
 * Bảng: NHANVIEN_CHUCVU
 * PK: maNhanVienChucVu VARCHAR(10)
 * FK: nhanVien → NHANVIEN_DONVI
 * FK: chucVu   → CHUCVU
 * Một nhân viên có thể giữ nhiều chức vụ theo thời gian
 */
@Entity
@Table(name = "NHANVIEN_CHUCVU")
@Getter
@Setter
public class NhanVienChucVuEntity {

    @Id
    @Column(name = "maNhanVienChucVu", length = 10, nullable = false)
    private String maNhanVienChucVu;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhanVien")
    private NhanVienDonViEntity nhanVien;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChucVu")
    private ChucVuEntity chucVu;

    @Column(name = "ngayBatDau")
    private LocalDate ngayBatDau;

    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;
}
