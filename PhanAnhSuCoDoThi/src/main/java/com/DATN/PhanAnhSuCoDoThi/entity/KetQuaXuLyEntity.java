package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKetQua;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", length = 20)
    private TrangThaiKetQua trangThai;

    @Column(name = "noiDungThucHien", columnDefinition = "TEXT")
    private String noiDungThucHien;
}
