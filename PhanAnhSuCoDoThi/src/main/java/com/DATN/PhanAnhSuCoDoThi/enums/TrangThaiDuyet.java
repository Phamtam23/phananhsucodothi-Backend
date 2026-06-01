package com.DATN.PhanAnhSuCoDoThi.enums;

/**
 * TrangThaiDuyet - Trạng thái duyệt sự cố bởi nhân viên TTDVC
 * CHO_DUYET   : Sự cố mới tạo, chờ TTDVC xem xét
 * DANG_DUYET  : TTDVC đang xem xét (lock để tránh xử lý trùng)
 * DA_DUYET    : TTDVC đã duyệt, đã tạo phiếu phân công
 * TU_CHOI     : TTDVC từ chối, sự cố không hợp lệ / spam
 */
public enum TrangThaiDuyet {
    CHO_DUYET,
    DANG_DUYET,
    DA_DUYET,
    TU_CHOI
}
