package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
public class SucoResponse {

    private String maSuCo;

    private String maNguoiDan;

    private Double kinhDo;

    private Double viDo;

    private String diaDiem;

    private String noiDung;

    private String trangThai;

    private LocalDate ngayDuKienHoanThanh;

    private LocalDateTime thoiGianTao;

    // media
    private List<MediaResponse> medias;

    private List<PhieuPhanCongSCRespomse>  phieuPhanCongs;

}
