package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.SysUser;

/**
 * 系统用户接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysUserService {

	/**
	 * 通过username和password查询用户
	 * 
	 * @param username
	 * @param password
	 * @return
	 */
	SysUser selectSysUser(String username, String password);

	/**
	 * 通过SysUser的id获得SysUser对象
	 * 
	 * @param id
	 * @return
	 */
	SysUser selectSysUserById(Long id);

	/**
	 * 分页查询用户
	 * 
	 * @param sysUser
	 * @return
	 */
	PageResultBean<SysUser> selectSysUserByPage(SysUser sysUser);

	/**
	 * 不分页查询用户
	 * 
	 * @param sysUser
	 * @return
	 */
	List<SysUser> selectSysUserList(SysUser sysUser);

	Long updateAcctPermissionByUserId(SysUser sysUser);

}