package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * KetQuaXuLy - Kết quả xử lý sự cố do nhân viên đơn vị nộp
 * Bảng: KETQUAXULY
 * PK: maKetQua VARCHAR(10)
 * FK: chiTietPhanCong → CHITIETPHANCONG
 */
@Entity
@Table(name = "KETQUAXULY")
@Getter
@Setter
public class KetQuaXuLyEntity {

    @Id
    @Column(name = "maKetQua", length = 10, nullable = false)
    private String maKetQua;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maChiTietPhanCong")
    private ChiTietPhanCongEntity chiTietPhanCong;

    @Column(name = "thoiGianNop")
    private LocalDateTime thoiGianNop;

    @Size(max = 20)
    @Column(name = "trangThai", length = 20)
    private String trangThai;

    @Column(name = "noiDungThucHien", columnDefinition = "TEXT")
    private String noiDungThucHien;
}
