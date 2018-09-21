package com.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.dao.SysUserDAO;
import com.app.dto.SysUserDTO;
import com.app.entity.SysUserEntity;
import com.app.exception.CheckException;
import com.app.service.SysUserService;
import com.app.util.CheckUtils;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 用户接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class SysUserServiceImpl implements SysUserService {

    /**
     * 注入系统用户dao
     */
    @Autowired
    private SysUserDAO sysUserDAO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insertSysUser(SysUserEntity sysUserEntity) {
        int check = selectUsername(sysUserEntity);
        if (check > 0) {
            throw new CheckException("用户名已存在");
        }
        sysUserDAO.insertSysUser(sysUserEntity);
        return sysUserEntity.getUserId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long updateSysUserById(SysUserEntity sysUserEntity) {
        CheckUtils.notNull(sysUserEntity.getUserId(), "id不能为空");
        // 数据库是否已有这个用户名
        int check = selectUsername(sysUserEntity);
        if (check > 0) {
            throw new CheckException("用户名已存在");
        }
        sysUserDAO.updateSysUserById(sysUserEntity);
        return sysUserEntity.getUserId();
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean deleteSysUserById(Long id) {
        boolean isDelete = false;
        long result = sysUserDAO.deleteSysUserById(id);
        if (result > 0) {
            isDelete = true;
        }
        return isDelete;
    }

    @Override
    public Integer selectUsername(SysUserEntity sysUserEntity) {
        String username = sysUserEntity.getUsername();
        CheckUtils.notEmpty(username, "用户名不能为空");
        return sysUserDAO.selectUsername(sysUserEntity);
    }

    @Override
    public SysUserEntity selectSysUserById(Long id) {
        return sysUserDAO.selectSysUserById(id);
    }

    @Override
    public PageResultBean<SysUserEntity> selectSysUserByPage(SysUserDTO sysUserDTO) {
        PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
        return new PageResultBean<SysUserEntity>(sysUserDAO.selectSysUser(sysUserDTO));
    }

}