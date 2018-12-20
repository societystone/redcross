package com.app.dao.local;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.SysRefDef;

/**
 * 系统码值dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRefDefDAO extends BaseDAO<SysRefDef, Long> {

	/**
	 * 查询权限集合
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysRefDef> selectPermissionByRoleId(@Param("roleId") Long roleId);
}