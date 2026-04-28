package com.DATN.PhanAnhSuCoDoThi.mapper;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class SucoMapper {

    public SucoResponse toResponse(SucoEntity sucoEntity, List<TepSuCoEntity> tepSuCoEntity) {
        if (sucoEntity == null) return null;
        return SucoResponse.builder()
                .maSuCo(sucoEntity.getMaSuCo())
                .maNguoiDan(
                        sucoEntity.getNguoiDan() != null
                                ? sucoEntity.getNguoiDan().getMaNguoiDan()
                                : null
                )
                .kinhDo(sucoEntity.getKinhDo())
                .viDo(sucoEntity.getViDo())
                .diaDiem(sucoEntity.getDiaDiem())
                .noiDung(sucoEntity.getNoiDung())
                .trangThai(
                        sucoEntity.getTrangThai() != null
                                ? sucoEntity.getTrangThai().name()
                                : null
                )
                .ngayDuKienHoanThanh(sucoEntity.getNgayDuKienHoanThanh())
                .thoiGianTao(sucoEntity.getThoiGianTao())

                // media
                .medias(mapMedia(tepSuCoEntity))

                .build();

    }

    private static List<MediaResponse> mapMedia(List<TepSuCoEntity> medias) {
        if (medias == null) return null;

        return medias.stream()
                .map(m -> MediaResponse.builder()
                        .url(m.getUrl())
                        .type(m.getLoai())
                        .build()
                )
                .collect(Collectors.toList());
    }

}
