package com.DATN.PhanAnhSuCoDoThi.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@Getter
@Setter
public class PhieuTrangThaiResponse {

    private Boolean canDanhGia;
    private Boolean daDanhGia;
    private Boolean canMoLai;
    private Boolean daMoLai;
}
