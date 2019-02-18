package com.app.service;

import java.util.Date;

import com.app.entity.AcctInfo;

/**
 * 账户交易流水接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface AcctTranHistService {
	/**
	 * 导入指定账户在一段时间内的交易流水
	 * 
	 * @param acctInfo
	 * @param beginDate
	 * @param endDate
	 * @param taskType  任务类型1-自动任务2-人工查询
	 */
	void loadAcctTranHist(AcctInfo acctInfo, Date beginDate, Date endDate, String taskType);
}