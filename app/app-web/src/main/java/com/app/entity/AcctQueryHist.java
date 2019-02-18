package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 账户取数记录 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class AcctQueryHist extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private Long acctId;// 账户表ID
	private String beginDate;// 开始日期 YYYYMMDD
	private String beginTime;// 开始时间 HHmmss
	private String endDate;// 结束日期 YYYYMMDD
	private String endTime;// 结束时间 HHmmss
	private String isNotDownload;// 是否不再从银行下载 Y/N
	private String type;// 记录类型 1-自动任务 2-人工查询
	private String userCode;// 操作人账号
	private String userName;// 操作人姓名

}
