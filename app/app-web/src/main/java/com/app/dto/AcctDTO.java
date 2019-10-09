package com.app.dto;

import lombok.Data;

@Data
public class AcctDTO {
	private Long id;
	private String acctNo;// 账户
	private String acctName;// 账户名称
	private boolean flag;// true or false
}
