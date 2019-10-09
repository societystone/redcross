package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.local.SysRoleRelationDAO;
import com.app.dao.remote.SysUserDAO;
import com.app.entity.Account;
import com.app.entity.SysRoleRelation;
import com.app.entity.SysUser;
import com.app.service.CompanyService;
import com.app.service.SysRoleService;
import com.app.service.SysUserService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 用户接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class SysUserServiceImpl implements SysUserService {

	/**
	 * 注入系统用户dao
	 */
	@Autowired
	private SysUserDAO sysUserDAO;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private CompanyService companyService;
	@Autowired
	private SysRoleRelationDAO sysRoleRelationDAO;

	@Override
	public SysUser selectSysUser(String username, String password) {
		ExceptionUtil.throwEmptyCheckException(username, "用户名不能为空");
		ExceptionUtil.throwEmptyCheckException(password, "密码不能为空");
		SysUser su = sysUserDAO.selectByUsername(username, password);
		if (Emptys.isNotEmpty(su)) {
			return su;
		} else {
			List<SysUser> sus = sysUserDAO.selectByName(username, password);
			if (Emptys.isNotEmpty(sus)) {
				return sus.get(0);
			} else {
				ExceptionUtil.throwCheckException("用户名或者密码错误");
			}
		}
		return null;
	}

	@Override
	public SysUser selectSysUserById(Long id) {
		SysUser su = sysUserDAO.selectByPrimaryKey(id);
		su.setCompany(companyService.selectCompanyByCode(su.getCompanyCode()));
		su.setRoles(sysRoleService.selectRoleByUserId(su.getId()));
		return su;
	}

	@Override
	public PageResultBean<SysUser> selectSysUserByPage(SysUser sysUser) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<SysUser> pages = new PageResultBean<SysUser>(selectSysUserList(sysUser));
		List<SysUser> sysUsers = pages.getRows();
		if (Emptys.isNotEmpty(sysUsers)) {
			List<SysUser> sus = new ArrayList<SysUser>();
			for (SysUser su : sysUsers) {
				su.setCompany(companyService.selectCompanyByCode(su.getCompanyCode()));
				su.setRoles(sysRoleService.selectRoleByUserId(su.getId()));
				sus.add(su);
			}
			pages.setRows(sus);
		}
		return pages;
	}

	@Override
	public List<SysUser> selectSysUserList(SysUser sysUser) {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("username", sysUser.getUsername());
		return sysUserDAO.selectList(queryMap);
	}

	@Override
	public Long updateAcctPermissionByUserId(SysUser sysUser) {
		// TODO Auto-generated method stub
		Long userId = sysUser.getId();
		ExceptionUtil.throwEmptyCheckException(userId, "用户ID为空");
		List<Account> accts = sysUser.getAccts();
		// 更新角色菜单
		String userRelationType = Constants.ROLE_RELATION_TYPE.USER_ACCT.getValue();
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("type", userRelationType);
		queryMap.put("roleId", userId);
		sysRoleRelationDAO.delete(queryMap);
		SysRoleRelation sysRoleRelation = new SysRoleRelation();
		sysRoleRelation.setType(userRelationType);
		sysRoleRelation.setRoleId(userId);
		if (Emptys.isNotEmpty(accts)) {
			for (Account acct : accts) {
				sysRoleRelation.setRelationId(acct.getId());
				sysRoleRelationDAO.insert(sysRoleRelation);
			}
		}
		return Long.valueOf(1);
	}

}