package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "NHANVIEN_DONVI")
@Getter
@Setter
public class NhanVienDonViEntity {

    @Id
    @Column(name = "maNhanVien", length = 10, nullable = false)
    private String maNhanVien;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTaiKhoan", referencedColumnName = "maTaiKhoan")
    private TaikhoanEntity taiKhoan;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maDonVi")
    private DonViXuLyEntity donVi;

    @Column(name = "ngaySinh")
    private LocalDate ngaySinh;
}
