package com.DATN.PhanAnhSuCoDoThi.enums;

/**
 * TrangThaiXacMinh - Trạng thái xác minh kết quả xử lý
 * CHO_XAC_MINH : Đơn vị đã báo hoàn thành, chờ TTDVC xác minh
 * DA_XAC_MINH  : TTDVC đã xác minh, kết quả đạt yêu cầu
 * CHUA_DAT     : TTDVC xác minh thấy chưa đạt, yêu cầu xử lý lại
 */
public enum TrangThaiXacMinh {
    CHO_XAC_MINH,
    DA_XAC_MINH,
    CHUA_DAT
}
