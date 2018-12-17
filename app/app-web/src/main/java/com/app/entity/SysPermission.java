package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统权限 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class SysPermission extends BaseEntity implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = 2384421022556466239L;
	private String name;//权限名

}
