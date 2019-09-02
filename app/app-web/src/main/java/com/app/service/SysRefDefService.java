package com.app.service;

import java.util.List;

import com.app.entity.SysRefDef;

/**
 * 码值接口
 * 
 * @author wangtw
 *
 */
public interface SysRefDefService {

	/**
	 * 获取码值数据集合
	 * 
	 * @param sysRefDef
	 * @return
	 */
	List<SysRefDef> selectList(SysRefDef sysRefDef);
	
	/**
	 * 获取码值描述
	 * @param refType
	 * @param refCode
	 * @return
	 */
	String getSysRefDefDesc(String refType, String refCode);

}