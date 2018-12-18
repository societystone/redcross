package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.local.SysRelationDAO;
import com.app.dao.local.SysUserDAO;
import com.app.dto.SysUserDTO;
import com.app.entity.SysRelation;
import com.app.entity.SysUser;
import com.app.service.SysUserService;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;
import com.app.util.MD5Utils;
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

	/**
	 * 注入系统关联dao
	 */
	@Autowired
	private SysRelationDAO sysRelationDAO;

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long insertSysUser(SysUser sysUser) {
		SysUser sue = selectUsername(sysUser);
		if (sue != null) {
			ExceptionUtil.throwCheckException("用户名已存在");
		}
		// 默认密码和用户名一样
		sysUser.setPassword(MD5Utils.MD5(sysUser.getUsername()));
		sysUser.setCreateDate(new Date());
		sysUser.setStatus(Constants.ENABLE_VALUE);// 启用
		sysUserDAO.insert(sysUser);
		Long userId = sysUser.getId();
		SysRelation sysRelation = new SysRelation();
		sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.A.toString());
		sysRelation.setMainPrimaryId(userId);
		for (Long roleId : sysUser.getRoles()) {
			sysRelation.setRelPrimaryId(roleId);
			sysRelationDAO.insert(sysRelation);
		}
		return userId;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Long updateSysUserById(SysUser sysUser) {
		ExceptionUtil.throwEmptyCheckException(sysUser.getId(), "id不能为空");
		// 数据库是否已有这个用户名
		SysUser sue = selectUsername(sysUser);
		if (sue != null) {
			ExceptionUtil.throwCheckException("用户名已存在");
		}
		sysUser.setModifyDate(new Date());
		sysUserDAO.update(sysUser);
		Long userId = sysUser.getId();
		SysRelation sysRelation = new SysRelation();
		sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.A.toString());
		sysRelation.setMainPrimaryId(userId);
		sysRelationDAO.delete(sysRelation);
		for (Long roleId : sysUser.getRoles()) {
			sysRelation.setRelPrimaryId(roleId);
			sysRelationDAO.insert(sysRelation);
		}
		return userId;
	}

	@Transactional(rollbackFor = Exception.class)
	@Override
	public Boolean deleteSysUserById(Long id) {
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setStatus(Constants.DISABLE_VALUE);// 禁用
		sysUser.setModifyDate(new Date());
		long result = sysUserDAO.update(sysUser);
		return result > 0 ? true : false;
	}

	@Override
	public SysUser selectUsername(SysUser sysUser) {
		String username = sysUser.getUsername();
		ExceptionUtil.throwEmptyCheckException(username, "用户名不能为空");
		return sysUserDAO.selectUsername(sysUser);
	}

	@Override
	public SysUser selectSysUserById(Long id) {
		SysUser su = sysUserDAO.selectByPrimaryKey(id);
		SysRelation sysRelation = new SysRelation();
		sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.A.toString());
		sysRelation.setMainPrimaryId(id);
		List<SysRelation> srs = sysRelationDAO.selectList(sysRelation);
		if (Emptys.isNotEmpty(srs)) {
			List<Long> roles = new ArrayList<Long>();
			for (SysRelation sr : srs) {
				roles.add(sr.getRelPrimaryId());
			}
			su.setRoles(roles);
		}
		return su;
	}

	@Override
	public PageResultBean<SysUser> selectSysUserByPage(SysUser sysUser) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<SysUser> pages = new PageResultBean<SysUser>(sysUserDAO.selectList(sysUser));
		List<SysUser> sysUsers = pages.getRows();
		if (Emptys.isNotEmpty(sysUsers)) {
			List<SysUser> sus = new ArrayList<SysUser>();
			for (SysUser su : sysUsers) {
				SysRelation sysRelation = new SysRelation();
				sysRelation.setRelationType(Constants.SYS_RELATION_TYPE.A.toString());
				sysRelation.setMainPrimaryId(su.getId());
				List<SysRelation> srs = sysRelationDAO.selectList(sysRelation);
				if (Emptys.isNotEmpty(srs)) {
					List<Long> roles = new ArrayList<Long>();
					for (SysRelation sr : srs) {
						roles.add(sr.getRelPrimaryId());
					}
					su.setRoles(roles);
				}
				sus.add(su);
			}
			pages.setRows(sus);
		}
		return pages;
	}

	@Override
	public List<SysUser> selectSysUserList(SysUser sysUser) {
		// TODO Auto-generated method stub
		return sysUserDAO.selectList(sysUser);
	}

	@Override
	public Boolean checkSysUserPwd(SysUser sysUser) {
		// TODO Auto-generated method stub
		Long id = sysUser.getId();
		String password = sysUser.getPassword();
		ExceptionUtil.throwEmptyCheckException(id, "id不能为空");
		ExceptionUtil.throwEmptyCheckException(password, "用户当前密码不能为空");
		SysUser su = sysUserDAO.selectByPrimaryKey(sysUser.getId());
		ExceptionUtil.throwEmptyCheckException(su, "用户不存在");
		if (!MD5Utils.MD5(password).equals(su.getPassword())) {
			ExceptionUtil.throwCheckException("用户当前密码不正确");
		}
		return true;
	}

	@Override
	public Boolean modifySysUserPwd(SysUserDTO sysUserDTO) {
		// TODO Auto-generated method stub
		Long id = sysUserDTO.getId();
		String oldPassword = sysUserDTO.getOldPassword();
		String password = sysUserDTO.getPassword();
		SysUser sysUser = new SysUser();
		sysUser.setId(id);
		sysUser.setPassword(oldPassword);
		checkSysUserPwd(sysUser);
		ExceptionUtil.throwEmptyCheckException(password, "用户新密码不能为空");
		if (password.equals(oldPassword)) {
			ExceptionUtil.throwCheckException("新密码不能和当前密码相同");
		}
		sysUser.setPassword(MD5Utils.MD5(password));
		sysUser.setModifyDate(new Date());
		long result = sysUserDAO.update(sysUser);
		return result > 0 ? true : false;
	}

}