package com.app.service;

import java.util.List;

import com.app.bean.PageResultBean;
import com.app.entity.Account;
import com.app.entity.AcctInfo;

public interface AccountService {

	int  addAcct(Account acct);

	int deleteAcctByid(Long id);

	List<Account> selectList(Account acct);

	int updateAcct(Account acct);

	int stopOrStartAcct(Account acct);
	
	PageResultBean<Account> SelectListByPage(Account acct);

	Account selectById(Long id);
	
	/**
	 * 获取所有有效账户列表
	 * 
	 * @return
	 */
	public List<Account> getAllValidAcct();

}
