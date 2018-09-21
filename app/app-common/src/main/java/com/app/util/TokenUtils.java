package com.app.util;

import java.util.UUID;

/**
 * 生成token的工具类 . <br>
 * 
 * @author wangtw <br>
 */
public final class TokenUtils {

    /**
     * 私有构造函数
     */
    private TokenUtils() {
        super();
    }

    /**
     * 初始化
     */
    private static final TokenUtils INSTANCE = new TokenUtils();

    /**
     * 返回类的对象
     * 
     * @return
     */
    public static TokenUtils getInstance() {
        return INSTANCE;
    }

    /**
     * 生成token
     * 
     * @return
     */
    public String createToken() {
        String token = UUID.randomUUID().toString().replace("-", "");
        return token;
    }

}
