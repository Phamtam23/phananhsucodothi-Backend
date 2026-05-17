package com.DATN.PhanAnhSuCoDoThi.dto.response.Suco;

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
    private String diaDiem;
    private String trangThai;
    private List<String> loaiSuCos;
    private LocalDateTime thoiGianTao;
}
