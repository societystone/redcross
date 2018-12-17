package com.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.entity.SysMenu;

/**
 * 系统菜单dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysMenuDAO {

    /**
     * 通过父菜单id和用户id查询菜单
     * @param parentId
     * @param userId
     * @return
     */
    List<SysMenu> selectListByParentIdAndUserId(@Param("parentId")Long parentId,@Param("userId")Long userId);
}