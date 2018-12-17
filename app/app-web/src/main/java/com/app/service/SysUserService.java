package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.dto.SysUserDTO;
import com.app.entity.SysUser;

/**
 * 系统用户接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysUserService {

    /**
     * 插入SysUser到数据库
     * 
     * @param sysUser
     * @return
     */
    Long insertSysUser(SysUser sysUser);

    /**
     * 通过SysUser的id更新SysUser中的数据
     * 
     * @param sysUser
     * @return
     */
    Long updateSysUserById(SysUser sysUser);

    /**
     * 通过SysUser的id删除SysUser
     * 
     * @param id
     * @return
     */
    Boolean deleteSysUserById(Long id);

    /**
     * 通过username和id查询用户
     * 
     * @param sysUser
     * @return
     */
    SysUser selectUsername(SysUser sysUser);

    /**
     * 通过SysUser的id获得SysUser对象
     * 
     * @param id
     * @return
     */
    SysUser selectSysUserById(Long id);
    
    /**
     * 分页查询用户
     * @param sysUser
     * @return
     */
    PageResultBean<SysUser> selectSysUserByPage(SysUser sysUser);

    /**
     * 不分页查询用户
     * @param sysUser
     * @return
     */
    List<SysUser> selectSysUserList(SysUser sysUser);

    /**
     * 检查用户密码
     * @param sysUser
     * @return
     */
    Boolean checkSysUserPwd(SysUser sysUser);

    /**
     * 修改用户密码
     * @param sysUserDTO
     * @return
     */
    Boolean modifySysUserPwd(SysUserDTO sysUserDTO);
}