package com.printwithbluetooth.escpos.command.sdk

import java.io.UnsupportedEncodingException;
import java.util.Arrays;

public class PrinterCommand {

    /**
     * Khởi tạo máy in
     *
     * @return byte[] lệnh khởi tạo
     */
    public static byte[] POS_Set_PrtInit() {
        return Command.ESC_Init;
    }

    /**
     * In và xuống dòng
     *
     * @return byte[] lệnh xuống dòng
     */
    public static byte[] POS_Set_LF() {
        return Command.LF;
    }

    /**
     * In và chạy giấy (0~255)
     *
     * @param feed lượng giấy cần in
     * @return byte[] lệnh in và chạy giấy
     */
    public static byte[] POS_Set_PrtAndFeedPaper(int feed) {
        if (feed > 255 || feed < 0) return null;

        byte[] data = Arrays.copyOf(Command.ESC_J, Command.ESC_J.length);
        data[2] = (byte) feed;
        return data;
    }

    /**
     * In trang tự kiểm tra
     *
     * @return byte[] lệnh in tự kiểm tra
     */
    public static byte[] POS_Set_PrtSelfTest() {
        return Command.US_vt_eot;
    }

    /**
     * Lệnh bíp
     *
     * @param m số lần bíp
     * @param t thời gian mỗi lần bíp
     * @return byte[] lệnh bíp
     */
    public static byte[] POS_Set_Beep(int m, int t) {
        if ((m < 1 || m > 9) || (t < 1 || t > 9)) return null;

        byte[] data = Arrays.copyOf(Command.ESC_B_m_n, Command.ESC_B_m_n.length);
        data[2] = (byte) m;
        data[3] = (byte) t;
        return data;
    }

    /**
     * Lệnh cắt giấy (chạy đến vị trí cắt và cắt giấy)
     *
     * @param cut 0~255
     * @return byte[] lệnh cắt giấy
     */
    public static byte[] POS_Set_Cut(int cut) {
        if (cut > 255 || cut < 0) return null;

        byte[] data = Arrays.copyOf(Command.GS_V_m_n, Command.GS_V_m_n.length);
        data[3] = (byte) cut;
        return data;
    }

    /**
     * Lệnh cho ngăn kéo tiền
     *
     * @param nMode chế độ
     * @param nTime1 thời gian 1
     * @param nTime2 thời gian 2
     * @return byte[] lệnh cho ngăn kéo tiền
     */
    public static byte[] POS_Set_Cashbox(int nMode, int nTime1, int nTime2) {
        if ((nMode < 0 || nMode > 1) || nTime1 < 0 || nTime1 > 255 || nTime2 < 0 || nTime2 > 255) return null;

        byte[] data = Arrays.copyOf(Command.ESC_p, Command.ESC_p.length);
        data[2] = (byte) nMode;
        data[3] = (byte) nTime1;
        data[4] = (byte) nTime2;
        return data;
    }

    /**
     * Đặt vị trí in tuyệt đối
     *
     * @param absolute vị trí tuyệt đối
     * @return byte[] lệnh đặt vị trí in tuyệt đối
     */
    public static byte[] POS_Set_Absolute(int absolute) {
        if (absolute > 65535 || absolute < 0) return null;

        byte[] data = Arrays.copyOf(Command.ESC_Absolute, Command.ESC_Absolute.length);
        data[2] = (byte) (absolute % 0x100);
        data[3] = (byte) (absolute / 0x100);
        return data;
    }

    /**
     * Đặt vị trí in tương đối
     *
     * @param relative vị trí tương đối
     * @return byte[] lệnh đặt vị trí in tương đối
     */
    public static byte[] POS_Set_Relative(int relative) {
        if (relative < 0 || relative > 65535) return null;

        byte[] data = Arrays.copyOf(Command.ESC_Relative, Command.ESC_Relative.length);
        data[2] = (byte) (relative % 0x100);
        data[3] = (byte) (relative / 0x100);
        return data;
    }

    /**
     * Đặt lề trái
     *
     * @param left lề trái
     * @return byte[] lệnh đặt lề trái
     */
    public static byte[] POS_Set_LeftSP(int left) {
        if (left > 255 || left < 0) return null;

        byte[] data = Arrays.copyOf(Command.GS_LeftSp, Command.GS_LeftSp.length);
        data[2] = (byte) (left % 0x100);
        data[3] = (byte) (left / 0x100);
        return data;
    }

    /**
     * Đặt chế độ căn chỉnh
     *
     * @param align chế độ căn chỉnh
     * @return byte[] lệnh đặt căn chỉnh
     */
    public static byte[] POS_S_Align(int align) {
        if ((align < 0 || align > 2) && (align < 48 || align > 50)) return null;

        byte[] data = Arrays.copyOf(Command.ESC_Align, Command.ESC_Align.length);
        data[2] = (byte) align;
        return data;
    }

    /**
     * Đặt chiều rộng vùng in
     *
     * @param width chiều rộng
     * @return byte[] lệnh đặt chiều rộng
     */
    public static byte[] POS_Set_PrintWidth(int width) {
        if (width < 0 || width > 255) return null;

        byte[] data = Arrays.copyOf(Command.GS_W, Command.GS_W.length);
        data[2] = (byte) (width % 0x100);
        data[3] = (byte) (width / 0x100);
        return data;
    }

    /**
     * Đặt khoảng cách dòng mặc định
     *
     * @return byte[] lệnh đặt khoảng cách dòng mặc định
     */
    public static byte[] POS_Set_DefLineSpace() {
        return Command.ESC_Two;
    }

    /**
     * Đặt khoảng cách dòng
     *
     * @param space khoảng cách
     * @return byte[] lệnh đặt khoảng cách dòng
     */
    public static byte[] POS_Set_LineSpace(int space) {
        if (space < 0 || space > 255) return null;

        byte[] data = Arrays.copyOf(Command.ESC_Three, Command.ESC_Three.length);
        data[2] = (byte) space;
        return data;
    }

    /**
     * Chọn mã ký tự
     *
     * @param page trang mã ký tự
     * @return byte[] lệnh chọn mã ký tự
     */
    public static byte[] POS_Set_CodePage(int page) {
        if (page > 255) return null;

        byte[] data = Arrays.copyOf(Command.ESC_t, Command.ESC_t.length);
        data[2] = (byte) page;
        return data;
    }

    /**
     * In văn bản
     *
     * @param pszString    chuỗi cần in
     * @param encoding     mã hóa ký tự
     * @param codepage     mã trang (0--255)
     * @param nWidthTimes  chiều rộng gấp đôi (0--4)
     * @param nHeightTimes chiều cao gấp đôi (0--4)
     * @param nFontType    loại phông chữ (0,1 48,49)
     * @return byte[] lệnh in văn bản
     */
    public static byte[] POS_Print_Text(String pszString, String encoding, int codepage,
                                        int nWidthTimes, int nHeightTimes, int nFontType) {
        if (codepage < 0 || codepage > 255 || pszString == null || "".equals(pszString) || pszString.length() < 1) {
            return null;
        }

        byte[] pbString;
        try {
            pbString = pszString.getBytes(encoding);
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        byte[] intToWidth = {0x00, 0x10, 0x20, 0x30};
        byte[] intToHeight = {0x00, 0x01, 0x02, 0x03};
        byte[] gsExclamationMark = Arrays.copyOf(Command.GS_ExclamationMark, Command.GS_ExclamationMark.length);
        gsExclamationMark[2] = (byte) (intToWidth[nWidthTimes] + intToHeight[nHeightTimes]);
        byte[] escT = Arrays.copyOf(Command.ESC_t, Command.ESC_t.length);
        escT[2] = (byte) codepage;
        byte[] escM = Arrays.copyOf(Command.ESC_M, Command.ESC_M.length);
        escM[2] = (byte) nFontType;

        byte[] data = new byte[gsExclamationMark.length + escT.length + escM.length + pbString.length + 1];
        System.arraycopy(gsExclamationMark, 0, data, 0, gsExclamationMark.length);
        System.arraycopy(escT, 0, data, gsExclamationMark.length, escT.length);
        System.arraycopy(escM, 0, data, gsExclamationMark.length + escT.length, escM.length);
        System.arraycopy(pbString, 0, data, gsExclamationMark.length + escT.length + escM.length, pbString.length);
        data[data.length - 1] = 0x0A;

        return data;
    }

    /**
     * In ảnh bitmap
     *
     * @param bitmap        bitmap cần in
     * @param nWidthTimes   chiều rộng gấp đôi
     * @param nHeightTimes  chiều cao gấp đôi
     * @param nHalftone     chế độ in (0,1)
     * @return byte[] lệnh in ảnh
     */
    public static byte[] POS_Print_Bitmap(Bitmap bitmap, int nWidthTimes, int nHeightTimes, int nHalftone) {
        if (bitmap == null) return null;

        int width = bitmap.getWidth();
        int height = bitmap.getHeight();
        byte[] widthByte = new byte[3];
        widthByte[0] = Command.GS_L + (byte) (width % 256);
        widthByte[1] = (byte) (width / 256);
        widthByte[2] = (byte) (nHalftone);

        byte[] bitmapData = new byte[width * height / 8];
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (bitmap.getPixel(j, i) == Color.BLACK) {
                    bitmapData[(width / 8) * i + j / 8] |= (1 << (7 - (j % 8)));
                }
            }
        }

        byte[] data = new byte[widthByte.length + bitmapData.length];
        System.arraycopy(widthByte, 0, data, 0, widthByte.length);
        System.arraycopy(bitmapData, 0, data, widthByte.length, bitmapData.length);
        return data;
    }
}
