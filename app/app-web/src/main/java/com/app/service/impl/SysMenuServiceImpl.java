package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.local.SysMenuDAO;
import com.app.entity.SysMenu;
import com.app.entity.SysRole;
import com.app.service.SysMenuService;
import com.app.service.SysRoleService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;

/**
 * 菜单接口实现类
 * 
 * @author wangtw
 *
 */
@Service
public class SysMenuServiceImpl implements SysMenuService {

	/**
	 * 注入dao
	 */
	@Autowired
	private SysMenuDAO sysMenuDAO;
	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public List<SysMenu> selectAllSysMenu() {
		// TODO Auto-generated method stub
		return sysMenuDAO.selectList(null);
	}

	@Override
	public List<SysMenu> selectListByParentIdAndUserId(Long parentId, Long userId) {
		// TODO Auto-generated method stub
		List<Long> roles = null;
		if (Emptys.isNotEmpty(userId)) {
			List<SysRole> sysRoles = sysRoleService.selectRoleByUserId(userId);
			if (Emptys.isNotEmpty(sysRoles)) {
				roles = new ArrayList<Long>();
				for (SysRole sysRole : sysRoles) {
					roles.add(sysRole.getId());
				}
			}else {
				return null;
			}
		}
		return sysMenuDAO.selectListByParentIdAndRoleId(parentId, roles);
	}

	@Override
	public List<SysMenu> selectMenuByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(roleId, "角色ID为空");
		return sysMenuDAO.selectMenuByRoleId(roleId);
	}

}