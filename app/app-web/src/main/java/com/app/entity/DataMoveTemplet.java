package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据迁移模板 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class DataMoveTemplet extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String name; // 模板名称

}
