package com.app.service;

import com.app.bean.PageResultBean;
import com.app.entity.SysLog;

/**
 * 系统日志接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface SysLogService {

	/**
	 * 插入SysLog到数据库
	 * 
	 * @param sysLog
	 * @return
	 */
	Long insertSysLog(SysLog sysLog);

	/**
	 * 查询系统日志详情
	 * 
	 * @param id
	 * @return
	 */
	SysLog selectSysLogById(Long id);

	/**
	 * 分页查询日志
	 * 
	 * @param sysLog
	 * @return
	 */
	PageResultBean<SysLog> selectSysLogByPage(SysLog sysLog);
}