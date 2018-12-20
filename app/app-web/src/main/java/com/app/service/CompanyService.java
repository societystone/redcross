package com.app.service;

import com.app.entity.Company;

/**
 * 单位接口 . <br>
 * 
 * @author wangtw <br>
 */
public interface CompanyService {

	/**
	 * 通过单位编码查询单位,多个单位取年度最大的
	 * 
	 * @param code
	 * @return
	 */
	Company selectCompanyByCode(String code);

}