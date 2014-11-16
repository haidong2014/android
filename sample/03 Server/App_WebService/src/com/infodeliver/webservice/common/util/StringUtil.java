package com.infodeliver.webservice.common.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.CharRange;
import org.apache.commons.lang.CharSet;
import org.apache.commons.lang.StringUtils;


public class StringUtil {

    /**
     * ASCII字符（半角英数）表示{@link CharSet}的实例。
     */
    private static CharSet alphaNumChar = CharSet.getInstance("0-9A-Za-z");
    /**
     * ASCII字符（半角英数·半角符号）表示{@link CharSet}的实例。
     */
    private static CharSet ascii = CharSet.getInstance(" !-/0-9:-@A-Z[-`a-z{-~");
    /**
     * 半角数字表示{@link CharRange}的实例。
     */
    private static CharRange digit = new CharRange('\u0030', '\u0039');

    /**
     * 校验字符串是否只由半角数字组成的方法。
     *
     * @param str 校验的字符串
     * @return 只由半角数字0-9组成返回 true
     */
    public static boolean isDigit(String str) {
        boolean isdigit = false;
        if (StringUtils.isEmpty(str)) {
            return isdigit;
        }
        isdigit = true;
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (!digit.contains(cs[i])) {
                isdigit = false;
                break;
            }
        }
        return isdigit;
    }

    /**
     * 校验字符串是否全部是ASCII字符（半角英数·半角符号）方法。
     *
     * @param str 校验的字符串
     * @return ASCII字符（半角英数·半角符号）以外的返回 false
     */
    public static boolean isAsciiOnly(String str) {
        boolean asciiOnly = true;
        if (StringUtils.isEmpty(str)) {
            return asciiOnly;
        }
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (!ascii.contains(cs[i])) {
                asciiOnly = false;
                break;
            }
        }
        return asciiOnly;
    }

    /**
     * 校验字符串是否全部是ASCII字符（半角英数）方法。
     *
     * @param str 校验的字符串
     * @return ASCII字符（半角英数）以外的返回 false
     */
    public static boolean isAsciiAlphaNumCharOnly(String str) {
        boolean asciiOnly = true;
        if (StringUtils.isEmpty(str)) {
            return asciiOnly;
        }
        char[] cs = str.toCharArray();
        for (int i = 0; i < cs.length; i++) {
            if (!alphaNumChar.contains(cs[i])) {
                asciiOnly = false;
                break;
            }
        }
        return asciiOnly;
    }

    /**
     * 按照指定的位数，对字符串左侧进行补0的方法。
     *
     * @param str 变换前的字符串
     * @param length 位数
     * @return 左侧补0后的字符串
     */
    public static String leftPadZero(String str, int length) {
        str = StringUtils.leftPad(str, length);

        StringBuffer buf = new StringBuffer(str.length());
        boolean flag = true;
        char[] carray = str.toCharArray();
        for (int i = 0; i < carray.length; i++) {
            if (flag && carray[i] == ' ') {
                buf.append('0');
            }
            else {
                flag = false;
                buf.append(carray[i]);
            }
        }
        return buf.toString();
    }

    /**
     * 字符串前后空格（全角，半角）去除的方法。
     *
     * @param orgstr 变换前的字符串
     * @return 变换后的字符串
     */
    public static String trim(String orgstr) {
        while (orgstr.startsWith(" ") || orgstr.startsWith("　")) {
            orgstr = orgstr.substring(1);
        }
        while (orgstr.endsWith(" ") || orgstr.endsWith("　")) {
            orgstr = orgstr.substring(0, orgstr.length() - 1);
        }
        return orgstr;
    }

    /**
     * 校验字符串是null或者空的方法。
     *
     * @param str 校验字符串
     * @return 校验字符串是null或者空的情况返回true，否则返回false。
     *
     */
    public static boolean isNull(String str) {
        return str == null || str.equals("");
    }

    /**
     * trim后，校验字符串是null或者空的方法。
     *
     * @param str 検証文字列
     * @return trim后，校验字符串是null或者空的情况返回true，否则返回false。
     *
     */
    public static boolean isNullWithTrim(String str) {
        return str == null || str.trim().equals("");
    }

    /**
     * 将一个 Date 格式化为日期/时间字符串的方法。
     *
     * @param date 要格式化为时间字符串的时间值
     * @param pattern 描述日期和时间格式的模式
     * @return 已格式化的时间字符串
     */
    public static String convertDateString(Date date, String pattern) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    /**
     * 按照指定的位数，对字符串右侧进行补空格的方法。
     *
     * @param str 变换前的字符串
     * @param length 位数
     * @return 右侧补空格后的字符串
     */
    public static String rightPadSpace(String str, int len) {
        if (str.length() > len) {
            return str.substring(0, len);
        }
        else if (str.length() == len) {
            return str;
        }
        else {
            StringBuffer sb = new StringBuffer();
            for (int i = 0; i < len - str.length(); i++) {
                sb.append(" ");
            }
            return str + sb.toString();
        }
    }

    /**
     * 字符串null的情况返回空文字，以外的情况直接返回字符串的方法。
     *
     * @param str 字符串
     * @return 变换后的字符串
     */
    public static String null2Str(String str) {
        if (str == null) {
            return "";
        } else {
            return str;
        }
    }

    /**
     * 字符串右侧半角空格去除的方法。
     *
     * @param orgstr 变换前的字符串
     * @return 变换后的字符串
     */
    public static String rightSpaceTrim(String orgstr) {
        if (orgstr == null || orgstr.equals("")) {
            return orgstr;
        }
        while (orgstr.endsWith(" ")) {
            orgstr = orgstr.substring(0, orgstr.length() - 1);
        }
        return orgstr;
    }
}
