package com.DATN.PhanAnhSuCoDoThi.dto.response.Suco;

import com.DATN.PhanAnhSuCoDoThi.enums.DoUuTien;
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
    private Double kinhDo;
    private Double viDo;
    private String trangThai;
    private DoUuTien doUuTien;
    private Integer diemSpam;
    private String lyDoSpam;
    private List<String> loaiSuCos;
    private LocalDateTime thoiGianTao;
}
