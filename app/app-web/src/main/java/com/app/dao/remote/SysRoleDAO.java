package com.app.dao.remote;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.SysRole;

/**
 * 系统角色dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRoleDAO extends BaseDAO<SysRole, Long> {

	/**
	 * 查询角色集合
	 * 
	 * @param userId
	 * @return
	 */
	List<SysRole> selectRoleByUserId(@Param("userId") Long userId);
}