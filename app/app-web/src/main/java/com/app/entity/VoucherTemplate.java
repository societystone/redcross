package com.app.entity;

import java.io.Serializable;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

/**
 * 凭证模板 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class VoucherTemplate extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String name; // 模板名称
	private String status;// 状态
	private String isUse;// 是否被使用
	private String remark;// 备注

	private List<VoucherTemplateDetail> templateDetails;// 模板详情

}
