package com.DATN.PhanAnhSuCoDoThi.dto.response;

import com.DATN.PhanAnhSuCoDoThi.entity.ThongBaoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.LoaiThongBao;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
public class ThongBaoResponse {
    private String maThongBao;
    private LoaiThongBao loaiThongBao;
    private Boolean daDoc;
    private String tieuDe;
    private String noiDung;
    private LocalDateTime thoiGianTao;

    public static ThongBaoResponse fromEntity(ThongBaoEntity entity) {
        if (entity == null) return null;
        return ThongBaoResponse.builder()
                .maThongBao(entity.getMaThongBao())
                .loaiThongBao(entity.getLoaiThongBao())
                .daDoc(entity.getDaDoc())
                .tieuDe(entity.getTieuDe())
                .noiDung(entity.getNoiDung())
                .thoiGianTao(entity.getThoiGianTao())
                .build();
    }
}
