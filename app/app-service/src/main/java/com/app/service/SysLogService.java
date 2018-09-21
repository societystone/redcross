package com.app.service;

import com.app.bean.PageResultBean;
import com.app.dto.SysLogDTO;
import com.app.entity.SysLogEntity;

/**
 * 系统日志接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysLogService {

    /**
     * 插入SysLog到数据库 <br>
     * 
     * 
     * @param sysLogEntity
     * @return
     */
    Long insertSysLog(SysLogEntity sysLogEntity);

    /**
     * 获得SysLog数据集合
     * 
     * @param sysLogDTO
     * @return
     */
    PageResultBean<SysLogEntity> selectSysLogByPage(SysLogDTO sysLogDTO);

}