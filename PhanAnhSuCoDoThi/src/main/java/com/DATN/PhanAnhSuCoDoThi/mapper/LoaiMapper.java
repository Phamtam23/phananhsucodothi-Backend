package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.LoaiResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.LoaiEntity;
import org.springframework.stereotype.Component;

@Component
public class LoaiMapper {
    public LoaiResponse toResponse(
            LoaiEntity entity
    ) {

        return LoaiResponse.builder()
                .maLoai(
                        entity.getMaLoaiSuCo()
                )
                .tenLoaiSuCo(
                        entity.getTenLoaiSuCo()
                )
                .build();
    }
}
