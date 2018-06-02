package com.softgarden.baselibrary.utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * 类描述：MD5加密工具类
 */
public class MD5Util {

    private MD5Util() {
        throw new UnsupportedOperationException("u can't fuck me...");
    }

    /**
     * 将字符串进行MD5加密
     *
     * @param secretKey 密钥
     * @param pstr      被加密的字符串
     * @return
     */
    public static String ToMD5(String secretKey, String pstr) {
        //加密
        pstr = secretKey + pstr;
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            MessageDigest md5Temp = MessageDigest.getInstance("MD5");
            md5Temp.update(pstr.getBytes("UTF8"));
            byte[] md = md5Temp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str).toLowerCase();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 不带Key 的加密
     * 多用于加密登录密码等
     *
     * @param pstr
     * @return
     */
    public static String ToMD5NOKey(String pstr) {
        return ToMD5(null, pstr);
    }

    /**
     * @explain: 使用MD5对原始字符串进行加密
     * @author: Qiang
     * @version: 1.0.0
     * @date; 2017/11/1 10:35
     * @param: [originalString]
     * @return: java.lang.String
     */
    public static String encodeByMD5(String originalString) {
        byte[] bytes = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            bytes = md.digest(originalString.getBytes("UTF-8"));
        } catch (NoSuchAlgorithmException e) {
            return null;
        } catch (UnsupportedEncodingException e) {
            return null;
        }

        return byte2HexStr(bytes);
    }

    /**
     * @explain: 字节转换为十六位字符串
     * @author: Qiang
     * @version: 1.0.0
     * @date; 2017/11/1 10:36
     * @param: [bytes]
     * @return: java.lang.String
     */
    private static String byte2HexStr(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hexStr = Integer.toHexString(bytes[i] & 0xFF);
            if (hexStr.length() == 1) {
                sb.append("0");
            }
            sb.append(hexStr);
        }
        return sb.toString();
    }



}
