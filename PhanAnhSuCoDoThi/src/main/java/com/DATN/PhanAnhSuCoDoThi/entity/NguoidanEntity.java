package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

/**
 * Nguoidan - Người dân báo cáo sự cố
 * Bảng: NGUOIDAN
 * PK: maNguoiDan VARCHAR(10)
 * FK: taiKhoan → TAIKHOAN (1-1)
 */
@Entity
@Table(name = "NGUOIDAN")
@Getter
@Setter
public class NguoidanEntity {

    @Id
    @Column(name = "maNguoiDan", length = 10, nullable = false)
    private String maNguoiDan;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTaiKhoan", referencedColumnName = "maTaiKhoan")
    private TaikhoanEntity taiKhoan;

    @Column(name = "diemUyTin")
    private Integer diemUyTin;
}
