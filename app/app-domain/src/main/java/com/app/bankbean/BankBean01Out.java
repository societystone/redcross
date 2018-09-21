package com.app.bankbean;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name="out")
public class BankBean01Out {
	
	@XmlElement(name="AccNo")          
	private String accNo         ;//账号                
	@XmlElement(name="AccName")        
	private String accName       ;//户名         
	@XmlElement(name="CurrType")       
	private String currType      ;//币种         
	@XmlElement(name="AreaCode")       
	private String areaCode      ;//地区代码     
	@XmlElement(name="NextTag")        
	private String nextTag       ;//查询下页标识 
	@XmlElement(name="TotalNum")       
	private String totalNum      ;//交易条数     
	@XmlElement(name="RepReserved1")   
	private String repReserved1  ;//响应备用字段1
	@XmlElement(name="RepReserved2")   
	private String repReserved2  ;//响应备用字段2
	@XmlElement(name="AcctSeq")   
	private String acctSeq  ;//账号序号

	@XmlElement(name="rd") 
	private List<BankBean01OutRd> rds;

}
