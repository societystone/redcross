package com.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bean.PageResultBean;
import com.app.dao.remote.SysUserDAO;
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

}