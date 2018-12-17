package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 系统日志 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class SysLog extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String username; // 用户名
	private String operation; // 操作
	private String method; // 方法名
	private String params; // 参数
	private String ip; // ip地址

}
