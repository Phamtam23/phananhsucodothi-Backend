package com.DATN.PhanAnhSuCoDoThi.dto.response.Suco;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanCongSCRespomse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SucoDetailResponse extends SucoBaseResponse {
    private String maNguoiDan;

    private Double kinhDo;
    private Double viDo;

    private LocalDate ngayDuKienHoanThanh;

    private List<MediaResponse> medias;
    private List<PhieuPhanCongSCRespomse> phieuPhanCongs;
}
