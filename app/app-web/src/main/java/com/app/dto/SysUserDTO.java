package com.app.dto;

import lombok.Data;

/**
 * 系统用户DTO . <br>
 * 
 * @author wangtw <br>
 */
@Data
public class SysUserDTO {

    private Long id;//用户ID
    private String username;//用户名
    private String password;//密码
    private String oldPassword;//旧密码

}
