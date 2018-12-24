package com.app.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据迁移模板 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class DataMoveTemplate extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String name; // 模板名称
	private String status;// 状态
	private String isUse;// 是否被使用
	private String remark;// 备注

	private List<DataMoveTemplateDetail> templateDetails;// 模板详情

}
