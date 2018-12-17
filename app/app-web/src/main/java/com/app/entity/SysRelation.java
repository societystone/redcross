package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统关联实体 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class SysRelation extends BaseEntity implements Serializable {
	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -887896957120535214L;
	private String relationType;//关联类型
	private Long mainPrimaryId;//主表主键id
	private Long relPrimaryId;//关联表主键id

}
