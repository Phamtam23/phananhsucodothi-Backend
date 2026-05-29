package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.*;
import com.DATN.PhanAnhSuCoDoThi.dto.response.KetQuaXuLy.KetQuaXuLyDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.PhieuPhanCongEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PhieuPhanCongMapper {

    public PhieuPhanCongResponse toResponse(
            PhieuPhanCongEntity e
    ) {
        if (e == null) return null;

        return PhieuPhanCongResponse.builder()
                .maPhieuPhanCong(e.getMaPhieuPhanCong())
                .trangThai(e.getTrangThai())
                .ghiChu(e.getGhiChu())
                .lyDoTuChoi(e.getLyDoTuChoi())
                .thoiGianTao(e.getThoiGianTao())

                .maNhanVienDieuPhoi(
                        e.getNhanVienDieuPhoi().getMaNhanVienDieuPhoi()
                )

                .maDonViXuLy(
                      e.getDonViXuLy().getMaDonViXuLy()
                )

                .maSuCo(
                        e.getSuCo().getMaSuCo()
                )
                .build();
    }


    public PhieuPhanCongSCResponse toResponseSC(
            PhieuPhanCongEntity entity
    ) {

        return PhieuPhanCongSCResponse.builder()
                .maPhieuPhanCong(entity.getMaPhieuPhanCong())
                .maSuCo(entity.getSuCo().getMaSuCo())
                .maNhanVienDieuPhoi(
                        entity.getNhanVienDieuPhoi()
                                .getMaNhanVienDieuPhoi()
                )
                .trangThai(entity.getTrangThai())
                .thoiGianTao(entity.getThoiGianTao())

                .donViXuLy(
                        DonViXuLySCResponse.builder()
                                .maDonViXuLy(
                                        entity.getDonViXuLy().getMaDonViXuLy()
                                )
                                .tenDonVi(
                                        entity.getDonViXuLy().getTenDonVi()
                                )
                                .build()
                )
                .build();
    }

    public PhieuPhanCongLSResponse toLSResponse(PhieuPhanCongEntity entity) {
        SucoEntity suCo = entity.getSuCo();

        String thumbnail = suCo.getTepSuCoList().stream()
                .findFirst()
                .map(TepSuCoEntity::getUrl)
                .orElse(null);

        List<String> danhSachLoai = suCo.getDanhSachLoai().stream()
                .map(ppl -> ppl.getLoai().getTenLoaiSuCo())
                .toList();

        return PhieuPhanCongLSResponse.builder()
                .maPhieuPhanCong(entity.getMaPhieuPhanCong())
                .trangThai(entity.getTrangThai())
                .thoiGianTao(entity.getThoiGianTao())
                .ghiChu(entity.getGhiChu())
                .lyDoTuChoi(entity.getLyDoTuChoi())
                .maSuCo(suCo.getMaSuCo())
                .tieuDe(suCo.getTieuDe())
                .diaDiem(suCo.getDiaDiem())
                .thumbnail(thumbnail)
                .danhSachLoai(danhSachLoai)
                .build();
    }


}