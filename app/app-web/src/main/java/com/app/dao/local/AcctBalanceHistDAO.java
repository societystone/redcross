package com.app.dao.local;

import java.util.HashMap;
import java.util.List;

import com.app.dao.BaseDAO;
import com.app.entity.AcctBalanceHist;

/**
 * 账户余额信息dao . <br>
 * 
 * @author wangtw <br>
 */
public interface AcctBalanceHistDAO extends BaseDAO<AcctBalanceHist, Long> {

	List<AcctBalanceHist> selectBalanceHist(HashMap<String, Object> map);
}