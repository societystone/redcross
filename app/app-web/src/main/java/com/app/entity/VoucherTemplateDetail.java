package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 凭证模板详情 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class VoucherTemplateDetail extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private Long templateId; // 模板ID
	private String dataType; // 本地数据类型

}
