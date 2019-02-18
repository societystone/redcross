package com.app.taskjob;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.entity.Account;
import com.app.service.AccountService;
import com.app.service.AcctTranHistService;
import com.app.util.DateUtils;
import com.app.util.Emptys;

@Component
public class AcctJob {

	@Autowired
	private AcctTranHistService acctTranHistService;
	@Autowired
	private AccountService accountService;

	/**
	 * 自动任务：从银行下载有效账户的昨日交易流水
	 */
	@Scheduled(cron = "${task.cron.acctJob}")
	private void downloadAcctBankData() {
		System.out.println("自动任务开始...........");
		Date nowDate = new Date();
		Date yesterday = DateUtils.addDay(nowDate, -1);
		List<Account> accounts = accountService.getAllValidAcct();
		if (Emptys.isNotEmpty(accounts)) {
			for (Account account : accounts) {
				try {
					System.out.println("执行账号：" + account.getAcctNo());
					acctTranHistService.loadAcctTranHist(account, yesterday, yesterday, "1");
				} catch (Exception e) {

				}
			}
		}
		System.out.println("自动任务结束...........");
	}
}
