package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuKiemDuyet;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKiemDuyet;
import lombok.Getter;

@Getter
public class CreatePhieuKiemDuyetRequest {
    TrangThaiKiemDuyet  trangThaiKiemDuyet;
    String  maSuCo;
    String lyDoTuChoi;
}
