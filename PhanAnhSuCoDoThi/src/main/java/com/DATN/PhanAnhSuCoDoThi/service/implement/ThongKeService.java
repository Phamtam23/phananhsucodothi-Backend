package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKeHeThongResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanLoaiEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.IThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ThongKeService implements IThongKeService {

    private final SucoRepository sucoRepository;
    private final TaikhoanRepository taikhoanRepository;
    private final DonViXuLyRepository donViXuLyRepository;
    private final LoaiRepository loaiRepository;
    private final PhieuPhanLoaiRepository phieuPhanLoaiRepository;

    @Override
    public ThongKeHeThongResponse thongKeHeThong() {

        // Lấy toàn bộ sự cố
        List<SucoEntity> tatCaSuCo = sucoRepository.findAll();

        long tongSoSuCo = tatCaSuCo.size();

        // Sự cố chưa xử lý = trạng thái CHO_TIEP_NHAN
        long suCoChuaXuLy = tatCaSuCo.stream()
                .filter(sc -> sc.getTrangThai() == TrangThaiSuCo.CHO_TIEP_NHAN
                        || sc.getTrangThai() == TrangThaiSuCo.DA_TIEP_NHAN)
                .count();

        // Sự cố đang xử lý = DANG_XU_LY
        long suCoDangXuLy = tatCaSuCo.stream()
                .filter(sc -> sc.getTrangThai() == TrangThaiSuCo.DANG_XU_LY)
                .count();

        // Sự cố đã xử lý = DA_XU_LY_XONG hoặc DA_DONG
        long suCoDaXuLy = tatCaSuCo.stream()
                .filter(sc -> sc.getTrangThai() == TrangThaiSuCo.DA_XU_LY_XONG
                        || sc.getTrangThai() == TrangThaiSuCo.DA_DONG)
                .count();

        long tongSoTaiKhoan = taikhoanRepository.count();
        long tongSoDonVi = donViXuLyRepository.count();
        long tongSoLoai = loaiRepository.findAllByDeletedAtIsNull().size();

        // Thống kê sự cố theo loại
        List<PhieuPhanLoaiEntity> tatCaPhanLoai = phieuPhanLoaiRepository.findAll();

        // Tính số lượng sự cố theo từng loại
        Map<String, Long> soLuongTheoLoai = tatCaPhanLoai.stream()
                .collect(Collectors.groupingBy(
                        pl -> pl.getLoai() != null ? pl.getLoai().getTenLoaiSuCo() : "Không rõ",
                        Collectors.counting()
                ));

        List<ThongKeHeThongResponse.ThongKeLoaiItem> suCoTheoLoai = soLuongTheoLoai.entrySet().stream()
                .map(e -> ThongKeHeThongResponse.ThongKeLoaiItem.builder()
                        .tenLoai(e.getKey())
                        .soLuong(e.getValue())
                        .build())
                .sorted(Comparator.comparingLong(ThongKeHeThongResponse.ThongKeLoaiItem::getSoLuong).reversed())
                .collect(Collectors.toList());

        // Thống kê sự cố theo trạng thái
        Map<TrangThaiSuCo, Long> soLuongTheoTrangThai = tatCaSuCo.stream()
                .filter(sc -> sc.getTrangThai() != null)
                .collect(Collectors.groupingBy(SucoEntity::getTrangThai, Collectors.counting()));

        List<ThongKeHeThongResponse.ThongKeTrangThaiItem> suCoTheoTrang = soLuongTheoTrangThai.entrySet().stream()
                .map(e -> ThongKeHeThongResponse.ThongKeTrangThaiItem.builder()
                        .trangThai(e.getKey().name())
                        .soLuong(e.getValue())
                        .build())
                .collect(Collectors.toList());

        // Thống kê sự cố theo tháng
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/yyyy");
        Map<String, Long> soLuongTheoThang = tatCaSuCo.stream()
                .filter(sc -> sc.getThoiGianTao() != null)
                .collect(Collectors.groupingBy(
                        sc -> sc.getThoiGianTao().format(formatter),
                        Collectors.counting()
                ));

        // Sắp xếp theo thứ tự thời gian
        List<ThongKeHeThongResponse.ThongKeThangItem> suCoTheoThang = soLuongTheoThang.entrySet().stream()
                .map(e -> ThongKeHeThongResponse.ThongKeThangItem.builder()
                        .thang(e.getKey())
                        .soLuong(e.getValue())
                        .build())
                .sorted(Comparator.comparing(ThongKeHeThongResponse.ThongKeThangItem::getThang))
                .collect(Collectors.toList());

        return ThongKeHeThongResponse.builder()
                .tongSoSuCo(tongSoSuCo)
                .suCoChuaXuLy(suCoChuaXuLy)
                .suCoDangXuLy(suCoDangXuLy)
                .suCoDaXuLy(suCoDaXuLy)
                .tongSoTaiKhoan(tongSoTaiKhoan)
                .tongSoDonVi(tongSoDonVi)
                .tongSoLoai(tongSoLoai)
                .suCoTheoLoai(suCoTheoLoai)
                .suCoTheoTrang(suCoTheoTrang)
                .suCoTheoThang(suCoTheoThang)
                .build();
    }
}
