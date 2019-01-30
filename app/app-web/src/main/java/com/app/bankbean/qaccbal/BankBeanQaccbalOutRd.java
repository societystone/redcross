package com.app.bankbean.qaccbal;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BankBeanQaccbalOutRd {

	@XmlElement(name = "iSeqno")
	private String iSeqno;// 指令顺序号
	@XmlElement(name = "AccNo")
	private String accNo;// 账号
	@XmlElement(name = "CurrType")
	private String currType;// 币种
	@XmlElement(name = "CashExf")
	private String cashExf;// 钞汇标志
	@XmlElement(name = "AcctProperty")
	private String acctProperty;// 账户属性
	@XmlElement(name = "AccBalance")
	private String accBalance;// 昨日余额
	@XmlElement(name = "Balance")
	private String balance;// 当前余额
	@XmlElement(name = "UsableBalance")
	private String usableBalance;// 可用余额
	@XmlElement(name = "FrzAmt")
	private String frzAmt;// 冻结额度合计
	@XmlElement(name = "QueryTime")
	private String queryTime;// 查询时间
	@XmlElement(name = "iRetCode")
	private String iRetCode;// 明细交易返回码
	@XmlElement(name = "iRetMsg")
	private String iRetMsg;// 明细交易返回描述
	@XmlElement(name = "RepReserved3")
	private String repReserved3;// 行别
	@XmlElement(name = "AccName")
	private String accName;// 账户名称
	@XmlElement(name = "HoldAmt")
	private String holdAmt;// 保留余额
	@XmlElement(name = "LastIntrDate")
	private String lastIntrDate;// 最后计息日
	@XmlElement(name = "LastTranDate")
	private String lastTranDate;// 最后交易日
	@XmlElement(name = "InterDeposit")
	private String interDeposit;// 通存标志
	@XmlElement(name = "InterWithdrawal")
	private String interWithdrawal;// 通兑标志
	@XmlElement(name = "AcctSeq")
	private String acctSeq;// 账号序号
	@XmlElement(name = "MainAcctNo")
	private String mainAcctNo;// 安心托管主账户

}
