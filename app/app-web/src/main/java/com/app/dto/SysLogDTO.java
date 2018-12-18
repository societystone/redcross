package com.app.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

/**
 * 系统日志DTO . <br>
 * 
 * @author wangtw <br>
 */
@Data
public class SysLogDTO {

	private String username;// 用户名
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date beginDate;// 开始日期
	@JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
	private Date endDate;// 结束日期

}
