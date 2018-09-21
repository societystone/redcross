package com.app.dto;

import lombok.Data;

/**
 * 系统日志DTO . <br>
 * 
 * @author wangtw <br>
 */
@Data
public class SysLogDTO {

    /**
     * 用户操作
     */
    private String operation;

    /**
     * 用户名
     */
    private String username;

}
