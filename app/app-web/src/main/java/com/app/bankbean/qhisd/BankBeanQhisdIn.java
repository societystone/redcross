package com.app.bankbean.qhisd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "in")
public class BankBeanQhisdIn {

	@XmlElement(name = "AccNo")
	private String accNo = "";// 查询账号
	@XmlElement(name = "BeginDate")
	private String beginDate = "";// 起始日期
	@XmlElement(name = "EndDate")
	private String endDate = "";// 截止日期
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
	@XmlElement(name = "DueBillNo")
	private String dueBillNo = "";// 借据编号
	@XmlElement(name = "AcctSeq")
	private String acctSeq = "";// 查询账号序号
	@XmlElement(name = "ComplementFlag")
	private String complementFlag = "";// 历史归档数据查询标志
	@XmlElement(name = "CardAccNoDef")
	private String cardAccNoDef = "";// 商务卡明细查询标志
	@XmlElement(name = "DesByTime")
	private String desByTime = "";// 明细是否按时间降序排序

}
