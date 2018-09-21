package com.app.util;

import org.slf4j.MDC;

/**
 * 用户工具类 <br>
 * 
 * @author wangtw <br>
 */
public final class UserUtils {

    /**
     * 用户
     */
    private static final ThreadLocal<String> USER = new ThreadLocal<String>();

    /**
     * 用户名
     */
    public static final String KEY_USER = "user";

    /**
     * 私有构造函数
     */
    private UserUtils() {
        super();
    }

    public static void setUser(String userid) {
        USER.set(userid);
        // 把用户信息放到slf4j
        MDC.put(KEY_USER, userid);
    }

    /**
     * 如果没有登录,返回null
     * 
     * @return
     */
    public static String getUserIfLogin() {
        return USER.get();
    }

    /**
     * 如果没有登录会抛出异常
     * 
     * @return
     */
    public static String getUser() {
        String user = USER.get();

        if (user == null) {
            // TODO 抛出自定义的未登录异常
            // throw new UnLoginException();
        }
        return user;
    }

    /**
     * 清理所有用户信息
     */
    public static void clearAllUserInfo() {
        USER.remove();
        MDC.remove(KEY_USER);
    }

}
