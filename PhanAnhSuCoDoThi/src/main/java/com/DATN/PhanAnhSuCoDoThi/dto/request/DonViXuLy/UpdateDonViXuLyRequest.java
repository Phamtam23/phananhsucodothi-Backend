package com.DATN.PhanAnhSuCoDoThi.dto.request.DonViXuLy;

import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiDonVi;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UpdateDonViXuLyRequest {
    @Size(max = 100)
    private String tenDonVi;

    @Size(max = 100)
    private String khuVuc;

    private String moTa;

    @Size(max = 100)
    private String diaChi;

    @Size(max = 10)
    private String sdt;

    @Email(message = "Email không hợp lệ")
    @Size(max = 100)
    private String email;

    @Size(max = 20)
    private TrangThaiDonVi trangThai;

}
