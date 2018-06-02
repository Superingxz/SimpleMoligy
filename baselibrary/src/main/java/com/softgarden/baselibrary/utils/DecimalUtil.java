package com.softgarden.baselibrary.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * Created by MirkoWu on 2017/2/22 0022.
 * 小数点格式化
 */

public class DecimalUtil {

    private DecimalUtil() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /*** 小数点后一位*/
    public static final String PATTERN_0_0 = "##0.0";
    /***  小数点后二位*/
    public static final String PATTERN_0_00 = "##0.00";
    /*** 小数点后三位*/
    public static final String PATTERN_0_000 = "##0.000";

    /**
     * 格式化小数点 double型
     *
     * @param num
     * @param pattern
     * @return
     */
    public static String formatDecimal(double num, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(num);
    }

    /**
     * 格式化小数点 float 型
     *
     * @param num
     * @param pattern
     * @return
     */
    public static String formatDecimal(float num, String pattern) {
        DecimalFormat format = new DecimalFormat(pattern);
        return format.format(num);
    }

    /**
     * 保留小数点后二位
     * @param num
     * @return
     */
    public static String formatDecimal2(double num) {
        return formatDecimal(num, PATTERN_0_00);
    }
    /**
     * 保留小数点后二位
     * @param num
     * @return
     */
    public static String formatDecimal2(float num) {
        return formatDecimal(num, PATTERN_0_00);
    }


    /**
     * 格式化为指定位小数的数字,返回未使用科学计数法表示的具有指定位数的字符串。
     * 该方法舍入模式：向“最接近的”数字舍入，如果与两个相邻数字的距离相等，则为向上舍入的舍入模式。
     * <pre>
     *  "3.1415926", 1          --> 3.1
     *  "3.1415926", 3          --> 3.142
     *  "3.1415926", 4          --> 3.1416
     *  "3.1415926", 6          --> 3.141593
     *  "1234567891234567.1415926", 3   --> 1234567891234567.142
     * </pre>
     * @param number
     * @param precision
     * @return
     */
    public static String keepPrecision(String number, int precision) {
        BigDecimal bg = new BigDecimal(number);
        return bg.setScale(precision, BigDecimal.ROUND_HALF_UP).toPlainString();
    }

}
