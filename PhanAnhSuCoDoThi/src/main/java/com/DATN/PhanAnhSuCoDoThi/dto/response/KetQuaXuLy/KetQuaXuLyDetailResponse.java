package com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaXuLyDetailResponse extends KetQuaXuLyBaseResponse{
    private List<MediaResponse> medias;
}
