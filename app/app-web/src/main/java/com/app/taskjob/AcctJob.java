package com.app.taskjob;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.entity.Account;
import com.app.service.AccountService;
import com.app.service.AcctDataService;
import com.app.util.DateUtils;
import com.app.util.Emptys;

@Component
public class AcctJob {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(AcctJob.class);

	@Autowired
	private AcctDataService acctDataService;
	@Autowired
	private AccountService accountService;

	/**
	 * 自动任务：从银行下载有效账户的昨日交易流水
	 */
	@Scheduled(cron = "${task.cron.acctJob}")
	private void downloadAcctBankData() {
		log.info("自动任务开始...........");
		Date nowDate = new Date();
		String yesterday = DateUtils.formatYYYYMMDD(DateUtils.addDay(nowDate, -1));
		List<Account> accounts = accountService.getAllValidAcct();
		if (Emptys.isNotEmpty(accounts)) {
			for (Account account : accounts) {
				try {
					log.info("执行账号：" + account.getAcctNo());
					acctDataService.loadAcctBalanceHist(account.getAcctNo(),"1");
					acctDataService.loadAcctTranHist(account.getAcctNo(), yesterday, DateUtils.formatYYYYMMDD(nowDate),"1");
				} catch (Exception e) {
					log.error("下载账户【{}】交易数据异常：", account.getAcctNo(), e);
				}
			}
		}
		log.info("自动任务结束...........");
	}
}
