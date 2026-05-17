package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiDonVi;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

@Entity
@Table(name = "DONVIXULY")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DonViXuLyEntity {

    @Id
    @Column(name = "maDonViXuLy", length = 10, nullable = false)
    private String maDonViXuLy;

    @Size(max = 100)
    @Column(name = "tenDonVi", columnDefinition = "nvarchar(100)")
    private String tenDonVi;

    @Size(max = 100)
    @Column(name = "khuVuc", columnDefinition = "nvarchar(100)")
    private String khuVuc;

    @Column(name = "moTa", columnDefinition = "TEXT")
    private String moTa;

    @Size(max = 100)
    @Column(name = "diaChi", columnDefinition = "nvarchar(100)")
    private String diaChi;

    @Size(max = 10)
    @Column(name = "SDT", columnDefinition = "CHAR(10)")
    private String sdt;

    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", length = 20)
    private TrangThaiDonVi trangThai;
}
