package com.app.dao.remote;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 远程数据dao . <br>
 * 
 * @author wangtw <br>
 */
public interface RemoteDataDAO {

	/**
	 * 插入远程数据
	 * 
	 * @param map
	 * @return
	 */
	int insert(@Param("insertColumns") List<String> insertColumns, @Param("insertValues") List<Object> insertValues);

	/**
	 * 批量插入远程数据
	 * 
	 * @param insertColumns
	 * @param insertValues
	 */
	int insertBatch(@Param("insertColumns") List<String> insertColumns,
			@Param("insertValues") List<List<Object>> insertValues);

}