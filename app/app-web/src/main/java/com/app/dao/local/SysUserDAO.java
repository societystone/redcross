package com.app.dao.local;

import java.util.HashMap;

import com.app.dao.BaseDAO;
import com.app.entity.SysUser;

/**
 * 系统用户dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysUserDAO extends BaseDAO<SysUser, Long> {

	/**
	 * 通过username和id查询用户
	 * 
	 * @param sysUser
	 * @return
	 */
	SysUser selectUsername(HashMap<String, Object> map);

}