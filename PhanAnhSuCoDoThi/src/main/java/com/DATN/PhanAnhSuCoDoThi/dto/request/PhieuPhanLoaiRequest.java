package com.DATN.PhanAnhSuCoDoThi.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhieuPhanLoaiRequest {
    String maSuCo;
    List<String> maLoai;
}
