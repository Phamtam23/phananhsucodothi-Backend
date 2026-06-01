package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PhieuPhanLoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class PhanLoaiMapper {

    public PhieuPhanLoaiResponse toResponse(
            String maSuCo,
            List<PhieuPhanLoaiEntity> entities
    ) {

        return PhieuPhanLoaiResponse.builder()
                .maSuCo(maSuCo)
                .dsLoai(
                        entities.stream()
                                .map(this::toLoaiResponse)
                                .toList()
                )
                .thoiGianPhanLoai(
                        entities.isEmpty()
                                ? null
                                : entities.get(0).getThoiGianTao()
                )
                .build();
    }

    private LoaiResponse toLoaiResponse(
            PhieuPhanLoaiEntity entity
    ) {

        return LoaiResponse.builder()
                .maLoai(
                        entity.getLoai().getMaLoaiSuCo()
                )
                .tenLoaiSuCo(
                        entity.getLoai().getTenLoaiSuCo()
                )
                .build();
    }
}
