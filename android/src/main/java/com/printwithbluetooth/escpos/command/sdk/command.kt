package com.printwithbluetooth.escpos.command.sdk

object Command {
    private val ESC: Byte = 0x1B
    private val FS: Byte = 0x1C
    private val GS: Byte = 0x1D
    private val US: Byte = 0x1F
    private val DLE: Byte = 0x10
    private val DC4: Byte = 0x14
    private val DC1: Byte = 0x11
    private val SP: Byte = 0x20
    private val NL: Byte = 0x0A
    private val FF: Byte = 0x0C
    val PIECE: Byte = 0xFF.toByte()
    val NUL: Byte = 0x00.toByte()

    // Khởi tạo máy in
    val ESC_Init = byteArrayOf(ESC, '@')

    /**
     * Lệnh in
     */
    // In và xuống dòng
    val LF = byteArrayOf(NL)

    // In và nạp giấy
    val ESC_J = byteArrayOf(ESC, 'J'.toByte(), 0x00)
    val ESC_d = byteArrayOf(ESC, 'd'.toByte(), 0x00)

    // In trang tự kiểm tra
    val US_vt_eot = byteArrayOf(US, DC1, 0x04)

    // Lệnh cảnh báo âm thanh
    val ESC_B_m_n = byteArrayOf(ESC, 'B'.toByte(), 0x00, 0x00)

    // Lệnh cắt giấy
    val GS_V_n = byteArrayOf(GS, 'V'.toByte(), 0x00)
    val GS_V_m_n = byteArrayOf(GS, 'V'.toByte(), 'B'.toByte(), 0x00)
    val GS_i = byteArrayOf(ESC, 'i'.toByte())
    val GS_m = byteArrayOf(ESC, 'm'.toByte())

    /**
     * Lệnh thiết lập ký tự
     */
    // Thiết lập khoảng cách ký tự
    val ESC_SP = byteArrayOf(ESC, SP, 0x00)

    // Thiết lập định dạng phông chữ in
    val ESC_ExclamationMark = byteArrayOf(ESC, '!'.toByte(), 0x00)

    // Thiết lập ký tự in phóng to chiều cao và chiều rộng
    val GS_ExclamationMark = byteArrayOf(GS, '!'.toByte(), 0x00)

    // Thiết lập in chữ ngược
    val GS_B = byteArrayOf(GS, 'B'.toByte(), 0x00)

    // Hủy/Chọn in xoay 90 độ
    val ESC_V = byteArrayOf(ESC, 'V'.toByte(), 0x00)

    // Chọn kiểu phông chữ (chủ yếu là mã ASCII)
    val ESC_M = byteArrayOf(ESC, 'M'.toByte(), 0x00)

    // Chọn/hủy chế độ in đậm
    val ESC_G = byteArrayOf(ESC, 'G'.toByte(), 0x00)
    val ESC_E = byteArrayOf(ESC, 'E'.toByte(), 0x00)

    // Chọn/hủy chế độ in ngược
    val ESC_LeftBrace = byteArrayOf(ESC, '{'.toByte(), 0x00)

    // Thiết lập độ cao gạch dưới (ký tự)
    val ESC_Minus = byteArrayOf(ESC, 45, 0x00)

    // Chế độ ký tự
    val FS_dot = byteArrayOf(FS, 46)

    // Chế độ chữ Hán
    val FS_and = byteArrayOf(FS, '&'.toByte())

    // Thiết lập chế độ in chữ Hán
    val FS_ExclamationMark = byteArrayOf(FS, '!'.toByte(), 0x00)

    // Thiết lập độ cao gạch dưới (chữ Hán)
    val FS_Minus = byteArrayOf(FS, 45, 0x00)

    // Thiết lập khoảng cách ký tự chữ Hán
    val FS_S = byteArrayOf(FS, 'S'.toByte(), 0x00, 0x00)

    // Chọn trang mã ký tự
    val ESC_t = byteArrayOf(ESC, 't'.toByte(), 0x00)

    /**
     * Lệnh thiết lập định dạng
     */
    // Thiết lập khoảng cách dòng mặc định
    val ESC_Two = byteArrayOf(ESC, 50)

    // Thiết lập khoảng cách dòng
    val ESC_Three = byteArrayOf(ESC, 51, 0x00)

    // Thiết lập chế độ căn chỉnh
    val ESC_Align = byteArrayOf(ESC, 'a'.toByte(), 0x00)

    // Thiết lập lề trái
    val GS_LeftSp = byteArrayOf(GS, 'L'.toByte(), 0x00, 0x00)

    // Thiết lập vị trí in tuyệt đối
    // Đặt vị trí hiện tại cách đầu dòng (nL + nH x 256).
    // Nếu vị trí thiết lập ngoài vùng in đã chỉ định, lệnh sẽ bị bỏ qua
    val ESC_Absolute = byteArrayOf(ESC, '$'.toByte(), 0x00, 0x00)

    // Thiết lập vị trí in tương đối
    val ESC_Relative = byteArrayOf(ESC, 92, 0x00, 0x00)

    // Thiết lập độ rộng vùng in
    val GS_W = byteArrayOf(GS, 'W'.toByte(), 0x00, 0x00)

    /**
     * Lệnh trạng thái
     */
    // Lệnh gửi trạng thái theo thời gian thực
    val DLE_eot = byteArrayOf(DLE, 0x04, 0x00)

    // Lệnh mở ngăn tiền theo thời gian thực
    val DLE_DC4 = byteArrayOf(DLE, DC4, 0x00, 0x00, 0x00)

    // Lệnh mở ngăn tiền chuẩn
    val ESC_p = byteArrayOf(ESC, 'p'.toByte(), 0x00, 0x00, 0x00)

    /**
     * Lệnh thiết lập mã vạch
     */
    // Chọn kiểu in HRI
    val GS_H = byteArrayOf(GS, 'H'.toByte(), 0x00)

    // Thiết lập chiều cao mã vạch
    val GS_h = byteArrayOf(GS, 'h'.toByte(), 0xA2.toByte())

    // Thiết lập độ rộng mã vạch
    val GS_w = byteArrayOf(GS, 'w'.toByte(), 0x00)

    // Thiết lập kiểu phông chữ HRI
    val GS_f = byteArrayOf(GS, 'f'.toByte(), 0x00)

    // Lệnh dịch trái mã vạch
    val GS_x = byteArrayOf(GS, 'x'.toByte(), 0x00)

    // Lệnh in mã vạch
    val GS_k = byteArrayOf(GS, 'k'.toByte(), 'A'.toByte(), FF)

    // Lệnh liên quan đến mã QR
    val GS_k_m_v_r_nL_nH = byteArrayOf(ESC, 'Z'.toByte(), 0x03, 0x03, 0x08, 0x00, 0x00)
}
