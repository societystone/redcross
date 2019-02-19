package com.app.dao.local;

import java.util.HashMap;

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

}