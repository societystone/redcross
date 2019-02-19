package com.app.service;

import java.util.Date;
import java.util.HashMap;

import com.app.bean.PageResultBean;
import com.app.entity.Account;
import com.app.entity.AcctTranHist;

/**
 * 账户交易流水接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface AcctTranHistService {
	/**
	 * 导入指定账户在一段时间内的交易流水
	 * 
	 * @param account
	 * @param beginDate
	 * @param endDate
	 * @param taskType  任务类型1-自动任务2-人工查询
	 */
	void loadAcctTranHist(Account account, Date beginDate, Date endDate, String taskType);

	PageResultBean<AcctTranHist> SelectListByPage(HashMap<String, Object> queryMap);

	AcctTranHist selectById(Long id);
	
	
	
}