package com.app.service;

import java.util.List;

import com.app.bankbean.qaccbal.BankBeanQaccbalOutRd;
import com.app.bankbean.qhisd.BankBeanQhisdOutRd;
import com.app.bankbean.qpd.BankBeanQpdOutRd;

/**
 * 银行数据接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface BankDataService {

	/**
	 * 从银行下载历史交易数据
	 * 
	 * @param acctNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	List<BankBeanQhisdOutRd> downloadHistDayBankData(String acctNo, String beginDate, String endDate);

	/**
	 * 从银行下载 当日交易数据
	 * 
	 * @param acctNo
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	List<BankBeanQpdOutRd> downloadNowDayBankData(String acctNo, String beginTime, String endTime);

	List<BankBeanQaccbalOutRd> queryAcctBal(String acctNo);
}