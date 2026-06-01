package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe.ThongKeHeThongResponse;

import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongKe.ThongKeDonViResponse;

public interface IThongKeService {
    ThongKeHeThongResponse thongKeHeThong(Integer nam, Integer thang);
    ThongKeDonViResponse thongKeDonVi(String maTaiKhoan, int nam);
}
