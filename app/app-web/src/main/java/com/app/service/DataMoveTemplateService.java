package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.DataMoveTemplate;

/**
 * 数据迁移模板接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface DataMoveTemplateService {

	/**
	 * 查询模板列表
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	List<DataMoveTemplate> selectTemplateList(DataMoveTemplate dataMoveTemplate);

	/**
	 * 通过模板id查询模板详情
	 * 
	 * @param id
	 * @return
	 */
	DataMoveTemplate selectTemplateDetail(Long id);

	/**
	 * 插入模板信息
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	Long insertTemplateInfo(DataMoveTemplate dataMoveTemplate);

	/**
	 * 更新模板信息
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	void updateTemplateInfo(DataMoveTemplate dataMoveTemplate);

	/**
	 * 删除模板信息
	 * 
	 * @param id
	 */
	void delteTemplateInfo(Long id);

	/**
	 * 启用模板
	 * 
	 * @param id
	 */
	void startUseTemplateInfo(Long id);

	/**
	 * 停用模板
	 * 
	 * @param id
	 */
	void stopUseTemplateInfo(Long id);

	/**
	 * 分页查询模板列表
	 * 
	 * @param dataMoveTemplate
	 * @return
	 */
	PageResultBean<DataMoveTemplate> selectTemplateListByPage(DataMoveTemplate dataMoveTemplate);

}