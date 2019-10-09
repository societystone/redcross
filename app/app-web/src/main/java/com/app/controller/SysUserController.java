package com.app.controller;

import java.util.List;

import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.aspect.annotation.Log;
import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.dto.SysUserDTO;
import com.app.entity.SysUser;
import com.app.service.SysPermissionService;
import com.app.service.SysUserService;
import com.app.util.ExceptionUtil;
import com.app.util.UserUtils;

/**
 * 系统用户controller . <br>
 * 
 * @author wangtw <br>
 */
@RestController
public class SysUserController {

	/**
	 * 注入接口
	 */
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysPermissionService sysPermissionService;

	@Autowired
	private HttpSession session;

	/**
	 * 系统登录
	 * 
	 * @param sysUser
	 * @return
	 */
	@PostMapping("/sys/login")
	public ResultBean<SysUser> login(@RequestBody SysUserDTO sysUser) {
		String username = sysUser.getUsername();
		String password = sysUser.getPassword();
		ExceptionUtil.throwEmptyCheckException(username, "用户名不能为空");
		ExceptionUtil.throwEmptyCheckException(password, "密码不能为空");
//		UsernamePasswordToken token = new UsernamePasswordToken(username, MD5Utils.MD5(password));
		UsernamePasswordToken token = new UsernamePasswordToken(username, password);
		try {
			SecurityUtils.getSubject().login(token); // 登录认证
		} catch (Exception e) {
			ExceptionUtil.throwCheckException("用户名或者密码错误");
		}
		SysUser su = (SysUser) SecurityUtils.getSubject().getPrincipal();
		su.setPermissions(sysPermissionService.selectPermissionByUserId(su.getId()));
		session.setAttribute(UserUtils.KEY_USER, su);
		return new ResultBean<SysUser>(su);
	}

	/**
	 * 系统退出登录
	 * 
	 * @param sysUser
	 * @return
	 */
	@PostMapping("/sys/logout")
	public ResultBean<Boolean> logout() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 通过id获得用户
	 * 
	 * @param id
	 * @return
	 */
	@GetMapping("/sys/user/{id}")
	public ResultBean<SysUser> selectSysUserById(@PathVariable("id") Long id) {
		return new ResultBean<SysUser>(sysUserService.selectSysUserById(id));
	}

	/**
	 * 分页查询用户
	 * 
	 * @param sysUserDTO
	 * @return
	 */
	@PostMapping("/sys/user/list")
	public ResultBean<PageResultBean<SysUser>> selectSysUserByPage(@RequestBody SysUser sysUser) {
		return new ResultBean<PageResultBean<SysUser>>(sysUserService.selectSysUserByPage(sysUser));
	}

	/**
	 * 不分页查询用户
	 * 
	 * @param sysUserDTO
	 * @return
	 */
	@PostMapping("/sys/user/noPageList")
	public ResultBean<List<SysUser>> selectSysUserList(@RequestBody SysUser sysUser) {
		return new ResultBean<List<SysUser>>(sysUserService.selectSysUserList(sysUser));
	}

	/**
	 * 编辑用户账户权限
	 * 
	 * @param id
	 * @return
	 */
	@Log("设置用户账户权限")
	@PostMapping("/sys/user/acct")
	public ResultBean<Long> updatePermissionByRoleId(@RequestBody SysUser sysUser) {
		return new ResultBean<Long>(sysUserService.updateAcctPermissionByUserId(sysUser));
	}

}
