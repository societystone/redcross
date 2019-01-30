package com.app.bankbean.qbill;

import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement(name = "out")
public class BankBeanQbillOut {

	@XmlElement(name = "AccNo")
	private String accNo;// 账号
	@XmlElement(name = "AccName")
	private String accName;// 户名
	@XmlElement(name = "CurrType")
	private String currType;// 币种
	@XmlElement(name = "SwiftBank")
	private String swiftBank;// Swift行号
	@XmlElement(name = "StartBalance")
	private String startBalance;// 期初余额
	@XmlElement(name = "EndBalance")
	private String endBalance;// 期末余额
	@XmlElement(name = "TotalNum")
	private String totalNum;// 交易条数
	@XmlElement(name = "NextTag")
	private String nextTag;// 查询下页标识

	@XmlElement(name = "rd")
	private List<BankBeanQbillOutRd> rds;

}
