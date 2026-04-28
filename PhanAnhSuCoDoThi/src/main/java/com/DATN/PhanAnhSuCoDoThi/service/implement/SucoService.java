package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.request.Suco.CreateSucoRequest;
import com.DATN.PhanAnhSuCoDoThi.dto.response.SucoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.NguoidanEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.SucoEntity;
import com.DATN.PhanAnhSuCoDoThi.entity.TepSuCoEntity;
import com.DATN.PhanAnhSuCoDoThi.enums.TrangThaiSuCo;
import com.DATN.PhanAnhSuCoDoThi.mapper.SucoMapper;
import com.DATN.PhanAnhSuCoDoThi.respository.SucoRespository;
import com.DATN.PhanAnhSuCoDoThi.respository.TepSuCoRepository;
import com.DATN.PhanAnhSuCoDoThi.respository.NguoidanRepository;
import com.DATN.PhanAnhSuCoDoThi.service.ISucoService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.DATN.PhanAnhSuCoDoThi.util.IdGenerator.generateMaSuCo;

@Service
public class SucoService implements ISucoService {

    private final SucoRespository sucoRespository;
    private final TepSuCoRepository tepSuCoRepository;
    private final SucoMapper sucoMapper;
    private final NguoidanRepository nguoidanRepository;

    public SucoService(SucoRespository sucoRespository, TepSuCoRepository tepSuCoRepository, SucoMapper sucoMapper, NguoidanRepository nguoidanRepository) {
        this.sucoRespository = sucoRespository;
        this.tepSuCoRepository = tepSuCoRepository;
        this.sucoMapper = sucoMapper;
        this.nguoidanRepository = nguoidanRepository;
    }


    @Override
    public List<SucoResponse> findAll() {
        List<SucoEntity> sucos = sucoRespository.findAll();

        // lấy list mã sự cố
        List<String> maSuCos = sucos.stream()
                .map(SucoEntity::getMaSuCo)
                .toList();

        // lấy toàn bộ media 1 lần
        List<TepSuCoEntity> allMedia =
                tepSuCoRepository.findBySuCo_MaSuCoIn(maSuCos);

        // group theo maSuCo
        Map<String, List<TepSuCoEntity>> mediaMap =
                allMedia.stream()
                        .collect(Collectors.groupingBy(
                                m -> m.getSuCo().getMaSuCo()
                        ));

        return sucos.stream()
                .map(suco -> sucoMapper.toResponse(
                        suco,
                        mediaMap.getOrDefault(
                                suco.getMaSuCo(),
                                List.of()
                        )
                ))
                .toList();

    }

    @Override
    public SucoResponse findById(String maSuco) {

        SucoEntity sucoEntity;
        sucoEntity = sucoRespository.findById(maSuco)
                .orElseThrow(()-> new RuntimeException("Khong tim thấy sự cố"));
        List<TepSuCoEntity> listTepSuCo = tepSuCoRepository.findBySuCo_MaSuCo(sucoEntity.getMaSuCo());
        return sucoMapper.toResponse(sucoEntity, listTepSuCo);
    }
    @Override
    public SucoResponse create(CreateSucoRequest request) {

        // 1. tìm người dân
        NguoidanEntity nguoiDan =nguoidanRepository.findById (request.getMaNguoiDan())
                .orElseThrow(() -> new RuntimeException("Người dân không tồn tại"));

        // 2. tạo sự cố
        SucoEntity suco = new SucoEntity();
        suco.setMaSuCo(generateMaSuCo(request.getMaNguoiDan())); // tự generate
        suco.setNguoiDan(nguoiDan);
        suco.setKinhDo(request.getKinhDo());
        suco.setViDo(request.getViDo());
        suco.setDiaDiem(request.getDiaDiem());
        suco.setNoiDung(request.getNoiDung());

        // default
        suco.setTrangThai(TrangThaiSuCo.CHO_TIEP_NHAN);
        suco.setLaSpam(false);
        suco.setDiemSpam(0);
        suco.setThoiGianTao(LocalDateTime.now());

        // 3. save suco trước
        sucoRespository.save(suco);

        // 4. lưu danh sách tệp
        List<TepSuCoEntity> tepList = new ArrayList<>();

        if (request.getMediaUrls() != null && !request.getMediaUrls().isEmpty()) {

            for (String url : request.getMediaUrls()) {

                TepSuCoEntity tep = new TepSuCoEntity();
                tep.setSuCo(suco);
                tep.setUrl(url);

                // auto detect type
                if (url.endsWith(".mp4") || url.endsWith(".mov")) {
                    tep.setLoai("VIDEO");
                } else {
                    tep.setLoai("IMAGE");
                }

                tepList.add(tep);
            }

            tepSuCoRepository.saveAll(tepList);
        }

        // 5. trả response
        return sucoMapper.toResponse(suco, tepList);
    }
}
