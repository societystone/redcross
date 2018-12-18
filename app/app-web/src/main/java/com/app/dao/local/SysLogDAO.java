package com.app.dao.local;

import java.util.HashMap;
import java.util.List;

import com.app.dao.BaseDAO;
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
	 * @param map
	 * @return
	 */
	List<SysLog> selectAll(HashMap<String, Object> map);
}