package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.entity.PhieuKiemDuyetEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKiemDuyet;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class PhieuKiemDuyetResponse {
    private String maKiemDuyet;
    private String maNhanVienDieuPhoi;
    private String  maSuCo;
    private TrangThaiKiemDuyet trangThai;
    private String  lyDoTuChoi;
    private LocalDateTime thoiGianTao;

}
