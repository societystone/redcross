package com.app.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.bean.PageResultBean;
import com.app.dao.local.AccountDAO;
import com.app.entity.Account;
import com.app.entity.SysUser;
import com.app.service.AccountService;
import com.app.util.PageUtils;
import com.app.util.UserUtils;
import com.github.pagehelper.PageHelper;

@Service
public class AccountServiceImpl implements AccountService {

	@Autowired
	private AccountDAO accountDao;

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
		return accountDao.addAcct(acct);
	}

	@Override
	public int deleteAcctByid(Long id) {

		return accountDao.deleteAcctById(id);
	}

	@Override
	public PageResultBean<Account> SelectListByPage(Account acct) {

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
		return accountDao.updateAcct(acct);
	}

	@Override
	public int stopOrStartAcct(Account acct) {

		SysUser su = (SysUser) UserUtils.getUser();
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		return accountDao.stopOrStartAcct(acct);
	}

	@Override
	public Account selectById(Long id) {
		Account acct = new Account();
		acct.setId(id);
		return accountDao.selectList(acct).get(0);
	}

	@Override
	public List<Account> getAllValidAcct() {
		// TODO Auto-generated method stub
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("status", 1);
		return accountDao.selectListByStatus(queryMap);
	}

	@Override
	public boolean getAcctByAcctNo(String acctNo) {
		if(accountDao.getAcctByAcctNo(acctNo)  != null) {
			return true;
		}
		return false;
	}
}
