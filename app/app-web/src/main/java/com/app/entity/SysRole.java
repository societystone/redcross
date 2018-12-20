package com.app.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统角色 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class SysRole extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 1818440402260608232L;
	private String code;// 角色代码
	private String name;// 角色名称

	private List<SysMenu> menus;// 角色菜单

	private List<SysRefDef> permissions;// 角色权限

}
