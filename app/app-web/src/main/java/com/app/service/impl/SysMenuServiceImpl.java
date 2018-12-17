package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.local.SysMenuDAO;
import com.app.entity.SysMenu;
import com.app.service.SysMenuService;

/**
 * 菜单接口实现类
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
	
	@Override
	public List<SysMenu> selectListByParentIdAndUserId(Long parentId,Long userId) {
		// TODO Auto-generated method stub
		return sysMenuDAO.selectListByParentIdAndUserId(parentId, userId);
	}

}