package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 系统日志 . <br>
 * 提供类所有属性的getting和 setting方法 <br>
 * 此外还提供了equals、canEqual、hashCode、toString方法-@Data <br>
 * 
 * 自动生成无参数构造函数-@NoArgsConstructor <br>
 * 自动生成全参数构造函数-@AllArgsConstructor
 * 
 * @author wangtw <br>
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SysLogEntity implements Serializable {

    /**
     * 序列化id
     */
    private static final long serialVersionUID = 2962671952846051639L;

    /**
     * id
     */
    private Long id;

    /**
     * 用户名
     */
    private String username;

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 请求方法
     */
    private String method;

    /**
     * 请求参数
     */
    private String params;

    /**
     * 执行时长(毫秒)
     */
    private Long time;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 创建时间@JSONField(format = "yyyy-MM-dd HH:mm:ss")
     */
    private Date createDate;

}
