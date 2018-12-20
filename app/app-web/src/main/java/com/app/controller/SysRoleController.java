package com.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.aspect.annotation.Log;
import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.entity.SysRole;
import com.app.service.SysMenuService;
import com.app.service.SysPermissionService;
import com.app.service.SysRoleService;

/**
 * 系统角色controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysRoleController {

	/**
	 * 注入接口
	 */
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysPermissionService sysPermissionService;
	@Autowired
	private SysMenuService sysMenuService;

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
	 * 
	 * @param sysRole
	 * @return
	 */
	@PostMapping("/sys/role/list")
	public ResultBean<PageResultBean<SysRole>> selectSysRoleByPage(@RequestBody SysRole sysRole) {
		return new ResultBean<PageResultBean<SysRole>>(sysRoleService.selectSysRoleByPage(sysRole));
	}

	/**
	 * 不分页查询角色
	 * 
	 * @param sysRole
	 * @return
	 */
	@PostMapping("/sys/role/noPageList")
	public ResultBean<List<SysRole>> selectSysRoleList(@RequestBody SysRole sysRole) {
		return new ResultBean<List<SysRole>>(sysRoleService.selectSysRoleList(sysRole));
	}

	/**
	 * 获取角色权限
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/sys/role/permission/{roleId}")
	public ResultBean<SysRole> selectPermissionIdByRoleId(@PathVariable("roleId") Long roleId) {
		SysRole sr = new SysRole();
		sr.setPermissions(sysPermissionService.selectPermissionByRoleId(roleId));
		sr.setMenus(sysMenuService.selectMenuByRoleId(roleId));
		return new ResultBean<SysRole>(sr);
	}

	/**
	 * 编辑角色权限
	 * 
	 * @param id
	 * @return
	 */
	@Log("设置角色权限")
	@PostMapping("/sys/role/permission")
	public ResultBean<Long> updatePermissionByRoleId(@RequestBody SysRole sysRole) {
		return new ResultBean<Long>(sysPermissionService.updatePermissionByRoleId(sysRole));
	}

}
