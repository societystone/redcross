package com.app.service;

import java.util.List;

import com.app.entity.SysRefDef;

/**
 * 码值接口
 * @author wangtw
 *
 */
public interface SysRefDefService {

	/**
	 * 获取码值数据集合
	 * @param sysRefDef
	 * @return
	 */
    List<SysRefDef> selectList(SysRefDef sysRefDef);

    /**
     * 查询关联数据集合
     * @param relationType
     * @param mainPrimaryId
     * @param refType
     * @return
     */
    List<SysRefDef> selectListByRelation(String relationType,Long mainPrimaryId,String refType);
}