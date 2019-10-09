package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bean.PageResultBean;
import com.app.common.Constants;
import com.app.dao.local.AccountDAO;
import com.app.dao.local.SysRoleRelationDAO;
import com.app.dto.AcctDTO;
import com.app.entity.Account;
import com.app.entity.SysRoleRelation;
import com.app.entity.SysUser;
import com.app.service.AccountService;
import com.app.util.Emptys;
import com.app.util.PageUtils;
import com.app.util.UserUtils;
import com.github.pagehelper.PageHelper;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDao;
	@Autowired
	private SysRoleRelationDAO sysRoleRelationDAO;

	private volatile Map<String, Account> accountCache = new HashMap<>();

	@Override
	public Map<String, Account> getAccountCache() {
		if (accountCache.isEmpty()) {
			reloadAccountCache();
		}
		return accountCache;
	}

	/**
	 * 获取指定账户名称
	 * 
	 * @param acctNo
	 * @return
	 */
	@Override
	public Account getAccount(String acctNo) {
		if (Emptys.isEmpty(accountCache)) {
			reloadAccountCache();
		}
		return accountCache.get(acctNo);
	}

	/**
	 * 重新加载账户缓存
	 */
	private void reloadAccountCache() {
		Map<String, Account> accountCache = new HashMap<>();
		List<Account> accounts = accountDao.selectListByStatus(null);
		if (Emptys.isNotEmpty(accounts)) {
			for (Account account : accounts) {
				accountCache.put(account.getAcctNo(), account);
			}
		}
		this.accountCache = accountCache;
	}

	@Transactional(value = "transactionManagerLocal", rollbackFor = { Exception.class })
	@Override
	public int addAcct(Account acct) {
		SysUser su = (SysUser) UserUtils.getUser();
		acct.setCreateDate(new Date());
		acct.setUserCode(su.getUsername());
		acct.setUserName(su.getRealName());
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		acct.setStatus("1");
		Long result = accountDao.insert(acct);
		if (result == 1) {
			SysRoleRelation sysRoleRelation = new SysRoleRelation();
			sysRoleRelation.setType(Constants.ROLE_RELATION_TYPE.USER_ACCT.getValue());
			SysUser loginUser = (SysUser) UserUtils.getUserIfLogin();
			sysRoleRelation.setRoleId(loginUser.getId());
			sysRoleRelation.setRelationId(acct.getId());
			sysRoleRelationDAO.insert(sysRoleRelation);
			reloadAccountCache();
		}
		return result.intValue();
	}

	@Override
	public int deleteAcctByid(Long id) {
		int result = accountDao.deleteAcctById(id);
		if (result == 1) {
			reloadAccountCache();
		}
		return result;
	}

	@Override
	public PageResultBean<Account> selectListByPage(Account acct) {
		acct.setUserId(((SysUser)UserUtils.getUserIfLogin()).getId());
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<Account> pages = new PageResultBean<Account>(selectList(acct));
		return pages;
	}

	@Override
	public List<Account> selectList(Account acct) {

		return accountDao.selectList(acct);
	}

	@Override
	public int updateAcct(Account acct) {
		SysUser su = (SysUser) UserUtils.getUser();
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		int result = accountDao.updateAcct(acct);
		if (result == 1) {
			reloadAccountCache();
		}
		return result;
	}

	@Override
	public int stopOrStartAcct(Account acct) {

		SysUser su = (SysUser) UserUtils.getUser();
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		int result = accountDao.stopOrStartAcct(acct);
		if (result == 1) {
			reloadAccountCache();
		}
		return result;
	}

	@Override
	public Account selectById(Long id) {
		return accountDao.selectByPrimaryKey(id);
	}

	@Override
	public List<Account> getAllValidAcct() {
		// TODO Auto-generated method stub
		List<Account> validAccounts = new ArrayList<>();
		Map<String, Account> accounts = getAccountCache();
		for (Account account : accounts.values()) {
			if ("1".equals(account.getStatus())) {
				validAccounts.add(account);
			}
		}
		return validAccounts;
	}

	@Override
	public boolean getAcctByAcctNo(String acctNo) {
		if (accountDao.getAcctByAcctNo(acctNo) != null) {
			return true;
		}
		return false;
	}

	@Override
	public Account getAccountByAcctNo(String acctNo) {
		// TODO Auto-generated method stub
		return accountDao.getAcctByAcctNo(acctNo);
	}

	@Override
	public List<AcctDTO> getAcctByUser(Long userId) {
		// TODO Auto-generated method stub
		List<AcctDTO> accts = new ArrayList<>();
		List<Account> accounts = accountDao.getAcctByUserId(userId);
		if (Emptys.isNotEmpty(accounts)) {
			for (Account account : accounts) {
				AcctDTO acct = new AcctDTO();
				acct.setAcctNo(account.getAcctNo());
				acct.setAcctName(account.getAcctName());
				accts.add(acct);
			}
		}
		return accts;
	}

	@Override
	public List<Account> getValidAcctLoginUser() {
		// TODO Auto-generated method stub
		Account acct = new Account();
		acct.setUserId(((SysUser)UserUtils.getUserIfLogin()).getId());
		return selectList(acct);
	}
}
