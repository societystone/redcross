package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 单位 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class Company implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String code;// 单位编码
	private String kjnd;// 年度
	private String name;// 单位名称

}
