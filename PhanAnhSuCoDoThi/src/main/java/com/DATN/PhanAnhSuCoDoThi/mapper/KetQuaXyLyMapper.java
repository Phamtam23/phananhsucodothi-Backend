package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLySummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.KetQuaXuLyEntity;
import org.springframework.stereotype.Component;

@Component
public class KetQuaXuLyMapper {

    public KetQuaXuLySummaryResponse toSummaryResponse(KetQuaXuLyEntity entity) {
        if (entity == null) {
            return null;
        }

        return KetQuaXuLySummaryResponse.builder()
                .maKetQuaXuLy(entity.getMaKetQua())
                .maChiTietPhanCong(
                        entity.getChiTietPhanCong() != null
                                ? entity.getChiTietPhanCong().getMaChiTietPhanCong()
                                : null
                )
                .thoiGianNop(entity.getThoiGianNop())
                .noiDungThucHien(entity.getNoiDungThucHien())
                .build();
    }

    public KetQuaXuLyDetailResponse toDetailResponse(KetQuaXuLyEntity entity) {
        if (entity == null) {
            return null;
        }

        return KetQuaXuLyDetailResponse.builder()
                .maKetQuaXuLy(entity.getMaKetQua())
                .maChiTietPhanCong(
                        entity.getChiTietPhanCong() != null
                                ? entity.getChiTietPhanCong().getMaChiTietPhanCong()
                                : null
                )
                .thoiGianNop(entity.getThoiGianNop())
                .noiDungThucHien(entity.getNoiDungThucHien())
                .medias(null) // set sau
                .build();
    }
}