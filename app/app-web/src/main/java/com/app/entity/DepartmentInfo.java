package com.app.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 部门 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class DepartmentInfo extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -5112302456969173304L;
	private String name;//部门名称
	private Integer rank;//排序
	private String status;//状态
	private String remark;//备注
    
    private List<Long> roles;//用户角色

}
