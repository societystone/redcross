package com.app.bankbean.qbill;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BankBeanQbillOutRd {

	@XmlElement(name = "BillDate")
	private String billDate;// 对账单日期
	@XmlElement(name = "AccNo")
	private String accNo;// 账号
	@XmlElement(name = "AccName")
	private String accName;// 户名
	@XmlElement(name = "CurrType")
	private String currType;// 币种
	@XmlElement(name = "DetailNo")
	private String detailNo;// 交易序号
	@XmlElement(name = "SubCode")
	private String subCode;// 科目号
	@XmlElement(name = "BusiDate")
	private String busiDate;// 入账日期
	@XmlElement(name = "BusiTime")
	private String busiTime;// 时间戳
	@XmlElement(name = "RevTranf")
	private String revTranf;// 正反交易标志
	@XmlElement(name = "UpdTranf")
	private String updTranf;// 冲正标志
	@XmlElement(name = "Drcrf")
	private String drcrf;// 借贷标志
	@XmlElement(name = "VouhType")
	private String vouhType;// 凭证种类
	@XmlElement(name = "VouhNo")
	private String vouhNo;// 凭证号
	@XmlElement(name = "CvouhType")
	private String cvouhType;// 贷方凭证种类
	@XmlElement(name = "CvouhNo")
	private String cvouhNo;// 贷方凭证号
	@XmlElement(name = "Amount")
	private String amount;// 发生额
	@XmlElement(name = "Balance")
	private String balance;// 账务发生后余额
	@XmlElement(name = "ValueDay")
	private String valueDay;// 起息日期
	@XmlElement(name = "RecipBkNo")
	private String recipBkNo;// 对方行号
	@XmlElement(name = "RecipBkName")
	private String recipBkName;// 对方行名
	@XmlElement(name = "RecipAccNo")
	private String recipAccNo;// 对方账号
	@XmlElement(name = "RecipName")
	private String recipName;// 对方户名
	@XmlElement(name = "OregAccNo")
	private String oregAccNo;// 对方入账账号
	@XmlElement(name = "OregName")
	private String oregName;// 对方入账户名
	@XmlElement(name = "OregBkNo")
	private String oregBkNo;// 对方入账行号
	@XmlElement(name = "StatCode")
	private String statCode;// 外汇统计代码
	@XmlElement(name = "SettleMode")
	private String settleMode;// 外汇结算方式
	@XmlElement(name = "Summary")
	private String summary;// 摘要
	@XmlElement(name = "UseCN")
	private String useCN;// 用途
	@XmlElement(name = "PostScript")
	private String postScript;// 附言
	@XmlElement(name = "Ref")
	private String ref;// 业务编号
	@XmlElement(name = "Oref")
	private String oref;// 相关业务编号
	@XmlElement(name = "Sqn")
	private String sqn;// 流水号
	@XmlElement(name = "BusCode")
	private String busCode;// 业务代码
	@XmlElement(name = "EnSummary")
	private String enSummary;// 英文备注
	@XmlElement(name = "AddInfo")
	private String addInfo;// 附加信息
	@XmlElement(name = "ERPchkno")
	private String eRPchkno;// ERP支票号
	@XmlElement(name = "ERPeftRef")
	private String eRPeftRef;// 付款批次
	@XmlElement(name = "SignDate")
	private String signDate;// 签发日期
	@XmlElement(name = "ICBCSeqNo")
	private String iCBCSeqNo;// 银行流水号
	@XmlElement(name = "OnlySequence")
	private String onlySequence;// 主机唯一流水号
	@XmlElement(name = "AgentAcctName")
	private String agentAcctName;// 财务公司二级账户户名（资金池次要对手账号户名）
	@XmlElement(name = "AgentAcctNo")
	private String agentAcctNo;// 财务公司二级账户账号（资金池次要对手账号）
	@XmlElement(name = "CashSign")
	private String cashSign;// 现金标识
	@XmlElement(name = "BusType")
	private String busType;// 回单业务类型
	@XmlElement(name = "CARDNO")
	private String cARDNO;// 本方财智账户卡卡号（管家卡卡号）

}
