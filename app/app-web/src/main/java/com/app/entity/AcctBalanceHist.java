package com.app.entity;

import java.io.Serializable;

import javax.xml.bind.annotation.XmlElement;

import lombok.Getter;
import lombok.Setter;

/**
 * 账户余额信息 . <br>
 * 
 * @author wangtw <br>
 */
@Setter
@Getter
public class AcctBalanceHist extends BaseEntity implements Serializable {

	/**
	 * 序列化id
	 */
	private static final long serialVersionUID = -1705182797058431420L;
	private String acctNo;// 账号
	private String date;// 日期
	private String currType;// 币种
	private String cashExf;// 钞汇标志
	private String acctProperty;// 账户属性
	private String accBalance;// 昨日余额
	private String balance;// 当前余额
	private String usableBalance;// 可用余额
	private String frzAmt;// 冻结额度合计
	private String queryTime;// 查询时间
	private String iRetCode;// 明细交易返回码
	private String iRetMsg;// 明细交易返回描述
	private String repReserved3;// 行别
	private String accName;// 账户名称
	private String holdAmt;// 保留余额
	private String lastIntrDate;// 最后计息日
	private String lastTranDate;// 最后交易日
	private String interDeposit;// 通存标志
	private String interWithdrawal;// 通兑标志
	private String acctSeq;// 账号序号
	private String mainAcctNo;// 安心托管主账户
	private String downType;//下载方式

}
