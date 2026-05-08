package com.DATN.PhanAnhSuCoDoThi.dto.response.Suco;

import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SucoBaseResponse {

    private String maSuCo;
    private String noiDung;
    private String diaDiem;
    private String trangThai;
    private LocalDateTime thoiGianTao;
}
