package com.app.service;

import java.util.Map;

/**
 * 银行数据接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface BankDataService {

	/**
	 * 从银行下载历史交易数据
	 * 
	 * @param result
	 * @param acctNo
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	boolean downloadHistDayBankData(Map<String, Object> result, String acctNo, String beginDate, String endDate);

	/**
	 * 从银行下载当日交易数据
	 * 
	 * @param result
	 * @param acctNo
	 * @param beginTime
	 * @param endTime
	 * @return
	 */
	boolean downloadNowDayBankData(Map<String, Object> result, String acctNo, String beginTime, String endTime);

	/**
	 * 从银行下载账户余额
	 * 
	 * @param result
	 * @param acctNo
	 * @return
	 */
	boolean downloadAcctBal(Map<String, Object> result, String acctNo);
}