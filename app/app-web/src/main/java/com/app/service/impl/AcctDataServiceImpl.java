package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.app.bankbean.qaccbal.BankBeanQaccbalOutRd;
import com.app.bankbean.qhisd.BankBeanQhisdOutRd;
import com.app.bean.PageResultBean;
import com.app.config.Config;
import com.app.dao.local.AcctBalanceHistDAO;
import com.app.dao.local.AcctQueryHistDAO;
import com.app.dao.local.AcctTranHistDAO;
import com.app.dto.AcctBalanceDTO;
import com.app.dto.AcctInOutDTO;
import com.app.entity.Account;
import com.app.entity.AcctBalanceHist;
import com.app.entity.AcctQueryHist;
import com.app.entity.AcctTranHist;
import com.app.service.AccountService;
import com.app.service.AcctDataService;
import com.app.service.BankDataService;
import com.app.util.DateUtils;
import com.app.util.DigitUtils;
import com.app.util.Emptys;
import com.app.util.PageUtils;
import com.github.pagehelper.PageHelper;

/**
 * 账户交易接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class AcctDataServiceImpl implements AcctDataService {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(AcctDataServiceImpl.class);

	/**
	 * 注入dao
	 */
	@Autowired
	private AcctTranHistDAO acctTranHistDAO;
	@Autowired
	private AcctQueryHistDAO acctQueryHistDAO;
	@Autowired
	private AcctBalanceHistDAO acctBalanceHistDAO;
	@Autowired
	private BankDataService bankDataService;
	@Autowired
	private AccountService accountService;

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public void loadAcctBalanceHist(String acctNo) {
		// TODO Auto-generated method stub
		log.info("查询账户余额开始：{}", acctNo);
		try {
			List<BankBeanQaccbalOutRd> rds = bankDataService.queryAcctBal(acctNo);
			if (Emptys.isEmpty(rds)) {
				log.error("查询余额异常：{}", acctNo);
				return;
			}
			for (BankBeanQaccbalOutRd rd : rds) {
				String queryTime = rd.getQueryTime();// 查询时间
				String queryDate = queryTime.substring(0, 8);// 查询日期
				HashMap<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("acctNo", acctNo);
				queryMap.put("date", queryDate);
				List<AcctBalanceHist> abhs = acctBalanceHistDAO.selectList(queryMap);
				if (abhs != null && !abhs.isEmpty()) {
					AcctBalanceHist acctBalanceHist = abhs.get(0);
					acctBalanceHist.setBalance(rd.getBalance());// 更新余额
					acctBalanceHist.setQueryTime(queryTime);
					acctBalanceHist.setModifyDate(new Date());
					acctBalanceHistDAO.update(acctBalanceHist);
				} else {
					AcctBalanceHist acctBalanceHist = new AcctBalanceHist();
					BeanUtils.copyProperties(acctBalanceHist, rd);
					acctBalanceHist.setAcctNo(acctNo);
					acctBalanceHist.setDate(queryDate);
					acctBalanceHist.setCreateDate(new Date());
					acctBalanceHistDAO.insert(acctBalanceHist);
				}
				if ("01".equals(queryDate.substring(6, 8))) {// 月初第一天
					String yesterDate = DateUtils
							.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(queryDate), -1));
					queryMap.put("date", yesterDate);
					abhs = acctBalanceHistDAO.selectList(queryMap);
					if (abhs != null && !abhs.isEmpty()) {
						AcctBalanceHist acctBalanceHist = abhs.get(0);
						acctBalanceHist.setBalance(rd.getAccBalance());// 更新余额
						acctBalanceHist.setQueryTime(queryTime);
						acctBalanceHist.setModifyDate(new Date());
						acctBalanceHistDAO.update(acctBalanceHist);
					} else {
						AcctBalanceHist acctBalanceHist = new AcctBalanceHist();
						BeanUtils.copyProperties(acctBalanceHist, rd);
						acctBalanceHist.setAccBalance(null);
						acctBalanceHist.setBalance(rd.getAccBalance());// 更新余额
						acctBalanceHist.setAcctNo(acctNo);
						acctBalanceHist.setDate(yesterDate);
						acctBalanceHist.setCreateDate(new Date());
						acctBalanceHistDAO.insert(acctBalanceHist);
					}
				}
			}
		} catch (Exception e) {
			log.error("查询账户【{}】余额异常：", acctNo, e);
		}
		log.info("查询账户余额结束");
	}

	@Transactional(value = "transactionManager", rollbackFor = Exception.class)
	@Override
	public void loadAcctTranHist(String acctNo, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		log.info("查询账户交易流水开始：{}--{}--{}", acctNo, beginDate, endDate);
		String systemTranDate = Config.systemTranDate;
		if (Config.isEnableSystemTranDate) {
			systemTranDate = DateUtils.formatYYYYMMDD(new Date());
		}
		if (systemTranDate.compareTo(endDate) < 0) {// 结束日期大于当前日期，将结束日期算作当前日期
			endDate = systemTranDate;
		}
		log.info("acct_no--beginDate--endDate：{}--{}--{}", acctNo, beginDate, endDate);
		List<Map<String, String>> timeRegionList = getQueryTimeRegion(acctNo, beginDate, endDate);
		log.info("查询时间区间：{}", timeRegionList);
		if (Emptys.isEmpty(timeRegionList)) {
			return;
		}
		for (Map<String, String> timeRegion : timeRegionList) {
			try {
				String beginQueryDate = timeRegion.get("beginDate");
				String endQueryDate = timeRegion.get("endDate");
				List<BankBeanQhisdOutRd> rds = bankDataService.downloadHistDayBankData(acctNo, beginQueryDate,
						endQueryDate);
				HashMap<String, Object> queryMap = new HashMap<String, Object>();
				queryMap.put("acctNo", acctNo);
				queryMap.put("beginDate", beginQueryDate);
				queryMap.put("endDate", endQueryDate);
				List<AcctQueryHist> acctQueryHistList = acctQueryHistDAO.selectList(queryMap);
				if (Emptys.isNotEmpty(rds)) {
					Set<String> tranNoExistsSet = new HashSet<>();// 本地已经存在的流水号集合
					if (acctQueryHistList != null && !acctQueryHistList.isEmpty()) {// 该段日期区间已经下载过
						List<AcctTranHist> aths = acctTranHistDAO.selectListByDate(queryMap);
						if (aths != null && !aths.isEmpty()) {
							for (AcctTranHist ath : aths) {
								tranNoExistsSet.add(ath.getOnlySequence());// 缓存本机已经存在的流水号
							}
						}
					}
					List<AcctTranHist> acctTranHists = new ArrayList<AcctTranHist>();
					int index = 0;
					for (BankBeanQhisdOutRd rd : rds) {
						String onlySequence = rd.getOnlySequence();// 银行交易流水号
						if (tranNoExistsSet.contains(onlySequence)) {// 本地已经存在，不再保存，保证增量保存
							continue;
						}
						AcctTranHist acctTranHist = new AcctTranHist();
						acctTranHist.setAcctNo(acctNo);
						BeanUtils.copyProperties(acctTranHist, rd);
//						acctTranHist.setDebitAmount(Emptys.isNotEmpty(rd.getDebitAmount()) ? Double.parseDouble(DigitUtils.divide(rd.getDebitAmount(), "100")) : null);
//						acctTranHist.setCreditAmount(Emptys.isNotEmpty(rd.getCreditAmount()) ? Double.parseDouble(DigitUtils.divide(rd.getCreditAmount(), "100")) : null);
//						acctTranHist.setBalance(Emptys.isNotEmpty(rd.getBalance()) ? Double.parseDouble(DigitUtils.divide(rd.getBalance(), "100")) : null);
//						acctTranHist.setTradeFee(Emptys.isNotEmpty(rd.getTradeFee()) ? Double.parseDouble(DigitUtils.divide(rd.getTradeFee(), "100")) : null);
//						acctTranHist.setTrxAmt(Emptys.isNotEmpty(rd.getTrxAmt()) ? Double.parseDouble(DigitUtils.divide(rd.getTrxAmt(), "100")) : null);
						acctTranHist.setCreateDate(new Date());
//						acctTranHistDAO.insert(acctTranHist);
						acctTranHists.add(acctTranHist);
						index++;
						if (index % Config.batchTotalCount == 0 || index == rds.size()) {
							acctTranHistDAO.insertBatch(acctTranHists);// 批量保存交易流水
							acctTranHists.clear();
						}
					}
				}

				// 保存更新查询记录
				int index = 0;
				List<AcctQueryHist> acctQueryHists = new ArrayList<AcctQueryHist>();
				do {
					boolean flag = false;
					for (AcctQueryHist acctQueryHist : acctQueryHistList) {
						if (beginQueryDate.equals(acctQueryHist.getQueryDate())) {
							if (!beginQueryDate.equals(systemTranDate)
									&& "N".equals(acctQueryHist.getIsNotDownload())) {
								acctQueryHist.setIsNotDownload("Y");
								acctQueryHist.setModifyDate(new Date());
								acctQueryHistDAO.update(acctQueryHist);
							}
							flag = true;
							break;
						}
					}
					if (!flag) {
						AcctQueryHist acctQueryHist = new AcctQueryHist();
						acctQueryHist.setAcctNo(acctNo);
						acctQueryHist.setQueryDate(beginQueryDate);
						if (beginQueryDate.equals(systemTranDate)) {
							acctQueryHist.setIsNotDownload("N");// 当天交易日期未完全下载
						} else {
							acctQueryHist.setIsNotDownload("Y");// 历史交易日期未完全下载
						}
						acctQueryHist.setCreateDate(new Date());
						acctQueryHists.add(acctQueryHist);
						index++;
						if (index % Config.batchTotalCount == 0) {
							acctQueryHistDAO.insertBatch(acctQueryHists);
							acctQueryHists.clear();
						}
					}
					beginQueryDate = DateUtils
							.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(beginQueryDate), 1));
				} while (beginQueryDate.compareTo(endQueryDate) <= 0);

				if (!acctQueryHists.isEmpty()) {
					acctQueryHistDAO.insertBatch(acctQueryHists);
				}
			} catch (Exception e) {
				log.error("下载流水异常：", e);
			}
		}
		log.info("查询账户交易流水结束");
	}

	@Override
	public PageResultBean<AcctBalanceDTO> queryAcctBalanceListPage() {
		// TODO Auto-generated method stub
		PageResultBean<AcctBalanceDTO> resultPages = null;
		List<AcctBalanceDTO> result = new ArrayList<AcctBalanceDTO>();
		PageResultBean<Account> pages = accountService.selectListByPage(null);// 查询账户
		List<Account> accounts = pages.getRows();
		if (Emptys.isEmpty(accounts)) {
			return resultPages;
		}
		for (Account account : accounts) {
			String acctNo = account.getAcctNo();
			AcctBalanceDTO dto = new AcctBalanceDTO();
			dto.setAcctNo(acctNo);
			dto.setAcctName(account.getAcctName());
			dto.setBankName(account.getBankName());
			dto.setAcctStatus(account.getStatus());
			HashMap<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("acctNo", acctNo);
			queryMap.put("isLast", "Y");
			List<AcctBalanceHist> abhs = acctBalanceHistDAO.selectList(queryMap);// 查询余额
			if (Emptys.isNotEmpty(abhs)) {
				AcctBalanceHist abh = abhs.get(0);
				dto.setQueryTime(abh.getQueryTime());// 查询时间
				dto.setBalance(DigitUtils.divide(abh.getBalance(), "100", 2));// 余额
			}
			// 查询收支
			AcctTranHist ath = acctTranHistDAO.selectSumAmount(queryMap);
			String inAmount = "";// 收入
			String outAmount = "";// 支出
			if (Emptys.isNotEmpty(ath)) {
				inAmount = DigitUtils.divide(ath.getCreditAmount(), "100", 2);// 贷方
				outAmount = DigitUtils.divide(ath.getDebitAmount(), "100", 2);// 借方
			}
			dto.setInAmount(inAmount);
			dto.setOutAmount(outAmount);
			result.add(dto);
		}
		resultPages = new PageResultBean<AcctBalanceDTO>(result);
		resultPages.setRows(result);
		resultPages.setPageNum(pages.getPageNum());
		resultPages.setPages(pages.getPages());
		resultPages.setPageSize(pages.getPageSize());
		resultPages.setTotal(pages.getTotal());
		return resultPages;
	}

	@Override
	public List<AcctBalanceDTO> queryAcctBalanceList() {
		// TODO Auto-generated method stub
		List<AcctBalanceDTO> result = new ArrayList<AcctBalanceDTO>();
		List<Account> accounts = accountService.selectList(null);// 查询账户
		for (Account account : accounts) {
			String acctNo = account.getAcctNo();
			AcctBalanceDTO dto = new AcctBalanceDTO();
			dto.setAcctNo(acctNo);
			dto.setAcctName(account.getAcctName());
			dto.setBankName(account.getBankName());
			dto.setAcctStatus(account.getStatus());
			HashMap<String, Object> queryMap = new HashMap<String, Object>();
			queryMap.put("acctNo", acctNo);
			queryMap.put("isLast", "Y");
			List<AcctBalanceHist> abhs = acctBalanceHistDAO.selectList(queryMap);// 查询余额
			if (Emptys.isNotEmpty(abhs)) {
				AcctBalanceHist abh = abhs.get(0);
				dto.setQueryTime(abh.getQueryTime());// 查询时间
				dto.setBalance(DigitUtils.divide(abh.getBalance(), "100", 2));// 余额
			}
			// 查询收支
			AcctTranHist ath = acctTranHistDAO.selectSumAmount(queryMap);
			String inAmount = "";// 收入
			String outAmount = "";// 支出
			if (Emptys.isNotEmpty(ath)) {
				inAmount = DigitUtils.divide(ath.getCreditAmount(), "100", 2);// 贷方
				outAmount = DigitUtils.divide(ath.getDebitAmount(), "100", 2);// 借方
			}
			dto.setInAmount(inAmount);
			dto.setOutAmount(outAmount);
			result.add(dto);
		}
		return result;
	}

	@Override
	public PageResultBean<AcctInOutDTO> queryAcctInOutListPage(HashMap<String, Object> queryMap) {
		// TODO Auto-generated method stub
		PageResultBean<AcctInOutDTO> resultPages = null;
		List<AcctInOutDTO> result = new ArrayList<AcctInOutDTO>();
		String beginDate = (String) queryMap.get("beginDate");
		String endDate = (String) queryMap.get("endDate");
		String type = (String) queryMap.get("type");
		if ("1".equals(type)) {// 日
			queryMap.put("beginDate", beginDate);
			queryMap.put("endDate", endDate);
		} else if ("2".equals(type)) {// 月
			queryMap.put("beginDate", beginDate + "00");
			queryMap.put("endDate", endDate + "31");
		}
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<AcctBalanceHist> pages = new PageResultBean<AcctBalanceHist>(
				acctBalanceHistDAO.selectBalanceHist(queryMap));// 查询余额
		List<AcctBalanceHist> abhs = pages.getRows();
		if (Emptys.isEmpty(abhs)) {
			return resultPages;
		}
		// 分账户查询收支信息
		Set<String> acctNoSet = new HashSet<String>();
		Map<String, String> acctInOutAmount = new HashMap<>();
		for (AcctBalanceHist abh : abhs) {
			String acctNo = abh.getAcctNo();
			if (!acctNoSet.contains(acctNo)) {
				acctInOutAmount.putAll(getAccountInOutAmount(queryMap));
				acctNoSet.add(acctNo);
			}
			Account account = accountService.getAccount(acctNo);
			AcctInOutDTO acctInOutDTO = new AcctInOutDTO();
			acctInOutDTO.setAcctNo(acctNo);
			acctInOutDTO.setAcctName(account.getAcctName());
			acctInOutDTO.setBankName(account.getBankName());
			acctInOutDTO.setBalance(DigitUtils.divide(abh.getBalance(), "100", 2));
			acctInOutDTO.setDate(abh.getDate());
			acctInOutDTO.setInAmount(acctInOutAmount.get("IN-" + acctNo + "-" + abh.getDate()));
			acctInOutDTO.setOutAmount(acctInOutAmount.get("OUT-" + acctNo + "-" + abh.getDate()));
			result.add(acctInOutDTO);
		}
		resultPages = new PageResultBean<AcctInOutDTO>(result);
		resultPages.setRows(result);
		resultPages.setPageNum(pages.getPageNum());
		resultPages.setPages(pages.getPages());
		resultPages.setPageSize(pages.getPageSize());
		resultPages.setTotal(pages.getTotal());
		return resultPages;
	}

	@Override
	public List<AcctInOutDTO> queryAcctInOutList(HashMap<String, Object> queryMap) {
		// TODO Auto-generated method stub
		List<AcctInOutDTO> result = new ArrayList<AcctInOutDTO>();
		String beginDate = (String) queryMap.get("beginDate");
		String endDate = (String) queryMap.get("endDate");
		String type = (String) queryMap.get("type");
		if ("1".equals(type)) {// 日
			queryMap.put("beginDate", beginDate);
			queryMap.put("endDate", endDate);
		} else if ("2".equals(type)) {// 月
			queryMap.put("beginDate", beginDate + "00");
			queryMap.put("endDate", endDate + "31");
		}
		List<AcctBalanceHist> abhs = acctBalanceHistDAO.selectBalanceHist(queryMap);// 查询余额
		// 分账户查询收支信息
		Set<String> acctNoSet = new HashSet<String>();
		Map<String, String> acctInOutAmount = new HashMap<>();
		for (AcctBalanceHist abh : abhs) {
			String acctNo = abh.getAcctNo();
			if (!acctNoSet.contains(acctNo)) {
				acctInOutAmount.putAll(getAccountInOutAmount(queryMap));
				acctNoSet.add(acctNo);
			}
			Account account = accountService.getAccount(acctNo);
			AcctInOutDTO acctInOutDTO = new AcctInOutDTO();
			acctInOutDTO.setAcctNo(acctNo);
			acctInOutDTO.setAcctName(account.getAcctName());
			acctInOutDTO.setBankName(account.getBankName());
			acctInOutDTO.setBalance(DigitUtils.divide(abh.getBalance(), "100", 2));
			acctInOutDTO.setDate(abh.getDate());
			acctInOutDTO.setInAmount(acctInOutAmount.get("IN-" + acctNo + "-" + abh.getDate()));
			acctInOutDTO.setOutAmount(acctInOutAmount.get("OUT-" + acctNo + "-" + abh.getDate()));
			result.add(acctInOutDTO);
		}
		return result;
	}

	@Override
	public PageResultBean<AcctTranHist> queryAcctTranHistListPage(HashMap<String, Object> queryMap) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<AcctTranHist> pages = new PageResultBean<AcctTranHist>(acctTranHistDAO.selectList(queryMap));
		List<AcctTranHist> aths = pages.getRows();
		if (Emptys.isNotEmpty(aths)) {
			for (AcctTranHist ath : aths) {
				Account account = accountService.getAccount(ath.getAcctNo());
				ath.setAcctName(account.getAcctName());
				ath.setBankName(account.getBankName());
				ath.setDebitAmount(DigitUtils.divide(ath.getDebitAmount(), "100", 2));
				ath.setCreditAmount(DigitUtils.divide(ath.getCreditAmount(), "100", 2));
				ath.setBalance(DigitUtils.divide(ath.getBalance(), "100", 2));
				ath.setTradeFee(DigitUtils.divide(ath.getTradeFee(), "100", 2));
				ath.setTrxAmt(DigitUtils.divide(ath.getTrxAmt(), "100", 2));
			}
		}
		return pages;
	}

	@Override
	public List<AcctTranHist> queryAcctTranHistList(HashMap<String, Object> queryMap) {
		// TODO Auto-generated method stub
		List<AcctTranHist> aths = acctTranHistDAO.selectList(queryMap);
		if (Emptys.isNotEmpty(aths)) {
			for (AcctTranHist ath : aths) {
				Account account = accountService.getAccount(ath.getAcctNo());
				ath.setAcctName(account.getAcctName());
				ath.setBankName(account.getBankName());
				ath.setDebitAmount(DigitUtils.divide(ath.getDebitAmount(), "100", 2));
				ath.setCreditAmount(DigitUtils.divide(ath.getCreditAmount(), "100", 2));
				ath.setBalance(DigitUtils.divide(ath.getBalance(), "100", 2));
				ath.setTradeFee(DigitUtils.divide(ath.getTradeFee(), "100", 2));
				ath.setTrxAmt(DigitUtils.divide(ath.getTrxAmt(), "100", 2));
			}
		}
		return aths;
	}

	@Override
	public AcctTranHist queryAcctTranHistById(Long id) {
		// TODO Auto-generated method stub
		AcctTranHist ath = acctTranHistDAO.selectByPrimaryKey(id);
		if (Emptys.isNotEmpty(ath)) {
			ath.setAcctName(accountService.getAccount(ath.getAcctNo()).getAcctName());
			ath.setDebitAmount(DigitUtils.divide(ath.getDebitAmount(), "100", 2));
			ath.setCreditAmount(DigitUtils.divide(ath.getCreditAmount(), "100", 2));
			ath.setBalance(DigitUtils.divide(ath.getBalance(), "100", 2));
			ath.setTradeFee(DigitUtils.divide(ath.getTradeFee(), "100", 2));
			ath.setTrxAmt(DigitUtils.divide(ath.getTrxAmt(), "100", 2));
		}
		return ath;
	}

	@Override
	public PageResultBean<AcctQueryHist> queryAcctQueryHistListPage(HashMap<String, Object> queryMap) {
		// TODO Auto-generated method stub
		PageHelper.startPage(PageUtils.getPageNum(), PageUtils.getPageSize());
		PageResultBean<AcctQueryHist> pages = new PageResultBean<AcctQueryHist>(acctQueryHistDAO.selectList(queryMap));
		List<AcctQueryHist> aqhs = pages.getRows();
		if (Emptys.isNotEmpty(aqhs)) {
			for (AcctQueryHist aqh : aqhs) {
				Account account = accountService.getAccount(aqh.getAcctNo());
				aqh.setAcctName(account.getAcctName());
				aqh.setBankName(account.getBankName());
			}
		}
		return pages;
	}

	/**
	 * 获取查询时间区间列表
	 * 
	 * @param acctNo
	 * @param beginDateStr
	 * @param endDateStr
	 * @return
	 */
	private List<Map<String, String>> getQueryTimeRegion(String acctNo, String beginDateStr, String endDateStr) {
		List<Map<String, String>> timeRegionList = new ArrayList<Map<String, String>>();
		HashMap<String, Object> queryMap = new HashMap<String, Object>();
		queryMap.put("acctNo", acctNo);
		queryMap.put("beginDate", beginDateStr);
		queryMap.put("endDate", endDateStr);
		queryMap.put("isNotDownload", "Y");// 查询不再下载的日期
		List<AcctQueryHist> acctQueryHists = acctQueryHistDAO.selectList(queryMap);
		String dateStrTmp = beginDateStr;
		if (Emptys.isNotEmpty(acctQueryHists)) {
			for (int i = 0; i < acctQueryHists.size(); i++) {
				AcctQueryHist acctQueryHist = acctQueryHists.get(i);
				String rQueryDateStr = acctQueryHist.getQueryDate();
				if (dateStrTmp.compareTo(rQueryDateStr) < 0) {
					Map<String, String> map = new HashMap<String, String>();
					map.put("beginDate", beginDateStr);
					map.put("endDate",
							DateUtils.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(rQueryDateStr), -1)));
					timeRegionList.add(map);
				}
				beginDateStr = DateUtils.formatYYYYMMDD(DateUtils.addDay(DateUtils.parseYYYYMMDD(rQueryDateStr), 1));
				dateStrTmp = beginDateStr;
			}
		}
		if (dateStrTmp.compareTo(endDateStr) <= 0) {
			Map<String, String> map = new HashMap<String, String>();
			map.put("beginDate", beginDateStr);
			map.put("endDate", endDateStr);
			timeRegionList.add(map);
		}
		return timeRegionList;
	}

	/**
	 * 查询账户在一段时间范围的收支信息
	 * 
	 * @param queryMap
	 * @return
	 */
	private Map<String, String> getAccountInOutAmount(HashMap<String, Object> queryMap) {
		Map<String, String> result = new HashMap<String, String>();
		// 查询收支
		String acctNo = (String) queryMap.get("acctNo");
		List<AcctTranHist> aths = acctTranHistDAO.selectSumAmountByDate(queryMap);
		if (Emptys.isEmpty(aths)) {
			return result;
		}
		for (AcctTranHist ath : aths) {
			result.put("IN-" + acctNo + "-" + ath.getDate(), DigitUtils.divide(ath.getCreditAmount(), "100", 2));// 贷方
			result.put("OUT-" + acctNo + "-" + ath.getDate(), DigitUtils.divide(ath.getDebitAmount(), "100", 2));// 借方
		}
		return result;
	}
}