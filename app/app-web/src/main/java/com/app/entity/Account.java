package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account extends BaseEntity implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String acctNo;
	private String acctName;
	private String bankNo;// 行号
	private String bankName;// 开户行名称
	private String bankShortName;// 开户行简称
	private String bankAdds;// 开户行地址
	private String opAcctCompany;// 开户单位
	private String opAcctDate;// 开户日期
	private String acctType;// 账户类型
	private String capitalNature;// 资金性质
	private String acctUse;// 账户用途
	private String capitalChannel;// 资金渠道
	private String status;

	private String userCode;
	private String userName;
	private String userCodeLastModify;
	private String userNameLastModify;
	private Long userId;

}
