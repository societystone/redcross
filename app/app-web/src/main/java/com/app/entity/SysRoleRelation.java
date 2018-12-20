package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统角色关联实体 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class SysRoleRelation extends BaseEntity implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -887896957120535214L;
	private String type;// 关联类型
	private Long roleId;// 角色id
	private Long relationId;// 关联表主键id

}
