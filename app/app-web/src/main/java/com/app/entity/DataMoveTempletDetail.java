package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 数据迁移模板详情 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class DataMoveTempletDetail extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private Long templetId; // 模板ID
	private String localColumn; // 本地字段
	private String remoteColumn; // 远程字段

}
