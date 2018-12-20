package com.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.dao.remote.CompanyDAO;
import com.app.entity.Company;
import com.app.service.CompanyService;
import com.app.util.Emptys;

/**
 * 单位接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class CompanyServiceImpl implements CompanyService {

	/**
	 * 注入dao
	 */
	@Autowired
	private CompanyDAO companyDAO;

	@Override
	public Company selectCompanyByCode(String code) {
		// TODO Auto-generated method stub
		Company company = null;
		List<Company> companys = companyDAO.selectCompanyByCode(code);
		if (Emptys.isNotEmpty(companys)) {
			for (Company c : companys) {
				if (Emptys.isNotEmpty(company)) {
					if (company.getKjnd().compareTo(c.getKjnd()) < 0) {
						company = c;
					}
				} else {
					company = c;
				}
			}
		}
		return company;
	}

}