package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import org.springframework.stereotype.Component;

@Component
public class MediaMapper {

  public MediaResponse mapSucoToResponse(TepSuCoEntity Media) {
        return MediaResponse.builder()
                .url(Media.getUrl())
                .loai(Media.getLoai())
                .build();
    }


}
