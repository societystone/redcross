package com.app.bankbean.qaccbal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BankBeanQaccbalInRd {

	@XmlElement(name = "iSeqno")
	private String iSeqno = "";// 指令顺序号
	@XmlElement(name = "AccNo")
	private String accNo = "";// 账号
	@XmlElement(name = "CurrType")
	private String currType = "";// 币种
	@XmlElement(name = "ReqReserved3")
	private String reqReserved3 = "";// 行别
	@XmlElement(name = "AcctSeq")
	private String acctSeq = "";// 账号序号
	@XmlElement(name = "MainAcctNo")
	private String mainAcctNo = "";// 安心托管主账户

}
