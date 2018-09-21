package com.app.exception;

/**
 * 自定义的校验异常 .<br>
 * 
 * @author wangtw <br>
 */
public class CheckException extends RuntimeException {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 7253936352553488578L;

    /**
     * 无参构造函数
     */
    public CheckException() {
        super();
    }

    /**
     * 有参构造函数
     * 
     * @param message
     */
    public CheckException(String message) {
        super(message);
    }

    /**
     * 有参构造函数
     * 
     * @param cause
     */
    public CheckException(Throwable cause) {
        super(cause);
    }

    /**
     * 有参构造函数
     * 
     * @param message
     * @param cause
     */
    public CheckException(String message, Throwable cause) {
        super(message, cause);
    }

    /**
     * 有参构造函数
     * 
     * @param message
     * @param cause
     * @param enableSuppression
     * @param writableStackTrace
     */
    public CheckException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}
