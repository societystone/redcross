package com.app.entity;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

/**
 * 账户交易流水 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class AcctTranHist extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private Long acctId;// 账户表ID
	private Integer drcrf;// 借贷标志
	private String vouhNo;// 凭证号
	private Double debitAmount;// 借方发生额
	private Double creditAmount;// 贷方发生额
	private Double balance;// 余额
	private String recipBkNo;// 对方行号
	private String recipBkName;// 对方行名
	private String recipAccNo;// 对方账号
	private String recipName;// 对方户名
	private String summary;// 摘要
	private String useCN;// 用途
	private String postScript;// 附言
	private String busCode;// 业务代码
	private String date;// 交易日期
	private String time;// 时间戳
	private String ref;// 业务编号
	private String oref;// 相关业务编号
	private String enSummary;// 英文备注
	private String busType;// 业务种类
	private String vouhType;// 凭证种类
	private String addInfo;// 附加信息
	private String toutfo;// 电子回单唯一标识
	private String onlySequence;// 银行交易流水号
	private String agentAcctName;// 财务公司二级账户户名（资金池次要对手账号户名）
	private String agentAcctNo;// 财务公司二级账户账号（资金池次要对手账号）
	private String upDtranf;// 冲正标志
	private String valueDate;// 起息日
	private String trxCode;// 工行交易代码
	private String ref1;// 业务编号1
	private String oref1;// 相关业务编号1
	private String cASHF;// 交易类型
	private String busiDate;// 入账日期
	private String busiTime;// 入账时间
	private String seqNo;// 顺序号
	private String mgNo;// 保证金编号
	private String mgAccNo;// 保证金缴存账号
	private String mgCurrType;// 保证金币种
	private String cashExf;// 钞汇标志
	private String detailNo;// 明细序号
	private String remark;// 备注
	private String tradeTime;// 交易时间
	private Double tradeFee;// 手续费
	private String tradeLocation;// 交易场所
	private String exRate;// 汇率
	private Integer forCurrtype;// 外币币种
	private String enAbstract;// 英文摘要
	private String openBankNo;// 开户行行号
	private String openBankBIC;// 开户行BIC
	private String openBankName;// 开户银行名称
	private String subAcctSeq;// 账号序号
	private Integer tHCurrency;// 币种
	private String recipBkName1;// 对方行名（以此为准）
	private String recipBkNo1;// 对方行号（以此为准）
	private String tInfoNew;// 电子回单唯一标识
	private String refundMsg;// 跨行退回原因
	private String busTypNo;// 回单业务种类
	private String receiptInfo;// 回单个性化信息
	private String telNo;// 柜员号
	private String mDCARDNO;// 本方财智账户卡卡号（管家卡卡号）
	private Double trxAmt;// 交易金额
	private String trxCurr;// 交易币种
	private String currType;// 币种
	private String dataLoadType;// 数据导入类型

}
