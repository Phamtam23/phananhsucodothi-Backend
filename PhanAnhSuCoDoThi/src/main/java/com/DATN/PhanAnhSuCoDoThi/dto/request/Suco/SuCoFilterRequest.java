package com.DATN.PhanAnhSuCoDoThi.dto.request.Suco;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SuCoFilterRequest {

    private String keyword;

    private List<TrangThaiSuCo> trangThai;

    private String maLoai;

    private String diaDiem;

}
