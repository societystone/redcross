package com.app.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 基础DAO
 * 
 * @author wangtw
 *
 */
public interface BaseDAO<T, PK> {
	/**
	 * 插入数据
	 * 
	 * @param t
	 * @return
	 */
	PK insert(T t);

	/**
	 * 批量插入数据
	 * 
	 * @param lists
	 * @return
	 */
	int insertBatch(@Param("lists") List<T> lists);

	/**
	 * 更新数据
	 * 
	 * @param t
	 * @return
	 */
	Long update(T t);

	/**
	 * 通过主键删除数据
	 * 
	 * @param id
	 * @return
	 */
	Long deleteByPrimaryKey(@Param("id") PK id);

	/**
	 * 通过主键查询数据
	 * 
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(@Param("id") PK id);

	/**
	 * 查询数据集合
	 * 
	 * @param map
	 * @return
	 */
	List<T> selectList(HashMap<String, Object> map);
}
