package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.response.MediaResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.TepKetQuaEntity;
import com.DATN.PhanAnhSuCoDoThi.repository.KetQuaXuLyRepository;
import com.DATN.PhanAnhSuCoDoThi.repository.TepKetQuaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class TepKetQuaService {
    private final TepKetQuaRepository tepKetQuaRepository;

    public List<MediaResponse> getMediasByMaKetQua(String maKetQua) {

        List<TepKetQuaEntity> tepKetQuaEntityList = tepKetQuaRepository.findAllByKetQua_MaKetQua( maKetQua);

        return tepKetQuaEntityList.stream()
                .map( tep -> MediaResponse.builder()
                        .url(tep.getUrl())
                        .loai(tep.getLoai())
                        .build()
                )
                .toList();
    }

    public Map<String,List<MediaResponse>> getMediasByKetQuas(List<String> maKetQuas) {
        if(maKetQuas==null||maKetQuas.size()==0)
            return null;

        return tepKetQuaRepository.findAllByKetQua_MaKetQuaIn(maKetQuas)
                .stream()
                .collect(Collectors.groupingBy(
                        tep -> tep.getKetQua().getMaKetQua(),
                        Collectors.mapping(
                                tep -> MediaResponse.builder()
                                        .url(tep.getUrl())
                                        .loai(tep.getLoai())
                                        .build(),
                                Collectors.toList()
                        )

                ));
    }
}
