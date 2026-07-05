package com.DATN.PhanAnhSuCoDoThi.dto.request.PhieuPhanCong;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UpdatePhieuPhanCongRequest {
    @NotBlank
    private TrangThaiPhanCong trangThai;

    private String ghiChu;

    private String lyDoTuChoi;
}
