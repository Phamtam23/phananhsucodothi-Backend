package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuChiDao;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateChiDaoRequest {

    private String maChiTietPhanCong;

    private String noiDung;

    private LocalDate ngayChiDao;
}
