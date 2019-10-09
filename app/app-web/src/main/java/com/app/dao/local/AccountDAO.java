package com.app.dao.local;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.app.dao.BaseDAO;
import com.app.entity.Account;

@Mapper
public interface AccountDAO extends BaseDAO<Account, Long> {

	int deleteAcctById(Long id);

	List<Account> selectList(Account acct);

	int updateAcct(Account acct);

	int stopOrStartAcct(Account acct);

	List<Account> selectListByStatus(HashMap<String, Object> map);

	Account getAcctByAcctNo(String acctNo);

	List<Account> getAcctByUserId(@Param("userId") Long userId);
}
