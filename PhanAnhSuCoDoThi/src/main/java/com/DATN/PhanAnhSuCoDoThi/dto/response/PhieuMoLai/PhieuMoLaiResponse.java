package com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuMoLai;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiMoLai;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhieuMoLaiResponse {
    String maPhieuMoLai;
    String lyDo;
    TrangThaiMoLai trangThaiMoLai;
    String maKetQuaXuLy;
    private LocalDateTime thoiGianTao;
    
    // Bổ sung các trường mới
    private String lyDoTuChoi;
    private String maSuCo;
    private String noiDungSuCo;
    private String maPhieuPhanCong;
}
