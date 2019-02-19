package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account extends BaseEntity implements Serializable  {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String acctNo;
	private String acctName;
	private String status;

	private String userCode;
	private String userName;
	private String userCodeLastModify;
	private String userNameLastModify;
	
}
