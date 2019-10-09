package com.app.service;

import java.util.List;
import java.util.Map;

import com.app.bean.PageResultBean;
import com.app.dto.AcctDTO;
import com.app.entity.Account;

public interface AccountService {

	public Map<String, Account> getAccountCache();

	/**
	 * 获取指定账户信息
	 * 
	 * @param acctNo
	 * @return
	 */
	public Account getAccount(String acctNo);

	int addAcct(Account acct) throws Exception;

	int deleteAcctByid(Long id);

	List<Account> selectList(Account acct);

	int updateAcct(Account acct);

	int stopOrStartAcct(Account acct);

	PageResultBean<Account> selectListByPage(Account acct);

	Account selectById(Long id);

	/**
	 * 获取所有有效账户列表
	 * 
	 * @return
	 */
	public List<Account> getAllValidAcct();

	boolean getAcctByAcctNo(String acctNo);

	Account getAccountByAcctNo(String acctNo);

	public List<AcctDTO> getAcctByUser(Long userId);

	List<Account> getValidAcctLoginUser();

}
