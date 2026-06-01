package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

/**
 * TepMinhChung - Tệp minh chứng đính kèm phiếu mở lại
 * Bảng: TEPMINHCHUNG
 * PK: maTepDanhGia VARCHAR(10)
 * FK: phieuMoLai → PHIEUMOLAI
 */
@Entity
@Table(name = "TEPMINHCHUNG")
@Getter
@Setter
public class TepMinhChungEntity {

    @Id
    @Column(name = "maTepDanhGia", length = 10, nullable = false)
    private String maTepDanhGia;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maPhieuMoLai")
    private PhieuMoLaiEntity phieuMoLai;

    @Size(max = 500)
    @Column(name = "url", length = 500)
    private String url;

    @Size(max = 50)
    @Column(name = "loai", length = 50)
    private String loai;
}
