package com.app.bankbean.qbill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class BankBeanQbillIn {

	@XmlElement(name = "AccNo")
	private String accNo = "";// 查询账号
	@XmlElement(name = "BeginDate")
	private String beginDate = "";// 起始日期
	@XmlElement(name = "EndDate")
	private String endDate = "";// 终止日期
	@XmlElement(name = "BeginTime")
	private String beginTime = "";// 开始时间
	@XmlElement(name = "EndTime")
	private String endTime = "";// 终止时间
	@XmlElement(name = "MinAmt")
	private String minAmt = "";// 发生额下限
	@XmlElement(name = "MaxAmt")
	private String maxAmt = "";// 发生额上限
	@XmlElement(name = "BankType")
	private String bankType = "";// 行别
	@XmlElement(name = "NextTag")
	private String nextTag = "";// 查询下页标识
	@XmlElement(name = "CurrType")
	private String currType = "";// 币种

}
