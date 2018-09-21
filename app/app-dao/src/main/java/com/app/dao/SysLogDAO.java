package com.app.dao;

import java.util.List;

import com.app.dto.SysLogDTO;
import com.app.entity.SysLogEntity;

/**
 * 系统日志dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysLogDAO {

    /**
     * 插入SysLog到数据库 <br>
     * 
     * 
     * @param sysLogEntity
     * @return
     */
    void insertSysLog(SysLogEntity sysLogEntity);

    /**
     * 获得SysLog数据集合
     * 
     * @param sysLogDTO
     * @return
     */
    List<SysLogEntity> selectSysLog(SysLogDTO sysLogDTO);
}