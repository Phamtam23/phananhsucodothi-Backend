    package com.DATN.PhanAnhSuCoDoThi.entity;

    import jakarta.persistence.*;
    import lombok.Getter;
    import lombok.Setter;

    import java.time.LocalDate;

    @Entity
    @Table(name = "NHANVIEN_CHUCVU")
    @Getter
    @Setter
    public class NhanVienChucVuEntity {

        @Id
        @Column(name = "maNhanVienChucVu", length = 10, nullable = false)
        private String maNhanVienChucVu;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "maNhanVien")
        private NhanVienDonViEntity nhanVien;

        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "maChucVu")
        private ChucVuEntity chucVu;

        @Column(name = "ngayBatDau")
        private LocalDate ngayBatDau;

        @Column(name = "ngayKetThuc")
        private LocalDate ngayKetThuc;
    }
