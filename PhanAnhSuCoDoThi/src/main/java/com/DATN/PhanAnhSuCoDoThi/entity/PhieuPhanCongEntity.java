package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "PHIEUPHANCONG")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", length = 20)
    private TrangThaiPhanCong trangThai;

    @Size(max = 255)
    @Column(name = "ghiChu", columnDefinition = "nvarchar(255)")
    private String ghiChu;

    @Column(name = "lyDoTuChoi", columnDefinition = "TEXT")
    private String lyDoTuChoi;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
