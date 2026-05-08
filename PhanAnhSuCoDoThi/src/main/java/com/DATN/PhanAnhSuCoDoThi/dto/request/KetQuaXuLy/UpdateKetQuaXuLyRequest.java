package com.DATN.PhanAnhSuCoDoThi.dto.request.KetQuaXuLy;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiKetQua;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UpdateKetQuaXuLyRequest {
    private String maKetQuaXuLy;

    private String noiDungThucHien;

    private TrangThaiKetQua  trangThaiKetQua;

    private List<String> mediaUrls;

}
