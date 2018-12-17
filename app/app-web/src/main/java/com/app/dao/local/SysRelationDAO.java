package com.app.dao.local;

import com.app.dao.BaseDAO;
import com.app.entity.SysRelation;

/**
 * 系统用户dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRelationDAO extends BaseDAO<SysRelation, Long> {
	/**
	 * 删除关联
	 * @param sysRelation
	 * @return
	 */
	Long delete(SysRelation sysRelation);
}