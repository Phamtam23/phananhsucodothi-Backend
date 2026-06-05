package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "hanhDong", length = 20)
    private TrangThaiSuCo hanhDong;

    @Column(name = "thoiGian")
    private LocalDateTime thoiGian;
}
