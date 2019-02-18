package com.app.dao.local;

import org.apache.ibatis.annotations.Mapper;

import com.app.entity.Account;

import java.util.HashMap;
import java.util.List;

@Mapper
public interface AccountDAO {

	 int addAcct(Account acct) ;
	 int deleteAcctById(Long id);
	 List<Account> selectList(Account acct);
	 int updateAcct(Account acct);
	 int stopOrStartAcct(Account acct);
	 List<Account> selectListByStatus(HashMap<String, Object> map);
}
