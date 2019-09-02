package com.app.dao.local;

import java.util.HashMap;
import java.util.List;

import com.app.dao.BaseDAO;
import com.app.entity.AcctTranHist;

/**
 * 账户交易流水dao . <br>
 * 
 * @author wangtw <br>
 */
public interface AcctTranHistDAO extends BaseDAO<AcctTranHist, Long> {

	/**
	 * 删除数据
	 * 
	 * @param map
	 * @return
	 */
	Long delete(HashMap<String, Object> map);

	/**
	 * 通过日期查询流水集合
	 * 
	 * @param map
	 * @return
	 */
	List<AcctTranHist> selectListByDate(HashMap<String, Object> map);

	AcctTranHist selectSumAmount(HashMap<String, Object> map);

	List<AcctTranHist> selectSumAmountByDate(HashMap<String, Object> map);

}