package com.app.service;

import java.util.HashMap;
import java.util.List;

import com.app.bean.PageResultBean;
import com.app.dto.AcctBalanceDTO;
import com.app.dto.AcctInOutDTO;
import com.app.entity.AcctQueryHist;
import com.app.entity.AcctTranHist;

/**
 * 账户交易接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface AcctDataService {
	/**
	 * 导入指定账户余额
	 * 
	 * @param acctNo
	 */
	void loadAcctBalanceHist(String acctNo,String downType);

	/**
	 * 导入指定账户在一段时间内的交易流水
	 * 
	 * @param acctNo
	 * @param beginDate
	 * @param endDate
	 */
	void loadAcctTranHist(String acctNo, String beginDate, String endDate,String downType);

	/**
	 * 分页查询账户余额信息
	 * 
	 * @return
	 */
	PageResultBean<AcctBalanceDTO> queryAcctBalanceListPage();

	/**
	 * 不分页查询账户余额信息
	 * 
	 * @return
	 */
	List<AcctBalanceDTO> queryAcctBalanceList();

	/**
	 * 分页查询账户收支信息
	 * 
	 * @param queryMap
	 * @return
	 */
	PageResultBean<AcctInOutDTO> queryAcctInOutListPage(HashMap<String, Object> queryMap);

	/**
	 * 不分页查询账户收支信息
	 * 
	 * @param queryMap
	 * @return
	 */
	List<AcctInOutDTO> queryAcctInOutList(HashMap<String, Object> queryMap);

	/**
	 * 分页查询账户交易流水
	 * 
	 * @param queryMap
	 * @return
	 */
	PageResultBean<AcctTranHist> queryAcctTranHistListPage(HashMap<String, Object> queryMap);

	/**
	 * 不分页查询账户交易流水
	 * 
	 * @param queryMap
	 * @return
	 */
	List<AcctTranHist> queryAcctTranHistList(HashMap<String, Object> queryMap);

	/**
	 * 通过id查询账户交易流水详情
	 * 
	 * @param id
	 * @return
	 */
	AcctTranHist queryAcctTranHistById(Long id);

	/**
	 * 分页查询账户数据下载记录
	 * 
	 * @param queryMap
	 * @return
	 */
	PageResultBean<AcctQueryHist> queryAcctQueryHistListPage(HashMap<String, Object> queryMap);
}