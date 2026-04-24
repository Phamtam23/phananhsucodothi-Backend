package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * NhanVienDieuPhoi - Nhân viên điều phối (TTDVC)
 * Vai trò: tiếp nhận, kiểm duyệt sự cố, tạo phiếu phân công
 * Bảng: NHANVIENDIEUPHOI
 * PK: maNhanVienDieuPhoi VARCHAR(10)
 * FK: taiKhoan → TAIKHOAN (1-1)
 */
@Entity
@Table(name = "NHANVIENDIEUPHOI")
@Getter
@Setter
public class NhanVienDieuPhoiEntity {

    @Id
    @Column(name = "maNhanVienDieuPhoi", length = 10, nullable = false)
    private String maNhanVienDieuPhoi;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTaiKhoan", referencedColumnName = "maTaiKhoan")
    private TaikhoanEntity taiKhoan;

    @Size(max = 20)
    @Column(name = "trangThai", length = 20)
    private String trangThai;

    @Column(name = "ngayBatDau")
    private LocalDateTime ngayBatDau;

    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;

    @Column(name = "ngayKetThuc")
    private LocalDate ngayKetThuc;
}
