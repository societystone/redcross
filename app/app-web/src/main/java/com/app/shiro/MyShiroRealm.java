package com.app.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.entity.SysRefDef;
import com.app.entity.SysRole;
import com.app.entity.SysUser;
import com.app.service.SysPermissionService;
import com.app.service.SysRoleService;
import com.app.service.SysUserService;
import com.app.util.Emptys;

/**
 * 自定义Realm
 * 
 * @author wangtw
 *
 */
//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm {

	// 用于用户查询
	@Autowired
	private SysUserService sysUserService;
	@Autowired
	private SysRoleService sysRoleService;
	@Autowired
	private SysPermissionService sysPermissionService;

	// 角色权限和对应权限添加
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		// 获取登录用户名
		String username = (String) principalCollection.getPrimaryPrincipal();
		// 查询用户名称
		SysUser sysUser = sysUserService.selectSysUser(username, "");
		// 添加角色
		SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
		List<SysRole> sysRoles = sysRoleService.selectRoleByUserId(sysUser.getId());
		if (Emptys.isNotEmpty(sysRoles)) {
			Set<String> roles = new HashSet<String>();
			for (SysRole sysRole : sysRoles) {
				String code = sysRole.getCode();
				roles.add(Emptys.isNotEmpty(code) ? code : "~");
				List<SysRefDef> permissions = sysPermissionService.selectPermissionByRoleId(sysRole.getId());
				if (Emptys.isNotEmpty(permissions)) {
					for (SysRefDef permission : permissions) {
						simpleAuthorizationInfo.addStringPermission(permission.getRefCode()); // 添加权限
					}
				}
			}
			simpleAuthorizationInfo.setRoles(roles);
		}
		return simpleAuthorizationInfo;
	}

	// 用户认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken)
			throws AuthenticationException {
		// 加这一步的目的是在Post请求的时候会先进认证，然后在到请求
		if (authenticationToken.getPrincipal() == null) {
			return null;
		}
		// 获取用户信息
		String username = authenticationToken.getPrincipal().toString();
		UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) authenticationToken;
		String password = String.valueOf(usernamePasswordToken.getPassword());
		SysUser sysUser = sysUserService.selectSysUser(username, password);
		if (sysUser == null) {
			// 这里返回后会报出对应异常
			return null;
		} else {
			// 这里验证authenticationToken和simpleAuthenticationInfo的信息
			SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser,
					sysUser.getPassword(), this.getClass().getName());
			return simpleAuthenticationInfo;
		}
	}
}