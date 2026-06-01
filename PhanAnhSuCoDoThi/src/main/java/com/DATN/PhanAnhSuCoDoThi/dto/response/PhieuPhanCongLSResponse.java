package com.DATN.PhanAnhSuCoDoThi.dto.response;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiPhanCong;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor

public class PhieuPhanCongLSResponse {

        private String maPhieuPhanCong;
        private TrangThaiPhanCong trangThai;
        private LocalDateTime thoiGianTao;
        private String ghiChu;
        private String lyDoTuChoi;
        private String maSuCo;
        private String tieuDe;
        private String diaDiem;
        private String thumbnail;
        private List<String> danhSachLoai;
}
