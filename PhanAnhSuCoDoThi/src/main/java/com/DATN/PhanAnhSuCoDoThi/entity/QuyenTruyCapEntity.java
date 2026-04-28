package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "QUYENTRUYCAP")
@Getter
@Setter
public class QuyenTruyCapEntity {

    @Id
    @Column(name = "maQuyen", length = 10, nullable = false)
    private String maQuyen;

    @Column(name = "tenQuyen", length = 50)
    private String tenQuyen;
}
