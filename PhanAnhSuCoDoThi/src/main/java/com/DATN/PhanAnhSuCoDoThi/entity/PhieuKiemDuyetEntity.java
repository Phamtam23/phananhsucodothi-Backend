package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKiemDuyet;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PhieuKiemDuyet - Phiếu kiểm duyệt sự cố bởi nhân viên điều phối
 * Bảng: PHIEUKIEMDUYET
 * PK: maKiemDuyet VARCHAR(10)
 * FK: suCo              → SUCO
 * FK: nhanVienDieuPhoi  → NHANVIENDIEUPHOI
 *
 * Mỗi sự cố có 1 phiếu kiểm duyệt. Nhân viên điều phối xem xét:
 *  - Nếu hợp lệ → tạo PHIEUPHANCONG
 *  - Nếu không hợp lệ → ghi lyDoTuChoi, đóng sự cố
 */
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
