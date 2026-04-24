package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * NhatKy - Nhật ký hành động trong hệ thống
 * Bảng: NHATKY
 * PK: maNhatKy VARCHAR(10)
 * FK: suCo     → SUCO
 * FK: taiKhoan → TAIKHOAN (ai thực hiện hành động)
 * Ghi lại mọi hành động: tiếp nhận, phân loại, phân công, xử lý...
 */
@Entity
@Table(name = "NHATKY")
@Getter
@Setter
public class NhatKyEntity {

    @Id
    @Column(name = "maNhatKy", length = 10, nullable = false)
    private String maNhatKy;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSuCo")
    private SucoEntity suCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTaiKhoan")
    private TaikhoanEntity taiKhoan;

    @Size(max = 20)
    @Column(name = "hanhDong", length = 20)
    private String hanhDong;

    @Column(name = "thoiGian")
    private LocalDateTime thoiGian;
}
