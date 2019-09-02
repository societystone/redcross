package com.app.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.local.SysRefDefDAO;
import com.app.entity.SysRefDef;
import com.app.service.SysRefDefService;
import com.app.util.Emptys;

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

	private volatile Map<String, Map<String, SysRefDef>> sysRefDefCache = new HashMap<>();// 数据字典缓存

	/**
	 * 重新加载数据字典缓存
	 */
	private void reloadSysRefDefCache() {
		Map<String, Map<String, SysRefDef>> sysRefDefCache = new HashMap<>();
		List<SysRefDef> sysRefDefs = selectList(null);
		if (Emptys.isNotEmpty(sysRefDefs)) {
			for (SysRefDef sysRefDef : sysRefDefs) {
				Map<String, SysRefDef> map = sysRefDefCache.get(sysRefDef.getRefType());
				if (Emptys.isEmpty(map)) {
					map = new HashMap<String, SysRefDef>();
					sysRefDefCache.put(sysRefDef.getRefType(), map);
				}
				map.put(sysRefDef.getRefCode(), sysRefDef);
			}
		}
		this.sysRefDefCache = sysRefDefCache;
	}

	@Override
	public List<SysRefDef> selectList(SysRefDef sysRefDef) {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("refType", sysRefDef.getRefType());
		queryMap.put("refCode", sysRefDef.getRefCode());
		queryMap.put("status", sysRefDef.getStatus());
		return sysRefDefDAO.selectList(queryMap);
	}

	@Override
	public String getSysRefDefDesc(String refType, String refCode) {
		// TODO Auto-generated method stub
		if (Emptys.isEmpty(sysRefDefCache)) {
			reloadSysRefDefCache();
		}
		Map<String, SysRefDef> map = sysRefDefCache.get(refType);
		if (Emptys.isEmpty(map)) {
			return "";
		}
		return map.get(refCode).getRefDesc();
	}

}