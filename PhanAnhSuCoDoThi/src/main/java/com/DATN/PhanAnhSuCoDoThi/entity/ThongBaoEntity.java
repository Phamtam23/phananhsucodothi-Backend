package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.LoaiThongBao;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "THONGBAO")
@Getter
@Setter
public class ThongBaoEntity {

    @Id
    @Column(name = "maThongBao", length = 10, nullable = false)
    private String maThongBao;

    @Enumerated(EnumType.STRING)
    @Column(name = "loaiThongBao", length = 50)
    private LoaiThongBao loaiThongBao;

    @Column(name = "daDoc")
    private Boolean daDoc = false;

    @Size(max = 100)
    @Column(name = "tieuDe", columnDefinition = "nvarchar(100)")
    private String tieuDe;

    @Column(name = "noiDung", columnDefinition = "TEXT")
    private String noiDung;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maTaiKhoan")
    private TaikhoanEntity taiKhoan;
}
