package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.VoucherTemplate;

/**
 * 凭证模板接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface VoucherTemplateService {

	/**
	 * 查询模板列表
	 * 
	 * @param voucherTemplate
	 * @return
	 */
	List<VoucherTemplate> selectTemplateList(VoucherTemplate voucherTemplate);

	/**
	 * 通过模板id查询模板详情
	 * 
	 * @param id
	 * @return
	 */
	VoucherTemplate selectTemplateDetail(Long id);

	/**
	 * 插入模板信息
	 * 
	 * @param voucherTemplate
	 * @return
	 */
	Long insertTemplateInfo(VoucherTemplate voucherTemplate);

	/**
	 * 更新模板信息
	 * 
	 * @param voucherTemplate
	 * @return
	 */
	void updateTemplateInfo(VoucherTemplate voucherTemplate);

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
	 * @param voucherTemplate
	 * @return
	 */
	PageResultBean<VoucherTemplate> selectTemplateListByPage(VoucherTemplate voucherTemplate);

}