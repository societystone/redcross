package com.app.service;

import com.app.bean.PageResultBean;
import com.app.dto.SysUserDTO;
import com.app.entity.SysUserEntity;

/**
 * 系统用户接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysUserService {

    /**
     * 插入SysUser到数据库
     * 
     * @param sysUserEntity
     * @return
     */
    Long insertSysUser(SysUserEntity sysUserEntity);

    /**
     * 通过SysUser的id更新SysUser中的数据
     * 
     * @param sysUserEntity
     * @return
     */
    Long updateSysUserById(SysUserEntity sysUserEntity);

    /**
     * 通过SysUser的id删除SysUser
     * 
     * @param id
     * @return
     */
    Boolean deleteSysUserById(Long id);

    /**
     * 通过username和id查询用户名是否存在
     * 
     * @param sysUserEntity
     * @return
     */
    Integer selectUsername(SysUserEntity sysUserEntity);

    /**
     * 通过SysUser的id获得SysUser对象
     * 
     * @param id
     * @return
     */
    SysUserEntity selectSysUserById(Long id);

    /**
     * 分页查询SysUser
     * 
     * @param sysUserDTO
     * @return
     */
    PageResultBean<SysUserEntity> selectSysUserByPage(SysUserDTO sysUserDTO);

}