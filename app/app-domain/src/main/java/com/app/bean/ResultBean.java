package com.app.bean;

import java.io.Serializable;

import lombok.Data;

/**
 * 返回对象包装类(带泛型) .<br>
 * 
 * @author wangtw <br>
 */
@Data
public class ResultBean<T> implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = -6883482050087865474L;

    /**
     * 成功
     */
    public static final int SUCCESS = 0;

    /**
     * 失败
     */
    public static final int CHECK_FAIL = 1;

    /**
     * 未知异常
     */
    public static final int UNKNOWN_EXCEPTION = -99;

    /**
     * 返回的信息(主要出错的时候使用)
     */
    private String msg;

    /**
     * 接口返回码
     */
    private Integer code;

    /**
     * 返回的数据
     */
    private T data;

    /**
     * 构造函数
     */
    public ResultBean() {
        super();
    }

    /**
     * 有参构造函数
     * 
     * @param data
     */
    public ResultBean(T data) {
        super();
        init(data);
    }

    /**
     * 初始化
     */
    private void init(T data) {
        this.msg = "success";
        this.code = SUCCESS;
        this.data = data;
    }

}
