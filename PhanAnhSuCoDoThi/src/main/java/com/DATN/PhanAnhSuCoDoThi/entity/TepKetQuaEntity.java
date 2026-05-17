package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TEPKETQUA")
@Getter
@Setter
public class TepKetQuaEntity {

    @Id
    @Column(name = "maTepKetQua", length = 10, nullable = false)
    private String maTepKetQua;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maKetQua")
    private KetQuaXuLyEntity ketQua;

    @Size(max = 20)
    @Column(name = "loai", length = 20)
    private String loai;

    @Size(max = 500)
    @Column(name = "url", length = 500)
    private String url;
}
