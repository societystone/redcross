package com.app.dao.local;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.VoucherTemplateDetail;

/**
 * 凭证模板详情dao . <br>
 * 
 * @author wangtw <br>
 */
public interface VoucherTemplateDetailDAO extends BaseDAO<VoucherTemplateDetail, Long> {
	/**
	 * 通过模板ID删除
	 * 
	 * @param templateId
	 * @return
	 */
	int deleteByTemplateId(@Param("templateId") Long templateId);
}