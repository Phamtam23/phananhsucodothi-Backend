package com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import java.util.List;
import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class KetQuaXuLySummaryResponse extends KetQuaXuLyBaseResponse {
    private List<MediaResponse> medias;
}
