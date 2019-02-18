package com.app.taskjob;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.app.entity.AcctInfo;
import com.app.service.AcctInfoService;
import com.app.service.AcctTranHistService;
import com.app.util.DateUtils;
import com.app.util.Emptys;

@Component
public class AcctJob {

	@Autowired
	private AcctTranHistService acctTranHistService;
	@Autowired
	private AcctInfoService acctInfoService;

	/**
	 * 自动任务：从银行下载有效账户的昨日交易流水
	 */
	@Scheduled(cron = "${task.cron.acctJob}")
	private void downloadAcctBankData() {
		System.out.println("自动任务开始...........");
		Date nowDate = new Date();
		Date yesterday = DateUtils.addDay(nowDate, -1);
		List<AcctInfo> acctInfos = acctInfoService.getAllValidAcct();
		if (Emptys.isNotEmpty(acctInfos)) {
			for (AcctInfo acctInfo : acctInfos) {
				try {
					System.out.println("执行账号：" + acctInfo.getAcctNo());
					acctTranHistService.loadAcctTranHist(acctInfo, yesterday, yesterday, "1");
				} catch (Exception e) {

				}
			}
		}
		System.out.println("自动任务结束...........");
	}
}
