package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuKiemDuyetResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuKiemDuyetEntity;
import org.springframework.stereotype.Component;

@Component
public class PhieuKiemDuyetMapper {

    public PhieuKiemDuyetResponse toResponse(PhieuKiemDuyetEntity phieuKiemDuyetEntity) {
        if (phieuKiemDuyetEntity == null) return null;
        return PhieuKiemDuyetResponse.builder()
                .maSuCo(phieuKiemDuyetEntity.getSuCo().getMaSuCo())
                .maKiemDuyet((phieuKiemDuyetEntity.getMaKiemDuyet()))
                .maNhanVienDieuPhoi(phieuKiemDuyetEntity.getNhanVienDieuPhoi().getMaNhanVienDieuPhoi())
                .trangThai(phieuKiemDuyetEntity.getTrangThai())
        .build();

    }
}
