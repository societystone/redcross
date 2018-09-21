package com.app.bankbean;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)  
@XmlRootElement(name="in")
public class BankBean01In {
	
	@XmlElement(name="AccNo")       
	private String accNo;//查询账号
	@XmlElement(name="AreaCode")    
	private String areaCode;//地区代码
	@XmlElement(name="MinAmt")      
	private String minAmt;//发生额下限
	@XmlElement(name="MaxAmt")      
	private String maxAmt;//发生额上限
	@XmlElement(name="BeginTime")   
	private String beginTime;//开始时间
	@XmlElement(name="EndTime")     
	private String endTime;//终止时间
	@XmlElement(name="NextTag")     
	private String nextTag;//查询下页标识
	@XmlElement(name="ReqReserved1")
	private String reqReserved1;//请求包备用字段1
	@XmlElement(name="ReqReserved2")
	private String reqReserved2;//请求包备用字段2

}
