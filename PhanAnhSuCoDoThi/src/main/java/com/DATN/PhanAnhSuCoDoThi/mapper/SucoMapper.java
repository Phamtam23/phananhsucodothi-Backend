package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoDetailResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.Suco.SucoSummaryResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class SucoMapper {
    private final NhanVienMapper nhanVienMapper;
    private final DonViXuLyMapper donViXuLyMapper;
    public SucoSummaryResponse toSummary(SucoEntity e, List<TepSuCoEntity> medias, List<String> loaiSuCos) {
        if (e == null) return null;

        return SucoSummaryResponse.builder()
                .maSuCo(e.getMaSuCo())
                .noiDung(e.getNoiDung())
                .diaDiem(e.getDiaDiem())
                .trangThai(e.getTrangThai() != null ? e.getTrangThai().name() : null)
                .thoiGianTao(e.getThoiGianTao())
                .thumbnail(getThumbnail(medias))
                .loaiSuCos(loaiSuCos)
                .build();
    }

    public SucoDetailResponse toDetail(
            SucoEntity e,
            List<TepSuCoEntity> medias,
            List<String> loaiSuCos
    ) {
        if (e == null) return null;

        return SucoDetailResponse.builder()
                .maSuCo(e.getMaSuCo())
                .noiDung(e.getNoiDung())
                .diaDiem(e.getDiaDiem())
                .trangThai(e.getTrangThai() != null ? e.getTrangThai().name() : null)
                .thoiGianTao(e.getThoiGianTao())

                .maNguoiDan(e.getNguoiDan() != null ? e.getNguoiDan().getMaNguoiDan() : null)
                .kinhDo(e.getKinhDo())
                .viDo(e.getViDo())
                .ngayDuKienHoanThanh(e.getNgayDuKienHoanThanh())
                .loaiSuCos(loaiSuCos)
                .medias(mapMedia(medias))
                .build();
    }

    private String getThumbnail(List<TepSuCoEntity> medias) {
        if (medias == null || medias.isEmpty()) {
            return null;
        }

        return medias.get(0).getUrl();
    }

    private static List<MediaResponse> mapMedia(List<TepSuCoEntity> medias) {
        if (medias == null) return null;

        return medias.stream()
                .map(m -> MediaResponse.builder()
                        .url(m.getUrl())
                        .loai(m.getLoai())
                        .build()
                )
                .collect(Collectors.toList());
    }
}
