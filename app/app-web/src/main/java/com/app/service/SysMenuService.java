package com.app.service;

import java.util.List;

import com.app.entity.SysMenu;

/**
 * 菜单接口
 * @author wangtw
 *
 */
public interface SysMenuService {

	/**
	 * 通过父菜单id和用户id查询菜单
	 * @param parentId
	 * @param userId
	 * @return
	 */
    List<SysMenu> selectListByParentIdAndUserId(Long parentId,Long userId);

}