package com.printwithbluetooth.escpos.command.sdk

import java.io.UnsupportedEncodingException
import java.util.Arrays

object PrinterCommand {

    /**
     * Khởi tạo máy in
     *
     * @return
     */
    fun POS_Set_PrtInit(): ByteArray {
        return Command.ESC_Init
    }

    /**
     * In và xuống dòng
     *
     * @return
     */
    fun POS_Set_LF(): ByteArray {
        return Command.LF
    }

    /**
     * In và di chuyển giấy (0~255)
     *
     * @param feed
     * @return
     */
    fun POS_Set_PrtAndFeedPaper(feed: Int): ByteArray? {
        if (feed > 255 || feed < 0) return null

        val data = Command.ESC_J.copyOf(Command.ESC_J.size)
        data[2] = feed.toByte()
        return data
    }

    /**
     * In trang tự kiểm tra
     *
     * @return
     */
    fun POS_Set_PrtSelfTest(): ByteArray {
        return Command.US_vt_eot
    }

    /**
     * Lệnh bíp âm báo
     *
     * @param m Số lần bíp
     * @param t Thời gian mỗi lần bíp
     * @return
     */
    fun POS_Set_Beep(m: Int, t: Int): ByteArray? {
        if ((m < 1 || m > 9) || (t < 1 || t > 9)) return null

        val data = Command.ESC_B_m_n.copyOf(Command.ESC_B_m_n.size)
        data[2] = m.toByte()
        data[3] = t.toByte()
        return data
    }

    /**
     * Lệnh cắt giấy (di chuyển đến vị trí cắt giấy và cắt giấy)
     *
     * @param cut 0~255
     * @return
     */
    fun POS_Set_Cut(cut: Int): ByteArray? {
        if (cut > 255 || cut < 0) return null

        val data = Command.GS_V_m_n.copyOf(Command.GS_V_m_n.size)
        data[3] = cut.toByte()
        return data
    }

    /**
     * Lệnh mở ngăn kéo tiền
     *
     * @param nMode
     * @param nTime1
     * @param nTime2
     * @return
     */
    fun POS_Set_Cashbox(nMode: Int, nTime1: Int, nTime2: Int): ByteArray? {
        if ((nMode < 0 || nMode > 1) || nTime1 < 0 || nTime1 > 255 || nTime2 < 0 || nTime2 > 255) return null

        val data = Command.ESC_p.copyOf(Command.ESC_p.size)
        data[2] = nMode.toByte()
        data[3] = nTime1.toByte()
        data[4] = nTime2.toByte()
        return data
    }

    /**
     * Thiết lập vị trí in tuyệt đối
     *
     * @param absolute
     * @return
     */
    fun POS_Set_Absolute(absolute: Int): ByteArray? {
        if (absolute > 65535 || absolute < 0) return null

        val data = Command.ESC_Absolute.copyOf(Command.ESC_Absolute.size)
        data[2] = (absolute % 0x100).toByte()
        data[3] = (absolute / 0x100).toByte()
        return data
    }

    /**
     * Thiết lập vị trí in tương đối
     *
     * @param relative
     * @return
     */
    fun POS_Set_Relative(relative: Int): ByteArray? {
        if (relative < 0 || relative > 65535) return null

        val data = Command.ESC_Relative.copyOf(Command.ESC_Relative.size)
        data[2] = (relative % 0x100).toByte()
        data[3] = (relative / 0x100).toByte()
        return data
    }

    /**
     * Thiết lập lề trái
     *
     * @param left
     * @return
     */
    fun POS_Set_LeftSP(left: Int): ByteArray? {
        if (left > 255 || left < 0) return null

        val data = Command.GS_LeftSp.copyOf(Command.GS_LeftSp.size)
        data[2] = (left % 0x100).toByte()
        data[3] = (left / 0x100).toByte()
        return data
    }

    /**
     * Thiết lập chế độ căn chỉnh
     *
     * @param align
     * @return
     */
    fun POS_S_Align(align: Int): ByteArray? {
        if ((align < 0 || align > 2) && (align < 48 || align > 50)) return null

        val data = Command.ESC_Align.copyOf(Command.ESC_Align.size)
        data[2] = align.toByte()
        return data
    }

    /**
     * Thiết lập chiều rộng vùng in
     *
     * @param width
     * @return
     */
    fun POS_Set_PrintWidth(width: Int): ByteArray? {
        if (width < 0 || width > 255) return null

        val data = Command.GS_W.copyOf(Command.GS_W.size)
        data[2] = (width % 0x100).toByte()
        data[3] = (width / 0x100).toByte()
        return data
    }

    /**
     * Thiết lập khoảng cách dòng mặc định
     *
     * @return
     */
    fun POS_Set_DefLineSpace(): ByteArray {
        return Command.ESC_Two
    }

    /**
     * Thiết lập khoảng cách dòng
     *
     * @param space
     * @return
     */
    fun POS_Set_LineSpace(space: Int): ByteArray? {
        if (space < 0 || space > 255) return null

        val data = Command.ESC_Three.copyOf(Command.ESC_Three.size)
        data[2] = space.toByte()
        return data
    }

    /**
     * Chọn trang mã ký tự
     *
     * @param page
     * @return
     */
    fun POS_Set_CodePage(page: Int): ByteArray? {
        if (page > 255) return null

        val data = Command.ESC_t.copyOf(Command.ESC_t.size)
        data[2] = page.toByte()
        return data
    }

    /**
     * In văn bản
     *
     * @param pszString    Chuỗi cần in
     * @param encoding     Mã hóa ký tự
     * @param codepage     Cài đặt mã trang (0--255)
     * @param nWidthTimes  Độ rộng nhân (0--4)
     * @param nHeightTimes Độ cao nhân (0--4)
     * @param nFontType    Loại phông chữ (chỉ áp dụng cho mã Ascii)(0,1 48,49)
     */
    fun POS_Print_Text(
      pszString: String?,
      encoding: String,
      codepage: Int,
      nWidthTimes: Int,
      nHeightTimes: Int,
      nFontType: Int
    ): ByteArray? {
      if (codepage < 0 || codepage > 255 || pszString.isNullOrEmpty()) {
          return null
      }

      val pbString: ByteArray?
      try {
          pbString = pszString.toByteArray(charset(encoding))
      } catch (e: UnsupportedEncodingException) {
          return null
      }

      val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30)
      val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03)
      val gsExclamationMark = Command.GS_ExclamationMark.copyOf()
      gsExclamationMark[2] = (intToWidth[nWidthTimes] + intToHeight[nHeightTimes]).toByte()
      val escT = Command.ESC_t.copyOf()
      escT[2] = codepage.toByte()
      val escM = Command.ESC_M.copyOf()
      escM[2] = nFontType.toByte()
      val data = concatAll(gsExclamationMark, escT, escM, pbString)

      return data
    }

    /**
    * Lệnh in đậm (chỉ có hiệu lực khi bit thấp nhất là 1)
    *
    * @param bold
    * @return
    */
    fun POS_Set_Bold(bold: Int): ByteArray {
      val escE = Command.ESC_E.copyOf()
      val escG = Command.ESC_G.copyOf()

      escE[2] = bold.toByte()
      escG[2] = bold.toByte()
      return concatAll(escE, escG)
    }

    /**
    * Thiết lập chế độ in ngược (hiệu lực khi bit thấp nhất là 1)
    *
    * @param brace
    * @return
    */
    fun POS_Set_LeftBrace(brace: Int): ByteArray {
      val data = Command.ESC_LeftBrace.copyOf()
      data[2] = brace.toByte()
      return data
    }

    /**
    * Thiết lập gạch chân
    *
    * @param line
    * @return
    */
    fun POS_Set_UnderLine(line: Int): ByteArray? {
      if (line < 0 || line > 2) return null
      val escMins = Command.ESC_Minus.copyOf()
      escMins[2] = line.toByte()
      val fsMinus = Command.FS_Minus.copyOf()
      fsMinus[2] = line.toByte()
      return concatAll(escMins, fsMinus)
    }

    /**
    * Chọn kích thước phông chữ (độ cao và độ rộng)
    *
    * @param size1
    * @param size2
    * @return
    */
    fun POS_Set_FontSize(size1: Int, size2: Int): ByteArray? {
      if (size1 !in 0..7 || size2 !in 0..7) return null
      val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30, 0x40, 0x50, 0x60, 0x70)
      val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03, 0x04, 0x05, 0x06, 0x07)
      val data = Command.GS_ExclamationMark.copyOf()
      data[2] = (intToWidth[size1] + intToHeight[size2]).toByte()
      return data
    }

    /**
    * Thiết lập in ngược màu
    *
    * @param inverse
    * @return
    */
    fun POS_Set_Inverse(inverse: Int): ByteArray {
      val data = Command.GS_B.copyOf()
      data[2] = inverse.toByte()
      return data
    }

    /**
    * Thiết lập xoay in 90 độ
    *
    * @param rotate
    * @return
    */
    fun POS_Set_Rotate(rotate: Int): ByteArray? {
      if (rotate !in 0..1) return null
      val data = Command.ESC_V.copyOf()
      data[2] = rotate.toByte()
      return data
    }

    /**
    * Chọn kiểu phông chữ
    *
    * @param font
    * @return
    */
    fun POS_Set_ChoseFont(font: Int): ByteArray? {
      if (font !in 0..1) return null
      val data = Command.ESC_M.copyOf()
      data[2] = font.toByte()
      return data
    }

    /**
    * Cắt giấy một điểm
    *
    * @return
    */
    fun POS_Cut_One_Point(): ByteArray {
      return Command.GS_i
    }

    /**
 * Hàm in mã QR
 *
 * @param str                   Dữ liệu mã QR cần in
 * @param nVersion              Loại mã QR
 * @param nErrorCorrectionLevel Mức độ sửa lỗi
 * @param nMagnification        Hệ số phóng đại
 * @return
 */
fun getQRCodeCommand(
    str: String,
    nVersion: Int,
    nErrorCorrectionLevel: Int,
    nMagnification: Int
): ByteArray? {

    if (nVersion < 0 || nVersion > 19 || nErrorCorrectionLevel < 0 || nErrorCorrectionLevel > 3
        || nMagnification < 1 || nMagnification > 8) {
        return null
    }

    val bCodeData: ByteArray
    try {
        bCodeData = str.toByteArray(charset("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
        return null
    }

    val command = ByteArray(bCodeData.size + 7)

    command[0] = 27
    command[1] = 90
    command[2] = nVersion.toByte()
    command[3] = nErrorCorrectionLevel.toByte()
    command[4] = nMagnification.toByte()
    command[5] = (bCodeData.size and 0xff).toByte()
    command[6] = ((bCodeData.size and 0xff00) shr 8).toByte()
    System.arraycopy(bCodeData, 0, command, 7, bCodeData.size)

    return command
}

/**
 * In mã vạch
 *
 * @param str              Ký tự mã vạch cần in
 * @param nType            Loại mã vạch (65~73)
 * @param nWidthX          Chiều rộng mã vạch
 * @param nHeight          Chiều cao mã vạch
 * @param nHriFontType     Kiểu chữ HRI
 * @param nHriFontPosition Vị trí HRI
 * @return
 */
fun getBarCodeCommand(
    str: String,
    nType: Int,
    nWidthX: Int,
    nHeight: Int,
    nHriFontType: Int,
    nHriFontPosition: Int
): ByteArray? {

    if (nType < 0x41 || nType > 0x49 || nWidthX < 2 || nWidthX > 6
        || nHeight < 1 || nHeight > 255 || str.isEmpty()) {
        return null
    }

    val bCodeData: ByteArray
    try {
        bCodeData = str.toByteArray(charset("UTF-8"))
    } catch (e: UnsupportedEncodingException) {
        e.printStackTrace()
        return null
    }

    val command = ByteArray(bCodeData.size + 16)

    command[0] = 29
    command[1] = 119
    command[2] = nWidthX.toByte()
    command[3] = 29
    command[4] = 104
    command[5] = nHeight.toByte()
    command[6] = 29
    command[7] = 102
    command[8] = (nHriFontType and 0x01).toByte()
    command[9] = 29
    command[10] = 72
    command[11] = (nHriFontPosition and 0x03).toByte()
    command[12] = 29
    command[13] = 107
    command[14] = nType.toByte()
    command[15] = bCodeData.size.toByte()
    System.arraycopy(bCodeData, 0, command, 16, bCodeData.size)

    return command
}

    /**
     * Thiết lập chế độ in (chọn phông chữ (font:A font:B), in đậm, phông chữ nhân cao rộng (tối đa 4 lần))
     *
     * @param str        Chuỗi cần in
     * @param bold       In đậm
     * @param font       Chọn kiểu chữ
     * @param widthsize  Kích thước nhân chiều rộng
     * @param heigthsize Kích thước nhân chiều cao
     * @return
     */
    fun POS_Set_Font(str: String, bold: Int, font: Int, widthsize: Int, heigthsize: Int): ByteArray? {
        if (str.isEmpty() || widthsize < 0 || widthsize > 4 || heigthsize < 0 || heigthsize > 4
            || font < 0 || font > 1) {
            return null
        }
        val strData: ByteArray
        try {
            strData = str.toByteArray(charset("UTF-8"))
        } catch (e: UnsupportedEncodingException) {
            e.printStackTrace()
            return null
        }
        val command = ByteArray(strData.size + 9)

        val intToWidth = byteArrayOf(0x00, 0x10, 0x20, 0x30) // tối đa bốn lần chiều rộng
        val intToHeight = byteArrayOf(0x00, 0x01, 0x02, 0x03) // tối đa bốn lần chiều cao

        command[0] = 27
        command[1] = 69
        command[2] = bold.toByte()
        command[3] = 27
        command[4] = 77
        command[5] = font.toByte()
        command[6] = 29
        command[7] = 33
        command[8] = (intToWidth[widthsize] + intToHeight[heigthsize]).toByte()

        System.arraycopy(strData, 0, command, 9, strData.size)
        return command
    }

    //**********************************************************************************************************//

    fun concatAll(first: ByteArray, vararg rest: ByteArray): ByteArray {
        var totalLength = first.size
        for (array in rest) {
            totalLength += array.size
        }
        val result = ByteArray(totalLength)
        var offset = first.size
        System.arraycopy(first, 0, result, 0, first.size)
        for (array in rest) {
            System.arraycopy(array, 0, result, offset, array.size)
            offset += array.size
        }
        return result
    }
}
