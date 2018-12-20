package com.app.service;

import java.util.List;
import java.util.Set;

import com.app.entity.SysRefDef;
import com.app.entity.SysRole;

/**
 * 权限接口
 * 
 * @author wangtw
 *
 */
public interface SysPermissionService {
	/**
	 * 通过角色id查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	public List<SysRefDef> selectPermissionByRoleId(Long roleId);

	/**
	 * 更新角色菜单权限和数据权限
	 * 
	 * @param sysRole
	 * @return
	 */
	Long updatePermissionByRoleId(SysRole sysRole);

	/**
	 * 通过用户id查询权限
	 * 
	 * @param roleId
	 * @return
	 */
	public Set<String> selectPermissionByUserId(Long userId);

}