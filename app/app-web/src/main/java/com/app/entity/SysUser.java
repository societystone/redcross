package com.app.entity;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

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
	private String username;// 用户名
	private String password;// 密码
	private String realName;// 姓名
	private String companyCode;// 单位编码

	private Company company;// 单位

	private List<SysRole> roles;// 用户角色

	private Set<String> permissions;// 用户权限
	private List<Account> accts;// 账户权限

}
