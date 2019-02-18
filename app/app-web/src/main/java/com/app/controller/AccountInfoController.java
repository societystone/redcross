package com.app.controller;

import java.util.Date;

import javax.servlet.http.HttpSession;

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
import com.app.entity.SysUser;
import com.app.service.AccountService;
import com.app.util.UserUtils;

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
		ResultBean<Account> rt = new ResultBean<Account>();
		SysUser su = (SysUser) UserUtils.getUser();
		acct.setCreateDate(new Date());
		acct.setUserCode(su.getUsername());
		acct.setUserName(su.getRealName());
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		int result = accountService.addAcct(acct);
		if(result > Constants.RESULT_NUM ) {
			rt.setCode(0);
		}else {
			rt.setCode(-1);
		}
		return rt;
	}
	
	/**
	 * 删除账户  此方法暂时不用，采用更新的方式 假删除
	 * @param id
	 * @return
	 */
	@RequestMapping("acct/delete/{id}")
	@ResponseBody
	public ResultBean<Account> addAcct(@PathVariable("id") Long id) {
		ResultBean<Account> rt = new ResultBean<Account>();
		int result = accountService.deleteAcctByid(id);
		if(result > Constants.RESULT_NUM ) {
			rt.setCode(0);
		}else {
			rt.setCode(-1);
		}
		return rt;
		
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
		ResultBean<Account> rt = new ResultBean<Account>();
		SysUser su = (SysUser) UserUtils.getUser();
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		int result = accountService.updateAcct(acct);
		if(result > Constants.RESULT_NUM ) {
			rt.setCode(0);
		}else {
			rt.setCode(-1);
		}
		return rt;
	}
	/**
	 * 启用或者停止使用账户
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/stopOrStartAcct/{id}/{status}")
	@ResponseBody
	public ResultBean<Account> stopOrStartAcct(@PathVariable("id") Long id,@PathVariable("status") String status) {
		ResultBean<Account> rt = new ResultBean<Account>();
		Account acct = new Account();
		acct.setId(id);
		acct.setStatus(status);
		
		SysUser su = (SysUser) UserUtils.getUser();
		acct.setModifyDate(new Date());
		acct.setUserCodeLastModify(su.getUsername());
		acct.setUserNameLastModify(su.getRealName());
		int result = accountService.stopOrStartAcct(acct);
		if(result > Constants.RESULT_NUM ) {
			rt.setCode(0);
		}else {
			rt.setCode(-1);
		}
		return rt;
	}
	
}
