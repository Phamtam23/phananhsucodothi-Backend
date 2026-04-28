package com.DATN.PhanAnhSuCoDoThi.security;

import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    public static CustomUserDetails getCurrentUser() {
        return (CustomUserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();
    }

    public static String getCurrentRefMa() {
        return getCurrentUser().getRefMa();
    }

    public static String getCurrentMaTaiKhoan() {
        return getCurrentUser().getMaTaiKhoan();
    }

    public static String getCurrentEmail() {
        return getCurrentUser().getUsername();
    }

}
