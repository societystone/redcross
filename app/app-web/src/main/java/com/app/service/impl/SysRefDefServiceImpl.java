package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.SysRefDefDAO;
import com.app.entity.SysRefDef;
import com.app.service.SysRefDefService;

/**
 * 码值接口实现类
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
		return sysRefDefDAO.selectList(sysRefDef);
	}

	@Override
	public List<SysRefDef> selectListByRelation(String relationType, Long mainPrimaryId, String refType) {
		// TODO Auto-generated method stub
		return sysRefDefDAO.selectListByRelation(relationType, mainPrimaryId, refType);
	}

}