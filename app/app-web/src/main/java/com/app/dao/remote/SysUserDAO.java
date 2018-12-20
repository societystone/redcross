package com.app.dao.remote;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.SysUser;

/**
 * 系统用户dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysUserDAO extends BaseDAO<SysUser, Long> {
	/**
	 * 通过username和password查询用户
	 * 
	 * @param username
	 * @return
	 */
	SysUser selectByUsername(@Param("username") String username, @Param("password") String password);

	/**
	 * 通过name和password查询用户
	 * 
	 * @param name
	 * @return
	 */
	List<SysUser> selectByName(@Param("name") String name, @Param("password") String password);

}