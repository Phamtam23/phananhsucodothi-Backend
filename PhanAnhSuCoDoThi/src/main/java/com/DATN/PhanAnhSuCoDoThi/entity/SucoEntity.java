package com.DATN.PhanAnhSuCoDoThi.entity;

import com.DATN.PhanAnhSuCoDoThi.enums.DoUuTien;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "SUCO")
@Getter
@Setter
public class SucoEntity {

    @Id
    @Column(name = "maSuCo", columnDefinition = "CHAR(10)", nullable = false)
    private String maSuCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan")
    private NguoidanEntity nguoiDan;

    @Column(name = "kinhDo")
    private Double kinhDo;

    @Column(name = "viDo")
    private Double viDo;

    @Size(max = 255)
    @Column(name = "diaDiem", columnDefinition = "nvarchar(500)")
    private String diaDiem; 

    @Column(name = "ngayDuKienHoanThanh")
    private LocalDate ngayDuKienHoanThanh;

    @Enumerated(EnumType.STRING)
    @Column(name = "trangThai", length = 20)
    private TrangThaiSuCo trangThai;

    @Column(name = "noiDung", columnDefinition = "TEXT")
    private String noiDung;

    @Column(name = "tieuDe", columnDefinition = "nvarchar(500)")
    private String tieuDe;

    @Column(name = "diemSpam")
    private Integer diemSpam;

    @Column(name = "lyDoSpam", columnDefinition = "TEXT")
    private String lyDoSpam;

    @Column(name = "laSpam")
    private Boolean laSpam;

    @Enumerated(EnumType.STRING)
    @Column(name = "doUuTien", length = 20)
    private DoUuTien doUuTien;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;

    @OneToMany(mappedBy = "suCo", fetch = FetchType.LAZY)
    private List<PhieuPhanLoaiEntity> danhSachLoai;

    @OneToMany(mappedBy = "suCo", fetch = FetchType.LAZY)
    private List<TepSuCoEntity> tepSuCoList;
}
