package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.DATN.PhanAnhSuCoDoThi.dto.response.PageResponse;
import com.DATN.PhanAnhSuCoDoThi.dto.response.ThongBaoResponse;
import com.DATN.PhanAnhSuCoDoThi.entity.ThongBaoEntity;
import com.DATN.PhanAnhSuCoDoThi.repository.ThongBaoRepository;
import com.DATN.PhanAnhSuCoDoThi.service.IThongBaoService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ThongBaoService implements IThongBaoService {

    private final ThongBaoRepository thongBaoRepository;

    @Override
    public PageResponse<ThongBaoResponse> getThongBaoByTaiKhoan(String maTaiKhoan, int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<ThongBaoEntity> entityPage = thongBaoRepository.findAllByTaiKhoan_MaTaiKhoanOrderByThoiGianTaoDesc(maTaiKhoan, pageable);
        return PageResponse.of(entityPage.map(ThongBaoResponse::fromEntity));
    }

    @Override
    public long getUnreadCount(String maTaiKhoan) {
        return thongBaoRepository.countUnreadByTaiKhoan(maTaiKhoan);
    }

    @Override
    public void markAsRead(String maThongBao, String maTaiKhoan) {
        ThongBaoEntity entity = thongBaoRepository.findById(maThongBao)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông báo"));
        if (!entity.getTaiKhoan().getMaTaiKhoan().equals(maTaiKhoan)) {
            throw new RuntimeException("Không có quyền truy cập thông báo này");
        }
        entity.setDaDoc(true);
        thongBaoRepository.save(entity);
    }

    @Override
    public void markAllAsRead(String maTaiKhoan) {
        Pageable unpaged = Pageable.unpaged();
        Page<ThongBaoEntity> entities = thongBaoRepository.findAllByTaiKhoan_MaTaiKhoanOrderByThoiGianTaoDesc(maTaiKhoan, unpaged);
        for (ThongBaoEntity entity : entities.getContent()) {
            if (!entity.getDaDoc()) {
                entity.setDaDoc(true);
                thongBaoRepository.save(entity);
            }
        }
    }
}
