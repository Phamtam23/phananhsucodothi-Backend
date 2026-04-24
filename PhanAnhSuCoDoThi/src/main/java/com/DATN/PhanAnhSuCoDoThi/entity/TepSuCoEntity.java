package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "TEPSUCO")
@Getter
@Setter
public class TepSuCoEntity {

    @Id
    @Column(name = "maTepSuCo", length = 10, nullable = false)
    private String maTepSuCo;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "maSuCo")
    private SucoEntity suCo;

    @Size(max = 20)
    @Column(name = "loai", length = 20)
    private String loai;

    @Size(max = 500)
    @Column(name = "url", length = 500)
    private String url;
}
