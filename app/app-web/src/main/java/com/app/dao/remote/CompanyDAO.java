package com.app.dao.remote;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.Company;

/**
 * 单位dao . <br>
 * 
 * @author wangtw <br>
 */
public interface CompanyDAO extends BaseDAO<Company, Long> {

	/**
	 * 通过单位编码查询单位
	 * 
	 * @param code
	 * @return
	 */
	List<Company> selectCompanyByCode(@Param("code") String code);

}