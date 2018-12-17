package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.entity.SysRole;
import com.app.service.SysRoleService;

/**
 * 系统角色controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysRoleController {

    /**
     * 注入角色接口
     */
    @Autowired
    private SysRoleService sysRoleService;

    /**
     * 插入角色到数据库
     * 
     * @param sysRole
     * @return
     */
    @PostMapping("/sys/role")
    public ResultBean<Long> insertSysRole(@RequestBody SysRole sysRole) {
        return new ResultBean<Long>(sysRoleService.insertSysRole(sysRole));
    }

    /**
     * 通过SysRole的id更新角色
     * 
     * @param SysRole
     * @return
     */
    @PutMapping("/sys/role")
    public ResultBean<Long> updateSysRoleById(@RequestBody SysRole sysRole) {
        return new ResultBean<Long>(sysRoleService.updateSysRoleById(sysRole));
    }

    /**
     * 通过SysRole的id删除角色
     * 
     * @param userId
     * @return
     */
    @DeleteMapping("/sys/role/{id}")
    public ResultBean<Boolean> deleteSysRoleById(@PathVariable("id") Long id) {
        return new ResultBean<Boolean>(sysRoleService.deleteSysRoleById(id));
    }

    /**
     * 通过SysRole的id获得角色
     * 
     * @param id
     * @return
     */
    @GetMapping("/sys/role/{id}")
    public ResultBean<SysRole> selectSysRoleById(@PathVariable("id") Long id) {
        return new ResultBean<SysRole>(sysRoleService.selectSysRoleById(id));
    }

    /**
     * 分页查询角色
     * @param sysRole
     * @return
     */
    @PostMapping("/sys/role/list")
    public ResultBean<PageResultBean<SysRole>> selectSysRoleByPage(@RequestBody SysRole sysRole) {
        return new ResultBean<PageResultBean<SysRole>>(sysRoleService.selectSysRoleByPage(sysRole));
    }

    /**
     * 不分页查询角色
     * @param sysRole
     * @return
     */
    @PostMapping("/sys/role/noPageList")
    public ResultBean<List<SysRole>> selectSysRoleList(@RequestBody SysRole sysRole) {
        return new ResultBean<List<SysRole>>(sysRoleService.selectSysRoleList(sysRole));
    }

    /**
     * 获取角色菜单权限
     * 
     * @param id
     * @return
     */
    @GetMapping("/sys/role/menu/{roleId}")
    public ResultBean<List<Long>> selectMenuIdByRoleId(@PathVariable("roleId") Long roleId) {
        return new ResultBean<List<Long>>(sysRoleService.selectMenuIdByRoleId(roleId));
    }

    /**
     * 编辑角色菜单权限
     * 
     * @param id
     * @return
     */
    @PostMapping("/sys/role/menu")
    public ResultBean<Long> updateMenuIdByRoleId(@RequestBody SysRole sysRole) {
    	return new ResultBean<Long>(sysRoleService.updateMenuIdByRoleId(sysRole.getId(),sysRole.getMenus()));
    }

}
