package com.DATN.PhanAnhSuCoDoThi.util;

import java.util.Random;
import java.util.UUID;

public class IdGenerator {
    private static final String CHAR_POOL = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
    private static final Random RANDOM = new Random();

    public static String generateMaSuCo(String maNguoiDan) {

        // lấy 4 ký tự cuối của mã người dân
        String last4 = getLast4(maNguoiDan);

        // random 4 ký tự
        String random4 = generateRandom(4);

        return "SC" + last4 + random4;
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

}
