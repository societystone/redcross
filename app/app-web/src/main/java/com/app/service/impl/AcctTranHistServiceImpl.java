package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bankbean.qhisd.BankBeanQhisdOutRd;
import com.app.dao.local.AcctQueryHistDAO;
import com.app.dao.local.AcctTranHistDAO;
import com.app.entity.Account;
import com.app.entity.AcctQueryHist;
import com.app.entity.AcctTranHist;
import com.app.service.AcctTranHistService;
import com.app.service.BankDataService;
import com.app.util.DateUtils;
import com.app.util.Emptys;

/**
 * 账户交易流水接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class AcctTranHistServiceImpl implements AcctTranHistService {

	/**
	 * 注入dao
	 */
	@Autowired
	private AcctTranHistDAO acctTranHistDAO;
	@Autowired
	private AcctQueryHistDAO acctQueryHistDAO;
	@Autowired
	private BankDataService bankDataService;

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public void loadAcctTranHist(Account account, Date beginDate, Date endDate, String taskType) {
		// TODO Auto-generated method stub
		Long acctId = account.getId();
		List<Map<String, String>> timeRegionList = getQueryTimeRegion(acctId, beginDate, endDate);
		if (Emptys.isNotEmpty(timeRegionList)) {
			String acctNo = account.getAcctNo();
			int batchTotalCount = 100;
			for (Map<String, String> timeRegion : timeRegionList) {
				String isNowDay = timeRegion.get("isNowDay");
				if ("Y".equals(isNowDay)) {// 当天交易
					String nowDateStr = timeRegion.get("nowDate");
//					String beginTime = timeRegion.get("beginTime");
//					String endTime = timeRegion.get("endTime");
//					List<BankBeanQpdOutRd> rds = bankDataService.downloadNowDayBankData(acctNo, beginTime, endTime);
//					if (Emptys.isNotEmpty(rds)) {
//						for (BankBeanQpdOutRd rd : rds) {
//							AcctTranHist acctTranHist = new AcctTranHist();
//							acctTranHist.setAcctId(acctId);
//							BeanUtils.copyProperties(rd, acctTranHist);
//							acctTranHist.setCreateDate(new Date());
//							acctTranHistDAO.insert(acctTranHist);
//						}
//					}
					List<BankBeanQhisdOutRd> rds = bankDataService.downloadHistDayBankData(acctNo, nowDateStr,
							nowDateStr);
					if (Emptys.isNotEmpty(rds)) {
						HashMap<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("acctId", acctId);
						queryMap.put("beginDate", nowDateStr);
						queryMap.put("endDate", nowDateStr);
						if (acctTranHistDAO.selectCount(queryMap) > 0) {
							acctTranHistDAO.delete(queryMap);
						}
						List<AcctTranHist> acctTranHists = new ArrayList<AcctTranHist>();
						int index = 0;
						for (BankBeanQhisdOutRd rd : rds) {
							AcctTranHist acctTranHist = new AcctTranHist();
							acctTranHist.setAcctId(acctId);
							BeanUtils.copyProperties(rd, acctTranHist);
							acctTranHist.setDataLoadType(taskType);
							acctTranHist.setCreateDate(new Date());
//							acctTranHistDAO.insert(acctTranHist);
							acctTranHists.add(acctTranHist);
							index++;
							if (index % batchTotalCount == 0 || index == rds.size()) {
								acctTranHistDAO.insertBatch(acctTranHists);
								acctTranHists = new ArrayList<AcctTranHist>();
							}
						}
					}
					AcctQueryHist acctQueryHist = new AcctQueryHist();
					acctQueryHist.setAcctId(acctId);
					acctQueryHist.setBeginDate(nowDateStr);
					acctQueryHist.setEndDate(nowDateStr);
					acctQueryHist.setIsNotDownload("N");// 当日交易流水没有从银行下载完全
					acctQueryHist.setType(taskType);
					acctQueryHist.setCreateDate(new Date());
					acctQueryHistDAO.insert(acctQueryHist);
				} else {// 历史交易日
					String beginDateStr = timeRegion.get("beginDate");
					String endDateStr = timeRegion.get("endDate");
					List<BankBeanQhisdOutRd> rds = bankDataService.downloadHistDayBankData(acctNo, beginDateStr,
							endDateStr);
					if (Emptys.isNotEmpty(rds)) {
						HashMap<String, Object> queryMap = new HashMap<String, Object>();
						queryMap.put("acctId", acctId);
						queryMap.put("beginDate", beginDateStr);
						queryMap.put("endDate", endDateStr);
						if (acctTranHistDAO.selectCount(queryMap) > 0) {
							acctTranHistDAO.delete(queryMap);
						}
						List<AcctTranHist> acctTranHists = new ArrayList<AcctTranHist>();
						int index = 0;
						for (BankBeanQhisdOutRd rd : rds) {
							AcctTranHist acctTranHist = new AcctTranHist();
							acctTranHist.setAcctId(acctId);
							BeanUtils.copyProperties(rd, acctTranHist);
							acctTranHist.setDataLoadType(taskType);
							acctTranHist.setCreateDate(new Date());
//							acctTranHistDAO.insert(acctTranHist);
							acctTranHists.add(acctTranHist);
							index++;
							if (index % batchTotalCount == 0 || index == rds.size()) {
								acctTranHistDAO.insertBatch(acctTranHists);
								acctTranHists = new ArrayList<AcctTranHist>();
							}
						}
					}
					AcctQueryHist acctQueryHist = new AcctQueryHist();
					acctQueryHist.setAcctId(acctId);
					acctQueryHist.setBeginDate(beginDateStr);
					acctQueryHist.setEndDate(endDateStr);
					acctQueryHist.setIsNotDownload("Y");// 不再从银行下载
					acctQueryHist.setType(taskType);
					acctQueryHist.setCreateDate(new Date());
					acctQueryHistDAO.insert(acctQueryHist);
				}
			}
		}
	}

	/**
	 * 获取查询时间区间列表
	 * 
	 * @param acctId
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	private List<Map<String, String>> getQueryTimeRegion(Long acctId, Date beginDate, Date endDate) {
		List<Map<String, String>> timeRegionList = new ArrayList<Map<String, String>>();
		String beginDateStr = DateUtils.formatYYYYMMDD(beginDate);
		String endDateStr = DateUtils.formatYYYYMMDD(endDate);
		Date nowDate = new Date();
		String nowDateStr = DateUtils.formatYYYYMMDD(nowDate);
		if (nowDateStr.compareTo(endDateStr) < 0) {// 结束日期大于当前日期，将结束日期算作当前日期
			endDateStr = nowDateStr;
		}
		if (nowDateStr.compareTo(endDateStr) == 0) {// 结束日期等于当前日期
			Map<String, String> map = new HashMap<String, String>();
			map.put("isNowDay", "Y");
			map.put("nowDate", beginDateStr);
			timeRegionList.add(map);
			endDateStr = DateUtils.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(endDateStr), -1));
		}
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("acctId", acctId);
		queryMap.put("beginDate", beginDateStr);
		queryMap.put("endDate", endDateStr);
		queryMap.put("isNotDownload", "Y");
		List<AcctQueryHist> acctQueryHists = acctQueryHistDAO.selectList(queryMap);
		String dateStrTmp = beginDateStr;
		if (Emptys.isNotEmpty(acctQueryHists)) {
			for (AcctQueryHist acctQueryHist : acctQueryHists) {
				String rBeginDateStr = acctQueryHist.getBeginDate();
				String rEndDateStr = acctQueryHist.getEndDate();
				if (dateStrTmp.compareTo(rBeginDateStr) < 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("isNowDay", "N");
					map.put("beginDate", dateStrTmp);
					map.put("endDate",
							DateUtils.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(rBeginDateStr), -1)));
					timeRegionList.add(map);
					dateStrTmp = DateUtils.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(rEndDateStr), 1));
				} else {
					dateStrTmp = DateUtils.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(rEndDateStr), 1));
				}
			}
		}
		if (dateStrTmp.compareTo(endDateStr) <= 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("isNowDay", "N");
			map.put("beginDate", dateStrTmp);
			map.put("endDate", endDateStr);
			timeRegionList.add(map);
		}
		return timeRegionList;
	}
}