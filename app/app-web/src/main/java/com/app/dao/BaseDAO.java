package com.app.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

/**
 * 基础DAO
 * @author wangtw
 *
 */
public interface BaseDAO<T, PK> {
	/**
	 * 插入数据
	 * @param t
	 * @return
	 */
	PK insert(T t);
	/**
	 * 批量插入数据
	 * @param ts
	 * @return
	 */
	int  insertBatch(List<T> ts);
	
	/**
	 * 更新数据
	 * @param t
	 * @return
	 */
	Long update(T t);
	
	/**
	 * 通过主键删除数据
	 * @param id
	 * @return
	 */
	Long deleteByPrimaryKey(@Param("id") PK id);
	
	/**
	 * 通过主键查询数据
	 * @param id
	 * @return
	 */
	T selectByPrimaryKey(@Param("id") PK id);
	
	/**
	 * 查询数据集合
	 * @param t
	 * @return
	 */
	List<T> selectList(T t);
}
