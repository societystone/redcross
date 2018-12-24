package com.app.service;

import java.util.List;

import com.app.dto.RemoteTableDTO;

/**
 * 远程库接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface RemoteDataService {

	/**
	 * 根据数据库表名查询字段名列表
	 * 
	 * @param tableName
	 * @return
	 */
	List<RemoteTableDTO> selectColumnsByTableName(String tableName);

}