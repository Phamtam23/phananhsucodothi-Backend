package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.CreateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.UpdateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongLSResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;

import java.time.LocalDate;
import java.util.List;

public interface IChiTietPhanCongService {

    PageResponse<ChiTietPhanCongLSResponse> FindAllByNhanVienXuLy( String nhanVienXuLy, String keyword, LocalDate tuNgay, LocalDate denNgay, int page, int size);

    ChiTietPhanCongResponse  FindById(String maChiTietPhanCong);

    ChiTietPhanCongResponse Create(CreateChiTietPhanCongRequest createChiTietPhanCongRequest, String maTruongDonVi);

    ChiTietPhanCongResponse update(UpdateChiTietPhanCongRequest UpdateChiTietPhanCongRequest, String maChiTietPhanCong);

    List<ChiTietPhanCongResponse> FindAllByPhanCongId(String maPhieuPhanCong);
}
