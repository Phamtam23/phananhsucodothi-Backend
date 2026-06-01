package com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateKetQuaXuLyRequest {

    private String maChiTietPhanCong;

    private String noiDungThucHien;

    private List<String> mediaUrls;
}
