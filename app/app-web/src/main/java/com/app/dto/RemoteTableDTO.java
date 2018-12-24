package com.app.dto;

import lombok.Data;

/**
 * 远程表字段DTO . <br>
 * 
 * @author wangtw
 *
 */
@Data
public class RemoteTableDTO {

	private String columnName;// 表字段名
	private String columnComment;// 表字段描述

}
