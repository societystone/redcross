package com.app.dao.local;

import java.util.List;

import com.app.dao.BaseDAO;
import com.app.dto.SysLogDTO;
import com.app.entity.SysLog;

/**
 * 系统日志dao . <br>
 * 
 * @author wangtw <br>
 */
public interface SysLogDAO extends BaseDAO<SysLog, Long> {

	/**
	 * 查询数据集合
	 * 
	 * @param sysLogDTO
	 * @return
	 */
	List<SysLog> selectAll(SysLogDTO sysLogDTO);
}