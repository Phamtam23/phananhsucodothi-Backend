package com.DATN.PhanAnhSuCoDoThi.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "CHUCVU")
@Getter
@Setter
public class ChucVuEntity {

    @Id
    @Column(name = "maChucVu", length = 10, nullable = false)
    private String maChucVu;

    @Size(max = 50)
    @Column(name = "tenChucVu", columnDefinition = "nvarchar(50)")
    private String tenChucVu;
}
