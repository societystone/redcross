package com.app.bankbean.qhisd;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;

import lombok.Data;

@Data
@XmlAccessorType(XmlAccessType.FIELD)
public class BankBeanQhisdOutRd {

	@XmlElement(name = "Drcrf")
	private String drcrf;// 借贷标志
	@XmlElement(name = "VouhNo")
	private String vouhNo;// 凭证号
	@XmlElement(name = "DebitAmount")
	private String debitAmount;// 借方发生额
	@XmlElement(name = "CreditAmount")
	private String creditAmount;// 贷方发生额
	@XmlElement(name = "Balance")
	private String balance;// 余额
	@XmlElement(name = "RecipBkNo")
	private String recipBkNo;// 对方行号
	@XmlElement(name = "RecipBkName")
	private String recipBkName;// 对方行名
	@XmlElement(name = "RecipAccNo")
	private String recipAccNo;// 对方账号
	@XmlElement(name = "RecipName")
	private String recipName;// 对方户名
	@XmlElement(name = "Summary")
	private String summary;// 摘要
	@XmlElement(name = "UseCN")
	private String useCN;// 用途
	@XmlElement(name = "PostScript")
	private String postScript;// 附言
	@XmlElement(name = "BusCode")
	private String busCode;// 业务代码
	@XmlElement(name = "Date")
	private String date;// 交易日期
	@XmlElement(name = "Time")
	private String time;// 时间戳
	@XmlElement(name = "Ref")
	private String ref;// 业务编号
	@XmlElement(name = "Oref")
	private String oref;// 相关业务编号
	@XmlElement(name = "EnSummary")
	private String enSummary;// 英文备注
	@XmlElement(name = "BusType")
	private String busType;// 业务种类
	@XmlElement(name = "VouhType")
	private String vouhType;// 凭证种类
	@XmlElement(name = "AddInfo")
	private String addInfo;// 附加信息
	@XmlElement(name = "Toutfo")
	private String toutfo;// 电子回单唯一标识
	@XmlElement(name = "OnlySequence")
	private String onlySequence;// 银行交易流水号
	@XmlElement(name = "AgentAcctName")
	private String agentAcctName;// 财务公司二级账户户名（资金池次要对手账号户名）
	@XmlElement(name = "AgentAcctNo")
	private String agentAcctNo;// 财务公司二级账户账号（资金池次要对手账号）
	@XmlElement(name = "UpDtranf")
	private String upDtranf;// 冲正标志
	@XmlElement(name = "ValueDate")
	private String valueDate;// 起息日
	@XmlElement(name = "TrxCode")
	private String trxCode;// 工行交易代码
	@XmlElement(name = "Ref1")
	private String ref1;// 业务编号1
	@XmlElement(name = "Oref1")
	private String oref1;// 相关业务编号1
	@XmlElement(name = "CASHF")
	private String cASHF;// 交易类型
	@XmlElement(name = "BusiDate")
	private String busiDate;// 入账日期
	@XmlElement(name = "BusiTime")
	private String busiTime;// 入账时间
	@XmlElement(name = "SeqNo")
	private String seqNo;// 顺序号
	@XmlElement(name = "MgNo")
	private String mgNo;// 保证金编号
	@XmlElement(name = "MgAccNo")
	private String mgAccNo;// 保证金缴存账号
	@XmlElement(name = "MgCurrType")
	private String mgCurrType;// 保证金币种
	@XmlElement(name = "CashExf")
	private String cashExf;// 钞汇标志
	@XmlElement(name = "DetailNo")
	private String detailNo;// 明细序号
	@XmlElement(name = "Remark")
	private String remark;// 备注
	@XmlElement(name = "TradeTime")
	private String tradeTime;// 交易时间
	@XmlElement(name = "TradeFee")
	private String tradeFee;// 手续费
	@XmlElement(name = "TradeLocation")
	private String tradeLocation;// 交易场所
	@XmlElement(name = "ExRate")
	private String exRate;// 汇率
	@XmlElement(name = "ForCurrtype")
	private String forCurrtype;// 外币币种
	@XmlElement(name = "EnAbstract")
	private String enAbstract;// 英文摘要
	@XmlElement(name = "OpenBankNo")
	private String openBankNo;// 开户行行号
	@XmlElement(name = "OpenBankBIC")
	private String openBankBIC;// 开户行BIC
	@XmlElement(name = "OpenBankName")
	private String openBankName;// 开户银行名称
	@XmlElement(name = "SubAcctSeq")
	private String subAcctSeq;// 账号序号
	@XmlElement(name = "THCurrency")
	private String tHCurrency;// 币种
	@XmlElement(name = "RecipBkName1")
	private String recipBkName1;// 对方行名（以此为准）
	@XmlElement(name = "RecipBkNo1")
	private String recipBkNo1;// 对方行号（以此为准）
	@XmlElement(name = "TInfoNew")
	private String tInfoNew;// 电子回单唯一标识
	@XmlElement(name = "RefundMsg")
	private String refundMsg;// 跨行退回原因
	@XmlElement(name = "BusTypNo")
	private String busTypNo;// 回单业务种类
	@XmlElement(name = "ReceiptInfo")
	private String receiptInfo;// 回单个性化信息
	@XmlElement(name = "TelNo")
	private String telNo;// 柜员号
	@XmlElement(name = "MDCARDNO")
	private String mDCARDNO;// 本方财智账户卡卡号（管家卡卡号）
	@XmlElement(name = "TrxAmt")
	private String trxAmt;// 交易金额
	@XmlElement(name = "TrxCurr")
	private String trxCurr;// 交易币种
	@XmlElement(name = "CurrType")
	private String currType;// 币种

}
