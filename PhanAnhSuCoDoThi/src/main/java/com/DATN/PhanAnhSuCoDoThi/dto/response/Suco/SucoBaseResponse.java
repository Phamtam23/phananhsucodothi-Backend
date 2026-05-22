package com.DATN.PhanAnhSuCoDoThi.dto.response.Suco;

import jakarta.persistence.Column;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SucoBaseResponse {
    private String maSuCo;
    private String noiDung;
    private String tieuDe;
    private String diaDiem;
    private String trangThai;
    private Integer diemSpam;
    private String lyDoSpam;
    private List<String> loaiSuCos;
    private LocalDateTime thoiGianTao;
}
