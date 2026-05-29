package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe.*;
import com.DATN.PhanAnhSuCoDoThi.entity.*;
import com.DATN.PhanAnhSuCoDoThi.enums.MucDoDanhGia;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiMoLai;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.repository.*;
import com.DATN.PhanAnhSuCoDoThi.service.IThongKeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ThongKeService implements IThongKeService {

    private final SucoRepository sucoRepository;
    private final TaikhoanRepository taikhoanRepository;
    private final DonViXuLyRepository donViXuLyRepository;
    private final LoaiRepository loaiRepository;
    private final PhieuPhanLoaiRepository phieuPhanLoaiRepository;
    private final NhanVienDonViRepository nhanVienDonViRepository;
    private final PhieuPhanCongRepository phieuPhanCongRepository;
    private final ChiTietPhanCongRepository chiTietPhanCongRepository;
    private final PhieuDanhGiaRepository phieuDanhGiaRepository;
    private final PhieuMoLaiRepository phieuMoLaiRepository;

    @Override
    public ThongKeHeThongResponse thongKeHeThong(Integer nam, Integer thang) {
        List<SucoEntity> tatCaSuCo = sucoRepository.findAll();
        List<PhieuPhanCongEntity> tatCaPhanCong = phieuPhanCongRepository.findAll();
        List<PhieuDanhGiaEntity> tatCaDanhGia = phieuDanhGiaRepository.findAll();
        List<PhieuMoLaiEntity> tatCaMoLai = phieuMoLaiRepository.findAll();

        return ThongKeHeThongResponse.builder()
                // Tổng quan — tính thẳng, không cần method riêng
                .tongSoSuCo(tatCaSuCo.size())
                .tongSuCoTrongNam(tatCaSuCo.stream()
                        .filter(sc -> sc.getThoiGianTao() != null && nam != null
                                && sc.getThoiGianTao().getYear() == nam).count())
                .tongSuCoTrongThang(tatCaSuCo.stream()
                        .filter(sc -> sc.getThoiGianTao() != null && nam != null && thang != null
                                && sc.getThoiGianTao().getYear() == nam
                                && sc.getThoiGianTao().getMonthValue() == thang).count())
                .suCoChuaXuLy(tatCaSuCo.stream()
                        .filter(sc -> sc.getTrangThai() == TrangThaiSuCo.CHO_TIEP_NHAN
                                || sc.getTrangThai() == TrangThaiSuCo.DA_TIEP_NHAN).count())
                .suCoDangXuLy(tatCaSuCo.stream()
                        .filter(sc -> sc.getTrangThai() == TrangThaiSuCo.DANG_XU_LY).count())
                .suCoDaXuLy(tatCaSuCo.stream()
                        .filter(sc -> sc.getTrangThai() == TrangThaiSuCo.DA_XU_LY_XONG
                                || sc.getTrangThai() == TrangThaiSuCo.DA_DONG).count())
                .tongSoTaiKhoan(taikhoanRepository.count())
                .tongSoDonVi(donViXuLyRepository.count())
                .tongSoLoai(loaiRepository.findAllByDeletedAtIsNull().size())
                // Chi tiết — giữ 3 method vì logic grouping đáng tách
                .suCoTheoLoai(thongKeTheoLoai(phieuPhanLoaiRepository.findAll()))
                .suCoTheoTrang(thongKeTheoTrangThai(tatCaSuCo))
                .suCoTheoThang(thongKeTheoThang(tatCaSuCo))
                // Đơn vị — giữ vì logic phức tạp nhất
                .bieuDoDonVi(tinhBieuDoDonVi(tatCaPhanCong, nam, thang))
                .bangThongKeDonVi(tinhBangThongKeDonVi(tatCaPhanCong, tatCaDanhGia, tatCaMoLai, nam, thang))
                .build();
    }


    private List<ThongKeLoaiItem> thongKeTheoLoai(List<PhieuPhanLoaiEntity> dsPhanLoai) {
        return dsPhanLoai.stream()
                .collect(Collectors.groupingBy(
                        pl -> pl.getLoai() != null ? pl.getLoai().getTenLoaiSuCo() : "Không rõ",
                        Collectors.counting()))
                .entrySet().stream()
                .map(e -> ThongKeLoaiItem.builder().tenLoai(e.getKey()).soLuong(e.getValue()).build())
                .sorted(Comparator.comparingLong(ThongKeLoaiItem::getSoLuong).reversed())
                .collect(Collectors.toList());
    }

    private List<ThongKeTrangThaiItem> thongKeTheoTrangThai(List<SucoEntity> dsSuCo) {
        return dsSuCo.stream()
                .filter(sc -> sc.getTrangThai() != null)
                .collect(Collectors.groupingBy(SucoEntity::getTrangThai, Collectors.counting()))
                .entrySet().stream()
                .map(e -> ThongKeTrangThaiItem.builder().trangThai(e.getKey().name()).soLuong(e.getValue()).build())
                .collect(Collectors.toList());
    }

    private List<ThongKeThangItem> thongKeTheoThang(List<SucoEntity> dsSuCo) {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("MM/yyyy");
        return dsSuCo.stream()
                .filter(sc -> sc.getThoiGianTao() != null)
                .collect(Collectors.groupingBy(sc -> sc.getThoiGianTao().format(fmt), Collectors.counting()))
                .entrySet().stream()
                .map(e -> ThongKeThangItem.builder().thang(e.getKey()).soLuong(e.getValue()).build())
                .sorted(Comparator.comparing(ThongKeThangItem::getThang))
                .collect(Collectors.toList());
    }


    private List<PhieuPhanCongEntity> locPhanCong(List<PhieuPhanCongEntity> ds, Integer nam, Integer thang) {
        return ds.stream()
                .filter(pc -> pc.getThoiGianTao() != null
                        && (nam == null || pc.getThoiGianTao().getYear() == nam)
                        && (thang == null || pc.getThoiGianTao().getMonthValue() == thang)
                        && pc.getDonViXuLy() != null && pc.getSuCo() != null)
                .collect(Collectors.toList());
    }

    private List<BieuDoDonViItem> tinhBieuDoDonVi(List<PhieuPhanCongEntity> ds, Integer nam, Integer thang) {
        return locPhanCong(ds, nam, thang).stream()
                .collect(Collectors.groupingBy(pc -> pc.getDonViXuLy().getTenDonVi(), Collectors.counting()))
                .entrySet().stream()
                .map(e -> BieuDoDonViItem.builder().tenDonVi(e.getKey()).soLuong(e.getValue()).build())
                .sorted(Comparator.comparingLong(BieuDoDonViItem::getSoLuong).reversed())
                .collect(Collectors.toList());
    }

    private List<BangThongKeDonViItem> tinhBangThongKeDonVi(
            List<PhieuPhanCongEntity> ds, List<PhieuDanhGiaEntity> dsDanhGia,
            List<PhieuMoLaiEntity> dsMoLai, Integer nam, Integer thang) {

        return locPhanCong(ds, nam, thang).stream()
                .collect(Collectors.groupingBy(pc -> pc.getDonViXuLy().getTenDonVi()))
                .entrySet().stream()
                .map(e -> {
                    List<PhieuPhanCongEntity> pcs = e.getValue();
                    long tong = pcs.size(), dangXuLy = 0, hoanThanh = 0,
                            soDanhGiaTot = 0, tongDanhGia = 0, soMoLai = 0;

                    for (PhieuPhanCongEntity pc : pcs) {
                        TrangThaiSuCo tt = pc.getSuCo().getTrangThai();
                        if (tt == TrangThaiSuCo.DANG_XU_LY) dangXuLy++;
                        else if (tt == TrangThaiSuCo.DA_XU_LY_XONG || tt == TrangThaiSuCo.DA_DONG) hoanThanh++;

                        String ma = pc.getMaPhieuPhanCong();
                        if (coDanhGia(dsDanhGia, ma))     tongDanhGia++;
                        if (laDanhGiaTot(dsDanhGia, ma))  soDanhGiaTot++;
                        if (laMoLaiChapNhan(dsMoLai, ma)) soMoLai++;
                    }

                    return BangThongKeDonViItem.builder()
                            .tenDonVi(e.getKey()).tongSuCo(tong)
                            .dangXuLy(dangXuLy).hoanThanh(hoanThanh)
                            .tiLeHoanThanh(lam1ChuSo(tinhTiLe(hoanThanh, tong)))
                            .tiLeDanhGiaTot(lam1ChuSo(tinhTiLe(soDanhGiaTot, tongDanhGia)))
                            .tiLeMoLai(lam1ChuSo(tinhTiLe(soMoLai, tong)))
                            .build();
                })
                .collect(Collectors.toList());
    }


    private boolean coDanhGia(List<PhieuDanhGiaEntity> ds, String ma) {
        return ds.stream().anyMatch(dg -> trungMa(dg, ma));
    }

    private boolean laDanhGiaTot(List<PhieuDanhGiaEntity> ds, String ma) {
        return ds.stream().anyMatch(dg -> trungMa(dg, ma)
                && (dg.getMucDoHaiLong() == MucDoDanhGia.HAI_LONG
                || dg.getMucDoHaiLong() == MucDoDanhGia.CHAP_NHAN));
    }

    private boolean laMoLaiChapNhan(List<PhieuMoLaiEntity> ds, String ma) {
        return ds.stream().anyMatch(ml ->
                ml.getKetQuaXuLy() != null
                        && ml.getKetQuaXuLy().getChiTietPhanCong() != null
                        && ml.getKetQuaXuLy().getChiTietPhanCong().getPhieuPhanCong() != null
                        && ml.getKetQuaXuLy().getChiTietPhanCong().getPhieuPhanCong().getMaPhieuPhanCong().equals(ma)
                        && ml.getTrangThaiMoLai() == TrangThaiMoLai.CHAP_NHAN);
    }

    private boolean trungMa(PhieuDanhGiaEntity dg, String ma) {
        return dg.getKetQuaXuLy() != null
                && dg.getKetQuaXuLy().getChiTietPhanCong() != null
                && dg.getKetQuaXuLy().getChiTietPhanCong().getPhieuPhanCong() != null
                && dg.getKetQuaXuLy().getChiTietPhanCong().getPhieuPhanCong().getMaPhieuPhanCong().equals(ma);
    }

    private double tinhTiLe(long tuSo, long mauSo) {
        return mauSo == 0 ? 0 : (double) tuSo / mauSo * 100;
    }

    private double lam1ChuSo(double value) {
        return Math.round(value * 10.0) / 10.0;
    }



    @Override
    public ThongKeDonViResponse thongKeDonVi(String maTaiKhoan, int nam) {
        NhanVienDonViEntity nv = nhanVienDonViRepository.findByTaiKhoan_MaTaiKhoan(maTaiKhoan)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin đơn vị của nhân viên"));
        String maDonVi = nv.getDonVi().getMaDonViXuLy();

        return ThongKeDonViResponse.builder()
                .tongSuCoTatCa(phieuPhanCongRepository.countByDonViXuLy_MaDonViXuLy(maDonVi))
                .tongSuCoTrongNam(phieuPhanCongRepository.countTongSuCoTrongNam(maDonVi,nam))
                .suCoTheoThang(buildThongKeTheoThang(maDonVi,nam))
                .suCoNhanVien(buidThongKeNhanVien(maDonVi))
                .build();
    }

    private List<ThongKeThangItem> buildThongKeTheoThang(String maDonVi,int nam)
    {
        List<ThongKeThangItem> danhSach = new ArrayList<>();

        for(int i =1 ; i<=12 ; ++i)
        {
            danhSach.add(new ThongKeThangItem("Tháng " + i, 0L));
        }

        List<Object[]> dulieuDb = phieuPhanCongRepository.countSuCoTheoThang(maDonVi,nam);

        for(Object[] obj : dulieuDb)
        {
            int thang = ((Number)obj[0]).intValue();
            long soLuong = ((Number)obj[1]).longValue();
            if(thang >=1 && thang<=12)
            {
                danhSach.get(thang-1).setSoLuong(soLuong);
            }
        }
        return danhSach;

    }

    private List<ThongKeNhanVienItem> buidThongKeNhanVien(String maDonVi)
    {
        List<ThongKeNhanVienItem> danhSach = new ArrayList<>();
        List<Object[]> dulieuDb = chiTietPhanCongRepository.countHoanThanhByNhanVien(maDonVi, com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiChiTietPhanCong.HOAN_THANH);

        for (Object[] obj : dulieuDb)
        {
            String tenNhanVien  = ((String)obj[0]);
            long  soLuong = ((Number)obj[1]).longValue();
            danhSach.add(new ThongKeNhanVienItem(tenNhanVien, soLuong));
        }

        return danhSach;
    }
}
