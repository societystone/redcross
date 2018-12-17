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
    private static final ThreadLocal<Object> USER = new ThreadLocal<Object>();

    /**
     * 用户名
     */
    public static final String KEY_USER = "user";

    public static void setUser(Object o) {
        USER.set(o);
    }

    /**
     * 如果没有登录,返回null
     * 
     * @return
     */
    public static Object getUserIfLogin() {
        return USER.get();
    }

    /**
     * 如果没有登录会抛出异常
     * 
     * @return
     */
    public static Object getUser() {
        Object user = USER.get();

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
