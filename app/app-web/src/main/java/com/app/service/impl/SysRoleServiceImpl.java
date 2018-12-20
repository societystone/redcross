package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bean.PageResultBean;
import com.app.dao.remote.SysRoleDAO;
import com.app.entity.SysRole;
import com.app.service.SysPermissionService;
import com.app.service.SysRoleService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 角色接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class SysRoleServiceImpl implements SysRoleService {

	/**
	 * 注入角色dao
	 */
	@Autowired
	private SysRoleDAO sysRoleDAO;
	@Autowired
	private SysPermissionService sysPermissionService;

	@Override
	public SysRole selectSysRoleById(Long id) {
		// TODO Auto-generated method stub
		ExceptionUtil.throwEmptyCheckException(id, "角色ID为空");
		return (SysRole) sysRoleDAO.selectByPrimaryKey(id);
	}

	@Override
	public PageResultBean<SysRole> selectSysRoleByPage(SysRole sysRole) {
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<SysRole> pages = new PageResultBean<SysRole>(selectSysRoleList(sysRole));
		List<SysRole> sysRoles = pages.getRows();
		if (Emptys.isNotEmpty(sysRoles)) {
			List<SysRole> srs = new ArrayList<SysRole>();
			for (SysRole sr : sysRoles) {
				sr.setPermissions(sysPermissionService.selectPermissionByRoleId(sr.getId()));
				srs.add(sr);
			}
			pages.setRows(srs);
		}
		return pages;
	}

	@Override
	public List<SysRole> selectSysRoleList(SysRole sysRole) {
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("code", sysRole.getCode());
		queryMap.put("name", sysRole.getName());
		return sysRoleDAO.selectList(queryMap);
	}

	@Override
	public List<SysRole> selectRoleByUserId(Long userId) {
		// TODO Auto-generated method stub
		return sysRoleDAO.selectRoleByUserId(userId);
	}

}