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
	private String name;//角色名称
	private Integer rank;//排序
	private String remark;//备注
	private String status;//状态

    private List<Long> menus;//角色菜单

}
