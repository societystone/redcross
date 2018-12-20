package com.app.service.impl;

import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.local.SysRefDefDAO;
import com.app.entity.SysRefDef;
import com.app.service.SysRefDefService;

/**
 * 码值接口实现类
 * 
 * @author wangtw
 *
 */
@Service
public class SysRefDefServiceImpl implements SysRefDefService {

	/**
	 * 注入dao
	 */
	@Autowired
	private SysRefDefDAO sysRefDefDAO;

	@Override
	public List<SysRefDef> selectList(SysRefDef sysRefDef) {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("refType", sysRefDef.getRefType());
		queryMap.put("refCode", sysRefDef.getRefCode());
		queryMap.put("status", sysRefDef.getStatus());
		return sysRefDefDAO.selectList(queryMap);
	}

}