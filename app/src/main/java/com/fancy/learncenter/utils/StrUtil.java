package com.fancy.learncenter.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by Hyy on 2016/9/22.
 */
public class StrUtil {


    /**
     * @param str 字符串
     * @return 如果字符串是数字返回ture，反正false
     */
    public static boolean isFormatInteger(String str) {
        if (str != null && !str.equals("") && isGigital(str)) {
            return true;
        }
        return false;
    }

    public static boolean isGigital(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isGigital = pattern.matcher(str);
        if (!isGigital.matches()) {
            return false;
        } else {
            return true;
        }
    }

    // 把字符串转换成UTF-8的格式
    public static String stringToUTF(String str) {
        if (str != null && !str.equals("")) {
            try {
                return URLEncoder.encode(str, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * @param str 字符串
     * @return 字符串转化MD5
     */
    public static String calcMd5(String str) {
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(str.getBytes());
            return toHexString(algorithm.digest(), "");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    // 上面的辅助类
    public static String toHexString(byte[] bytes, String separator) {
        StringBuilder hexString = new StringBuilder();
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        for (byte b : bytes) {
            hexString.append(hexDigits[b >> 4 & 0xf]);
            hexString.append(hexDigits[b & 0xf]);
        }
        return hexString.toString();
    }


    // 去掉字符串中的空格、回车、换行符、制表符
    public static String replaceBlank(String str) {
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            String after = m.replaceAll("");
            return after;
        } else {
            return null;
        }
    }
}
