package com.app.dao.local;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.DataMoveTemplateDetail;

/**
 * 数据迁移模板详情dao . <br>
 * 
 * @author wangtw <br>
 */
public interface DataMoveTemplateDetailDAO extends BaseDAO<DataMoveTemplateDetail, Long> {
	/**
	 * 通过模板ID删除
	 * 
	 * @param templateId
	 * @return
	 */
	int deleteByTemplateId(@Param("templateId") Long templateId);

}