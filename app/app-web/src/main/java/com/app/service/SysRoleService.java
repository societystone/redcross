package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.SysRole;

/**
 * 系统角色接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRoleService {

	/**
	 * 通过id获得角色
	 * 
	 * @param id
	 * @return
	 */
	SysRole selectSysRoleById(Long id);

	/**
	 * 分页查询角色
	 * 
	 * @param SysRole
	 * @return
	 */
	PageResultBean<SysRole> selectSysRoleByPage(SysRole SysRole);

	/**
	 * 不分页查询角色
	 * 
	 * @param sysRole
	 * @return
	 */
	public List<SysRole> selectSysRoleList(SysRole sysRole);

	/**
	 * 查询用户的角色
	 * 
	 * @param userId
	 * @return
	 */
	public List<SysRole> selectRoleByUserId(Long userId);

}