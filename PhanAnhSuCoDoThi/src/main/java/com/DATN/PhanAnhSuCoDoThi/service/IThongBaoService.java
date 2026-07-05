package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongBaoResponse;

public interface IThongBaoService {
    PageResponse<ThongBaoResponse> getThongBaoByTaiKhoan(String maTaiKhoan, int page, int size);
    long getUnreadCount(String maTaiKhoan);
    void markAsRead(String maThongBao, String maTaiKhoan);
    void markAllAsRead(String maTaiKhoan);
}
