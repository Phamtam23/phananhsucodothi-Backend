package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKiemDuyet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUKIEMDUYET")
@Getter
@Setter
public class PhieuKiemDuyetEntity {

    @Id
    @Column(name = "maKiemDuyet", length = 10, nullable = false)
    private String maKiemDuyet;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSuCo")
    private SucoEntity suCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNhanVienDieuPhoi")
    private NhanVienDieuPhoiEntity nhanVienDieuPhoi;

    @Column(name = "lyDoTuChoi", columnDefinition = "TEXT")
    private String lyDoTuChoi;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", columnDefinition = "TEXT")
    private TrangThaiKiemDuyet trangThai;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
