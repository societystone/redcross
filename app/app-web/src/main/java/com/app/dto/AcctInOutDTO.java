package com.app.dto;

import lombok.Data;

/**
 * 账户余额DTO . <br>
 * 
 * @author wangtw <br>
 */
@Data
public class AcctInOutDTO {

	private String bankName;// 银行名称
	private String acctNo;// 账户
	private String acctName;// 账户名称
	private String date;// 日期
	private String inAmount;// 收入
	private String outAmount;// 支出
	private String balance;// 余额

}
