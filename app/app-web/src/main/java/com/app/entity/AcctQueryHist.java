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
	private static final long serialVersionUID = 1L;
	private String acctNo;// 账号
	private String acctName;// 账户名称
	private String bankName;// 银行名称
	private String queryDate;// 查询日期 YYYYMMDD
	private String isNotDownload;// 是否不再从银行下载 Y/N
	private String userCode;// 操作人账号
	private String userName;// 操作人姓名

}
