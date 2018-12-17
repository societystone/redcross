package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

/**
 * 部门 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5112302456969173304L;
	private Long id;//主键id
	private Date createDate;//创建时间
	private Date modifyDate;//修改时间

}
