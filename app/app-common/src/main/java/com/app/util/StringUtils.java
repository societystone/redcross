package com.app.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 字符串工具类 <br>
 * 
 * @author wangtw <br>
 */
public final class StringUtils {

    /**
     * 日志对象
     */
    private static Logger log = LoggerFactory.getLogger(StringUtils.class);

    /**
     * 私有构造函数
     */
    private StringUtils() {
        super();
    }

    /**
     * 判空(不包含空格)
     * 
     * @param cs
     * @return
     */
    public static boolean isEmpty(CharSequence cs) {
        return ((cs == null) || (cs.length() == 0));
    }

    /**
     * 判断不为空
     * 
     * @param cs
     * @return
     */
    public static boolean isNotEmpty(CharSequence cs) {
        return !isEmpty(cs);
    }

    /**
     * 判空(有空格也返回true)
     * 
     * @param cs
     * @return
     */
    public static boolean isBlank(CharSequence cs) {
        int strLen;
        if ((cs == null) || ((strLen = cs.length()) == 0)) {
            return true;
        }
        for (int i = 0; i < strLen; ++i) {
            if (!(Character.isWhitespace(cs.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 判断不为空
     * 
     * @param cs
     * @return
     */
    public static boolean isNotBlank(CharSequence cs) {
        return (!(isBlank(cs)));
    }

    /**
     * 字符串转Integer
     * 
     * @param in
     * @return
     */
    public static Integer stringToInteger(String in) {
        if (in == null) {
            return null;
        }
        in = in.trim();
        if (in.length() == 0) {
            return null;
        }
        try {
            return Integer.valueOf(Integer.parseInt(in));
        } catch (NumberFormatException e) {
            log.warn("stringToInteger fail,string=" + in, e);
        }
        return null;
    }

    /**
     * 字符串转Double
     * 
     * @param in
     * @return
     */
    public static Double stringToDouble(String in) {
        if (in == null) {
            return null;
        }
        in = in.trim();
        if (in.length() == 0) {
            return null;
        }
        try {
            return Double.valueOf(Double.parseDouble(in));
        } catch (NumberFormatException e) {
            log.warn("stringToDouble fail,string=" + in, e);
        }
        return null;
    }

    /**
     * 字符串比较方法
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equals(String a, String b) {
        if (a == null) {
            return (b == null);
        }
        return a.equals(b);
    }

    /**
     * 字符串比较忽略大小写
     * 
     * @param a
     * @param b
     * @return
     */
    public static boolean equalsIgnoreCase(String a, String b) {
        if (a == null) {
            return (b == null);
        }
        return a.equalsIgnoreCase(b);
    }

}
