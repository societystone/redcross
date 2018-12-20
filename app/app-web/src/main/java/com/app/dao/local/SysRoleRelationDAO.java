package com.app.dao.local;

import java.util.HashMap;

import com.app.dao.BaseDAO;
import com.app.entity.SysRoleRelation;

/**
 * 系统角色关联dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRoleRelationDAO extends BaseDAO<SysRoleRelation, Long> {
	/**
	 * 删除关联
	 * 
	 * @param map
	 * @return
	 */
	Long delete(HashMap<String, Object> map);
}