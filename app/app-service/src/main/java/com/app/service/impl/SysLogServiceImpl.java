package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.dao.SysLogDAO;
import com.app.dto.SysLogDTO;
import com.app.entity.SysLogEntity;
import com.app.service.SysLogService;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 系统接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class SysLogServiceImpl implements SysLogService {

    /**
     * 注入dao
     */
    @Autowired
    private SysLogDAO sysLogDAO;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Long insertSysLog(SysLogEntity sysLogEntity) {
        sysLogDAO.insertSysLog(sysLogEntity);
        return sysLogEntity.getId();
    }

    @Override
    public PageResultBean<SysLogEntity> selectSysLogByPage(SysLogDTO sysLogDTO) {
        PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
        List<SysLogEntity> result = sysLogDAO.selectSysLog(sysLogDTO);
        return new PageResultBean<SysLogEntity>(result);
    }

}