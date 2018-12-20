package com.app.dao.local;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.SysMenu;

/**
 * 系统菜单dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysMenuDAO extends BaseDAO<SysMenu, Long> {

	/**
	 * 通过父菜单id和用户id查询菜单
	 * 
	 * @param parentId
	 * @param roles
	 * @return
	 */
	List<SysMenu> selectListByParentIdAndRoleId(@Param("parentId") Long parentId, @Param("roles") List<Long> roles);

	/**
	 * 查询菜单集合
	 * 
	 * @param roleId
	 * @return
	 */
	List<SysMenu> selectMenuByRoleId(@Param("roleId") Long roleId);
}