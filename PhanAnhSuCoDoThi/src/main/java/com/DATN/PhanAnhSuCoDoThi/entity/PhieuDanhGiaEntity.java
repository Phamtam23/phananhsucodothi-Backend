package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * PhieuDanhGia - Phiếu đánh giá kết quả xử lý sự cố do người dân thực hiện
 * Bảng: PHIEUDANHGIA
 * PK: maDanhGia VARCHAR(10)
 * FK: ketQua   → KETQUAXULY
 * FK: nguoiDan → NGUOIDAN
 */
@Entity
@Table(name = "PHIEUDANHGIA")
@Getter
@Setter
public class PhieuDanhGiaEntity {

    @Id
    @Column(name = "maDanhGia", length = 10, nullable = false)
    private String maDanhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKetQua")
    private KetQuaXuLyEntity ketQua;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maNguoiDan")
    private NguoidanEntity nguoiDan;

    /**
     * Mức độ hài lòng: RAT_HAI_LONG, HAI_LONG, BINH_THUONG, KHONG_HAI_LONG
     */
    @Size(max = 20)
    @Column(name = "mucDoHaiLong", length = 20)
    private String mucDoHaiLong;

    @Column(name = "thoiGianTao")
    private LocalDateTime thoiGianTao;
}
