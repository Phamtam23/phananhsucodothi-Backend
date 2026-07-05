package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuMoLai;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatePhieuMoLaiRequest {
    String maKetQuaXuLy;
    String lyDo;
    List<String> mediaUrls;
}
