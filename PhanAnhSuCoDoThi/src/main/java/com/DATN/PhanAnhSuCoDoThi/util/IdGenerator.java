package com.DATN.PhanAnhSuCoDoThi.util;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public static String generateMaSuCo(String maNguoiDan) {

        String prefix = "SC";

        String last2 = (maNguoiDan != null && maNguoiDan.length() >= 2)
                ? maNguoiDan.substring(maNguoiDan.length() - 2)
                : "00";

        String timePart = String.format("%04d",
                System.currentTimeMillis() % 10000
        );

        String randomPart = generateRandom(2);

        return prefix + last2 + timePart + randomPart;
    }

    private static String getLast4(String input) {
        if (input == null) return "0000";

        if (input.length() <= 4) return input;

        return input.substring(input.length() - 4);
    }

    private static String generateRandom(int length) {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < length; i++) {
            sb.append(CHAR_POOL.charAt(RANDOM.nextInt(CHAR_POOL.length())));
        }

        return sb.toString();
    }

    public static String generateMaPhieuKiemDuyet(String maNhanVienDieuPhoi) {

        // lấy 4 ký tự cuối của mã người dân
        String last4 = getLast4(maNhanVienDieuPhoi);

        // random 4 ký tự
        String random4 = generateRandom(4);

        return "KD" + last4 + random4;
    }

    public static String generateMaTaiKhoan() {

        String maTaiKhoan = "TK" + UUID.randomUUID().toString().substring(0, 8).toUpperCase();
         return  maTaiKhoan;
    }


    public static String generateMaTep() {
        return "TEP" + UUID.randomUUID().toString()
                .replace("-", "")
                .substring(0, 7)
                .toUpperCase();
    }

    public static String generateMaPhieuPhanCong(String maSuCo, String maDonVi) {
        return  "PC" + getLast4(maSuCo) + getLast4(maDonVi);
    }

    public static String geMaChiTietPhanCong(String maPhieuPhanCong, String maTruongDonVi)
    {
        return getLast4(maTruongDonVi) + getLast4(maPhieuPhanCong) + generateRandom(2);
    }

    public static String generateMaChiDao(String maPhieuPhanCong)
    {
        return getLast4(maPhieuPhanCong) + generateRandom(6);
    }

    public static String generateMaKetQuaXuLy(String maChiTietPhanCong)
    {
        return "KQ"
                + getLast4(maChiTietPhanCong)
                + generateRandom(4);
    }

    public static String generateMaPhieuDanhGia(String maKetQuaXuLy)
    {
        return "DG"
                + getLast4(maKetQuaXuLy)
                + generateRandom(4);
    }

    public static String generateMaPhieuMoLai(String maKetQuaXuLy)
    {
        return "ML"
                + getLast4(maKetQuaXuLy)
                + generateRandom(4);
    }

    public static String generateMaDonViSuCo()
    {
        return "DV"
                + generateRandom(8);
    }
}
