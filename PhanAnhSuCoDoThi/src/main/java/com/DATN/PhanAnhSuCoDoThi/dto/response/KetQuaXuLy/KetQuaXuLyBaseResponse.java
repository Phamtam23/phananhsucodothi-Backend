package com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaXuLyBaseResponse {
    String maKetQuaXuLy;
    String maChiTietPhanCong;
    LocalDateTime thoiGianNop;
    String noiDungThucHien;
}
