package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "LOAI")
@Getter
@Setter
public class LoaiEntity {

    @Id
    @Column(name = "maLoaiSuCo", length = 10, nullable = false)
    private String maLoaiSuCo;

    @Size(max = 50)
    @Column(name = "tenLoaiSuCo", columnDefinition = "nvarchar(50)")
    private String tenLoaiSuCo;

    @Column(name = "moTa", columnDefinition = "TEXT")
    private String moTa;

    @Column(name = "deletedAt")
    private LocalDate deletedAt;
}
