package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement 
public class BankBeanPubResponse {
	
	@XmlElement(name="TransCode")
	private String transCode;//交易代码    
	@XmlElement(name="CIS")      
	private String cIS;//集团CIS号   
	@XmlElement(name="BankCode") 
	private String bankCode;//归属银行编号
	@XmlElement(name="ID")       
	private String iD;//证书ID      
	@XmlElement(name="TranDate") 
	private String tranDate;//交易日期    
	@XmlElement(name="TranTime") 
	private String tranTime;//交易时间    
	@XmlElement(name="fSeqno")   
	private String fSeqno;//指令包序列号
	@XmlElement(name="RetCode")  
	private String retCode;//交易返回码  
	@XmlElement(name="RetMsg")   
	private String retMsg;//交易返回描述

}
