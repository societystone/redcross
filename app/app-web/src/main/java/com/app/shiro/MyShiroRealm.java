package com.app.shiro;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import com.app.common.Constants;
import com.app.entity.SysMenu;
import com.app.entity.SysRefDef;
import com.app.entity.SysUser;
import com.app.service.SysMenuService;
import com.app.service.SysRefDefService;
import com.app.service.SysUserService;
/**
 * 自定义Realm
 * @author wangtw
 *
 */
//实现AuthorizingRealm接口用户用户认证
public class MyShiroRealm extends AuthorizingRealm{

  //用于用户查询
  @Autowired
  private SysUserService sysUserService;
  
  @Autowired
  private SysRefDefService sysRefDefService;
  
  @Autowired
  private SysMenuService sysMenuService;

  //角色权限和对应权限添加
  @Override
  protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
      //获取登录用户名
      String username= (String) principalCollection.getPrimaryPrincipal();
      //查询用户名称
      SysUser su = new SysUser();
      su.setUsername(username);
      SysUser sysUser = sysUserService.selectUsername(su);
      //添加角色和权限
      SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
      List<SysRefDef> roleList = sysRefDefService.selectListByRelation(Constants.SYS_RELATION_TYPE.A.toString()
    		  , sysUser.getId(), Constants.SYS_REF_TYPE.A.toString());
      Set<String> roles=new HashSet<String>();
      if(roleList.size()>0){
    	  for(SysRefDef role:roleList){
    		  roles.add(role.getRefCode());
    	  }
      }
      simpleAuthorizationInfo.setRoles(roles);
	  //根据角色id查询所有资源
	  List<SysMenu> menuList=sysMenuService.selectListByParentIdAndUserId(null, sysUser.getId());
	  for(SysMenu menu:menuList){
		  simpleAuthorizationInfo.addStringPermission(menu.getName()); // 添加权限
	  }
      return simpleAuthorizationInfo;
  }

  //用户认证
  @Override
  protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
      //加这一步的目的是在Post请求的时候会先进认证，然后在到请求
      if (authenticationToken.getPrincipal() == null) {
          return null;
      }
      //获取用户信息
      String username = authenticationToken.getPrincipal().toString();
      SysUser su = new SysUser();
      su.setUsername(username);
      SysUser sysUser = sysUserService.selectUsername(su);
      if (sysUser == null) {
          //这里返回后会报出对应异常
          return null;
      } else {
          //这里验证authenticationToken和simpleAuthenticationInfo的信息
          SimpleAuthenticationInfo simpleAuthenticationInfo = new SimpleAuthenticationInfo(sysUser, sysUser.getPassword(),this.getClass().getName());
          return simpleAuthenticationInfo;
      }
  }
}