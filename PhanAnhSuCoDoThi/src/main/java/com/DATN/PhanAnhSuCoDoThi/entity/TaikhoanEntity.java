package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

import java.util.Set;

@Entity
@Table(name = "TAIKHOAN",
        uniqueConstraints = {
                @UniqueConstraint(columnNames = "soDienThoai"),
                @UniqueConstraint(columnNames = "CCCD")
        })
@Getter
@Setter
public class TaikhoanEntity {

    @Id
    @Column(name = "maTaiKhoan", length = 10, nullable = false)
    private String maTaiKhoan;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Size(max = 100)
    @Column(name = "matKhau", length = 100)
    private String matKhau;

    @Size(max = 100)
    @Column(name = "hoTen", columnDefinition = "nvarchar(100)")
    private String hoTen;

    @Size(max = 10)
    @Column(name = "soDienThoai", columnDefinition = "CHAR(10)")
    private String soDienThoai;

    @Size(max = 12)
    @Column(name = "CCCD", columnDefinition = "CHAR(12)")
    private String cccd;

    @Size(max = 255)
    @Column(name = "diaChi", columnDefinition = "nvarchar(255)")
    private String diaChi;

    @Size(max = 500)
    @Column(name = "anhDaiDien", length = 500)
    private String anhDaiDien;

    @Size(max = 5)
    @Column(name = "code", columnDefinition = "CHAR(5)")
    private String code;

    @Column(name = "daXacThucEmail")
    private Boolean daXacThucEmail;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "TAIKHOAN_QUYEN",
            joinColumns = @JoinColumn(name = "maTaiKhoan"),
            inverseJoinColumns = @JoinColumn(name = "maQuyen")
    )
    private Set<QuyenTruyCapEntity> quyens;
    @OneToOne(mappedBy = "taiKhoan", fetch = FetchType.LAZY)
    private NguoidanEntity nguoiDan;
}
