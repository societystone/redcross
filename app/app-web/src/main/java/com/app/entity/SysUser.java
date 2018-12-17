package com.app.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统用户 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class SysUser extends BaseEntity implements Serializable {

    /**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String username;//用户名
    private String password;//密码
    private String realName;//姓名
    private String sex;//性别
    private String phone;//手机号码
    private Long departmentId;//部门id
    private String status;//状态
    
    private List<Long> roles;//用户角色

}
