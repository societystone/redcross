package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.SysRole;

/**
 * 系统角色接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysRoleService {

    /**
     * 插入角色到数据库
     * 
     * @param SysRole
     * @return
     */
    Long insertSysRole(SysRole SysRole);

    /**
     * 通过id更新角色
     * 
     * @param SysRole
     * @return
     */
    Long updateSysRoleById(SysRole SysRole);

    /**
     * 通过id删除角色
     * 
     * @param id
     * @return
     */
    Boolean deleteSysRoleById(Long id);

    /**
     * 通过id获得角色
     * 
     * @param id
     * @return
     */
    SysRole selectSysRoleById(Long id);

    /**
     * 分页查询角色
     * 
     * @param SysRole
     * @return
     */
    PageResultBean<SysRole> selectSysRoleByPage(SysRole SysRole);
    
    /**
     * 不分页查询角色
     * @param sysRole
     * @return
     */
    public List<SysRole> selectSysRoleList(SysRole sysRole);
    
    /**
     * 通过角色id查询菜单id
     * @param roleId
     * @return
     */
    public List<Long> selectMenuIdByRoleId(Long roleId);
    
    /**
     * 更新角色菜单权限
     * @param roleId
     * @param menuIds
     * @return
     */
    Long updateMenuIdByRoleId(Long roleId,List<Long> menuIds);

}