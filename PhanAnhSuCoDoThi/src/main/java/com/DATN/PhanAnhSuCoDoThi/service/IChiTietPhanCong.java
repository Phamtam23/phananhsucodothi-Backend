package com.DATN.PhanAnhSuCoDoThi.service;

import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.CreateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.request.ChiTietPhanCong.UpdateChiTietPhanCongRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ChiTietPhanCongResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ChiTietPhanCongEntity;

public interface IChiTietPhanCong {

    PageResponse<ChiTietPhanCongEntity> FindAllByNhanVienXuLy(String nhanVienXuLy, int page, int size);

    ChiTietPhanCongResponse  FindById(String maChiTietPhanCong);

    ChiTietPhanCongResponse Create(CreateChiTietPhanCongRequest createChiTietPhanCongRequest, String maTruongDonVi);

    ChiTietPhanCongResponse update(UpdateChiTietPhanCongRequest UpdateChiTietPhanCongRequest, String maChiTietPhanCong);

}
