package com.app.service.impl;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.common.Constants;
import com.app.dao.local.SysRefDefDAO;
import com.app.dao.local.SysRoleRelationDAO;
import com.app.entity.SysMenu;
import com.app.entity.SysRefDef;
import com.app.entity.SysRole;
import com.app.entity.SysRoleRelation;
import com.app.service.SysPermissionService;
import com.app.service.SysRoleService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;

/**
 * 权限接口实现类
 * 
 * @author wangtw
 *
 */
@Service
public class SysPermissionServiceImpl implements SysPermissionService {

	/**
	 * 注入dao
	 */
	@Autowired
	private SysRefDefDAO sysRefDefDAO;
	@Autowired
	private SysRoleRelationDAO sysRoleRelationDAO;
	@Autowired
	private SysRoleService sysRoleService;

	@Override
	public List<SysRefDef> selectPermissionByRoleId(Long roleId) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(roleId, "角色ID为空");
		return sysRefDefDAO.selectPermissionByRoleId(roleId);
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public Long updatePermissionByRoleId(SysRole sysRole) {
		// TODO Auto-generated method stub
		Long roleId = sysRole.getId();
		ExceptionUtil.throwEmptyCheckException(roleId, "角色ID为空");
		List<SysMenu> menus = sysRole.getMenus();
		List<SysRefDef> permissions = sysRole.getPermissions();
		// 更新角色菜单
		String roleRelationType = Constants.ROLE_RELATION_TYPE.ROLE_MENU.getValue();
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("type", roleRelationType);
		queryMap.put("roleId", roleId);
		sysRoleRelationDAO.delete(queryMap);
		SysRoleRelation sysRoleRelation = new SysRoleRelation();
		sysRoleRelation.setType(roleRelationType);
		sysRoleRelation.setRoleId(roleId);
		if (Emptys.isNotEmpty(menus)) {
			for (SysMenu menu : menus) {
				sysRoleRelation.setRelationId(menu.getId());
				sysRoleRelationDAO.insert(sysRoleRelation);
			}
		}
		// 更新角色权限
		roleRelationType = Constants.ROLE_RELATION_TYPE.ROLE_PERMISSION.getValue();
		queryMap.put("type", roleRelationType);
		queryMap.put("roleId", roleId);
		sysRoleRelationDAO.delete(queryMap);
		sysRoleRelation = new SysRoleRelation();
		sysRoleRelation.setType(roleRelationType);
		sysRoleRelation.setRoleId(roleId);
		if (Emptys.isNotEmpty(permissions)) {
			for (SysRefDef permission : permissions) {
				sysRoleRelation.setRelationId(permission.getId());
				sysRoleRelationDAO.insert(sysRoleRelation);
			}
		}
		return Long.valueOf(1);
	}

	@Override
	public Set<String> selectPermissionByUserId(Long userId) {
		// TODO Auto-generated method stub
		Set<String> permissions = null;
		List<SysRole> sysRoles = sysRoleService.selectRoleByUserId(userId);
		if (Emptys.isNotEmpty(sysRoles)) {
			permissions = new HashSet<String>();
			for (SysRole sysRole : sysRoles) {
				List<SysRefDef> permissionObjs = selectPermissionByRoleId(sysRole.getId());
				if (Emptys.isNotEmpty(permissionObjs)) {
					for (SysRefDef permission : permissionObjs) {
						permissions.add(permission.getRefCode());
					}
				}
			}
		}
		return permissions;
	}

}