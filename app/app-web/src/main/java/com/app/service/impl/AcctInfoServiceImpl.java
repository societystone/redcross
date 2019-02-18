package com.app.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.local.AcctInfoDAO;
import com.app.entity.AcctInfo;
import com.app.service.AcctInfoService;

/**
 * 账户接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class AcctInfoServiceImpl implements AcctInfoService {

	/**
	 * 注入dao
	 */
	@Autowired
	private AcctInfoDAO acctInfoDAO;

	@Override
	public List<AcctInfo> getAllValidAcct() {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("status", 1);
		return acctInfoDAO.selectList(queryMap);
	}
}