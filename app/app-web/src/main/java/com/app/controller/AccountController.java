package com.app.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.bean.PageResultBean;
import com.app.bean.ResultBean;
import com.app.common.Constants;
import com.app.config.Config;
import com.app.dto.AcctBalanceDTO;
import com.app.dto.AcctInOutDTO;
import com.app.entity.Account;
import com.app.entity.AcctQueryHist;
import com.app.entity.AcctTranHist;
import com.app.service.AccountService;
import com.app.service.AcctDataService;
import com.app.util.DateUtils;
import com.app.util.Emptys;
import com.app.util.ExceptionUtil;

@RestController
public class AccountController {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(AccountController.class);

	@Autowired
	private AccountService accountService;

	@Autowired
	private AcctDataService acctDataService;

	/**
	 * 添加账户
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/add")
	@ResponseBody
	public ResultBean<Account> addAccount(@RequestBody Account acct) {
		/*
		 * 判断账号会否已存在
		 */
		if (accountService.getAcctByAcctNo(acct.getAcctNo())) {
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
	 * 删除账户 此方法暂时不用，采用更新的方式 假删除
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("acct/delete/{id}")
	@ResponseBody
	public ResultBean<Account> deleteAccount(@PathVariable("id") Long id) {
		int result = accountService.deleteAcctByid(id);
		if (result != Constants.RESULT_NUM1) {
			ExceptionUtil.throwCheckException("删除账户失败");
		}
		return new ResultBean<Account>(null);

	}

	/**
	 * 分页查询账户
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/list")
	public ResultBean<PageResultBean<Account>> selectAccountList(@RequestBody Account acct) {
		return new ResultBean<PageResultBean<Account>>(accountService.selectListByPage(acct));
	}

	/**
	 * 按账户id查询账户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("acct/select/{id}")
	@ResponseBody
	public ResultBean<Account> selectAccountById(@PathVariable("id") Long id) {

		return new ResultBean<Account>(accountService.selectById(id));
	}

	/**
	 * 跟新账户信息
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/update")
	@ResponseBody
	public ResultBean<Account> updateAccount(@RequestBody Account acct) {
		int result = accountService.updateAcct(acct);
		if (result != Constants.RESULT_NUM1) {
			ExceptionUtil.throwCheckException("更新账户失败");
		}
		return new ResultBean<Account>(null);
	}

	/**
	 * 启用或者停止使用账户
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct/stopOrStartAcct/{id}/{status}")
	@ResponseBody
	public ResultBean<Account> stopOrStartAccount(@PathVariable("id") Long id, @PathVariable("status") String status) {
		Account acct = new Account();
		acct.setId(id);
		acct.setStatus(status);
		int result = accountService.stopOrStartAcct(acct);
		if (result != Constants.RESULT_NUM1) {
			ExceptionUtil.throwCheckException("对账户启动或停止操作失败");
		}

		return new ResultBean<Account>(null);
	}

	/**
	 * 分页查询账户流水
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct_tran_hist/list")
	public ResultBean<PageResultBean<AcctTranHist>> selectAcctTranHistList(
			@RequestBody HashMap<String, Object> queryMap) {
		return new ResultBean<PageResultBean<AcctTranHist>>(acctDataService.queryAcctTranHistListPage(queryMap));
	}

	/**
	 * 按账户id查询账户信息
	 * 
	 * @param id
	 * @return
	 */
	@RequestMapping("acct_tran_hist/{id}")
	@ResponseBody
	public ResultBean<AcctTranHist> selectAcctTranHistById(@PathVariable("id") Long id) {
		return new ResultBean<AcctTranHist>(acctDataService.queryAcctTranHistById(id));
	}

	/**
	 * 下载账户数据
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct_hist/download")
	public ResultBean<Boolean> download(@RequestBody HashMap<String, Object> queryMap) {
		String beginDate = (String) queryMap.get("beginDate");
		ExceptionUtil.throwEmptyCheckException(beginDate, "开始日期不能为空");
		String endDate = (String) queryMap.get("endDate");
		ExceptionUtil.throwEmptyCheckException(endDate, "结束日期不能为空");
		String acctNo = (String) queryMap.get("acctNo");
		if (Emptys.isNotEmpty(acctNo)) {
			if (!accountService.getAcctByAcctNo(acctNo)) {
				ExceptionUtil.throwCheckException("账户不存在或者未启用");
			}
			acctDataService.loadAcctBalanceHist(acctNo);
			acctDataService.loadAcctTranHist(acctNo, beginDate, endDate);
		} else {
			List<Account> accounts = accountService.getAllValidAcct();
			if (Emptys.isNotEmpty(accounts)) {
				for (Account account : accounts) {
					try {
						log.info("执行账号：" + account.getAcctNo());
						acctDataService.loadAcctBalanceHist(account.getAcctNo());
						acctDataService.loadAcctTranHist(account.getAcctNo(), beginDate, endDate);
					} catch (Exception e) {
						log.error("下载账户【{}】交易数据异常：", account.getAcctNo(), e);
					}
				}
			}
		}
		return new ResultBean<Boolean>(true);
	}

	@RequestMapping("acct_balance/{acctNo}")
	@ResponseBody
	public ResultBean<Boolean> queryAccountBalance(@PathVariable("acctNo") String acctNo) {
		if (!accountService.getAcctByAcctNo(acctNo)) {
			ExceptionUtil.throwCheckException("账户不存在或者未启用");
		}
		acctDataService.loadAcctBalanceHist(acctNo);
		String systemTranDate = Config.systemTranDate;
		if (Config.isEnableSystemTranDate) {
			systemTranDate = DateUtils.formatYYYYMMDD(new Date());
		}
		acctDataService.loadAcctTranHist(acctNo, systemTranDate, systemTranDate);
		return new ResultBean<Boolean>(true);
	}

	/**
	 * 查询账户余额信息
	 * 
	 * @return
	 */
	@RequestMapping("acct_banlance/list")
	public ResultBean<PageResultBean<AcctBalanceDTO>> selectAcctBalanceList() {
		return new ResultBean<PageResultBean<AcctBalanceDTO>>(acctDataService.queryAcctBalanceListPage());
	}

	/**
	 * 分页查询账户收支
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct_in_out_hist/list")
	public ResultBean<PageResultBean<AcctInOutDTO>> selectAcctInOutList(@RequestBody HashMap<String, Object> queryMap) {
		String beginDate = (String) queryMap.get("beginDate");
		ExceptionUtil.throwEmptyCheckException(beginDate, "开始时间不能为空");
		String endDate = (String) queryMap.get("endDate");
		ExceptionUtil.throwEmptyCheckException(endDate, "结束时间不能为空");
		return new ResultBean<PageResultBean<AcctInOutDTO>>(acctDataService.queryAcctInOutListPage(queryMap));
	}

	/**
	 * 分页查询账户数据下载记录
	 * 
	 * @param acct
	 * @return
	 */
	@RequestMapping("acct_query_hist/list")
	public ResultBean<PageResultBean<AcctQueryHist>> selectAcctQueryHistList(
			@RequestBody HashMap<String, Object> queryMap) {
		return new ResultBean<PageResultBean<AcctQueryHist>>(acctDataService.queryAcctQueryHistListPage(queryMap));
	}

}
