package com.app.service;

import java.util.List;

import com.app.entity.DataMoveTempletDetail;

/**
 * 数据迁移模板详情接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface DataMoveTempletDetailService {

	/**
	 * 通过模板id查询模板详情
	 * 
	 * @param templetId
	 * @return
	 */
	List<DataMoveTempletDetail> selectListByTempletId(Long templetId);

}