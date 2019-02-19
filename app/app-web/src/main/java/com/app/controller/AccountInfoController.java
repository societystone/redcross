package com.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.common.Constants;
import com.app.entity.Account;
import com.app.service.AccountService;
import com.app.util.ExceptionUtil;

@RestController
public class AccountInfoController {
	
	@Autowired
	private AccountService accountService;


	/**
	 *添加账户
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/add")
	@ResponseBody
	public ResultBean<Account> addAcct(@RequestBody Account acct) {
		/*
		 * 判断账号会否已存在
		 */
		if(accountService.getAcctByAcctNo(acct.getAcctNo())) {
			ExceptionUtil.throwCheckException("账号已存在");
		}
		try {
			accountService.addAcct(acct);
		} catch (Exception e) {
			ExceptionUtil.throwCheckException("账户添加失败");
			
		}
		
		return new ResultBean<Account>(null);
	}
	
	/**
	 * 删除账户  此方法暂时不用，采用更新的方式 假删除
	 * @param id
	 * @return
	 */
	@RequestMapping("acct/delete/{id}")
	@ResponseBody
	public ResultBean<Account> addAcct(@PathVariable("id") Long id) {
		int result = accountService.deleteAcctByid(id);
		if(result != Constants.RESULT_NUM1 ) {
			ExceptionUtil.throwCheckException("删除账户失败");
		}
		return new ResultBean<Account>(null);
		
	}
	
	/**
	 * 分页查询账户
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/selectlist")
	public ResultBean<PageResultBean<Account>> selectList(@RequestBody Account acct) {
	
		return new ResultBean<PageResultBean<Account>>(accountService.SelectListByPage(acct));
	}
	/**
	 * 按账户id查询账户信息
	 * @param id
	 * @return
	 */
	@RequestMapping("acct/select/{id}")
	@ResponseBody
	public ResultBean<Account> selectById(@PathVariable("id") Long id) {
	
		return new ResultBean<Account>(accountService.selectById(id));
	}
	/**
	 * 跟新账户信息
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/updateAcct")
	@ResponseBody
	public ResultBean<Account> updateAcct(@RequestBody Account acct) {
		int result = accountService.updateAcct(acct);
		if(result != Constants.RESULT_NUM1 ) {
			ExceptionUtil.throwCheckException("更新账户失败");
		}
		return new ResultBean<Account>(null);
	}
	/**
	 * 启用或者停止使用账户
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/stopOrStartAcct/{id}/{status}")
	@ResponseBody
	public ResultBean<Account> stopOrStartAcct(@PathVariable("id") Long id,@PathVariable("status") String status) {
		Account acct = new Account();
		acct.setId(id);
		acct.setStatus(status);
		int result = accountService.stopOrStartAcct(acct);
		if(result != Constants.RESULT_NUM1 ) {
			ExceptionUtil.throwCheckException("对账户启动或停止操作失败");
		}

		return new ResultBean<Account>(null);
	}
	
}
