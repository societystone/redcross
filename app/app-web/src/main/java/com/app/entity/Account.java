package com.app.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Account  implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  Long  id ;
	private String acctNo;
	private String acctName;
	private String status;
	private Date createDate;
	private Date modifyDate;
	private String userCode;
	private String userName;
	private String userCodeLastModify;
	private String userNameLastModify;
	
}
