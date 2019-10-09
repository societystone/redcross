package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.bankbean.BankBeanRequest;
import com.app.bankbean.pub.BankBeanPubResponse;
import com.app.bankbean.qaccbal.BankBeanQaccbalIn;
import com.app.bankbean.qaccbal.BankBeanQaccbalInRd;
import com.app.bankbean.qaccbal.BankBeanQaccbalOut;
import com.app.bankbean.qaccbal.BankBeanResponseQaccbal;
import com.app.bankbean.qhisd.BankBeanQhisdIn;
import com.app.bankbean.qhisd.BankBeanQhisdOut;
import com.app.bankbean.qhisd.BankBeanQhisdOutRd;
import com.app.bankbean.qhisd.BankBeanResponseQhisd;
import com.app.bankbean.qpd.BankBeanQpdIn;
import com.app.bankbean.qpd.BankBeanQpdOut;
import com.app.bankbean.qpd.BankBeanQpdOutRd;
import com.app.bankbean.qpd.BankBeanResponseQpd;
import com.app.config.Config;
import com.app.service.BankDataService;
import com.app.util.DateUtils;
import com.app.util.Emptys;

import icbc.api.NCExample;

/**
 * 银行数据接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class BankDataServiceImpl implements BankDataService {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(BankDataServiceImpl.class);

	@Override
	public boolean downloadHistDayBankData(Map<String, Object> result, String acctNo, String beginDate,
			String endDate) {
		// TODO Auto-generated method stub
		try {
			log.info("查询参数：{}--{}--{}", acctNo, beginDate, endDate);
			List<BankBeanQhisdOutRd> resultList = new ArrayList<BankBeanQhisdOutRd>();
			String systemTranDate = Config.systemTranDate;
			if (Config.isEnableSystemTranDate) {
				systemTranDate = DateUtils.formatYYYYMMDD(new Date());
			}
			String nextTag = "";
			int queryPage = 1;
			do {
				log.info("查询页码：{}", queryPage++);
				String sTransCode = "QHISD";// 交易代码
				BankBeanQhisdIn bbi = new BankBeanQhisdIn();
				bbi.setAccNo(acctNo);
				bbi.setMinAmt("0");
				bbi.setMaxAmt("99999999999999999");
				bbi.setBeginDate(beginDate);
				bbi.setEndDate(endDate);
				bbi.setNextTag(nextTag);
				BankBeanRequest<BankBeanQhisdIn> bbRequest = new BankBeanRequest<BankBeanQhisdIn>(null, bbi);
				BankBeanResponseQhisd bbResponse = NCExample.sendToBank(BankBeanResponseQhisd.class, sTransCode,
						bbRequest, systemTranDate);
				nextTag = "";
				boolean flag = false;
				if (Emptys.isNotEmpty(bbResponse)) {
					BankBeanPubResponse pubResponse = bbResponse.getPub();
					if (Emptys.isNotEmpty(pubResponse)) {
						String retCode = pubResponse.getRetCode();
						log.info("查询参数：{}--{}--{}，工行返回：{}--{}", acctNo, beginDate, endDate, retCode,
								pubResponse.getRetMsg());
						result.put("retCode", retCode);
						result.put("retMsg", pubResponse.getRetMsg());
						if ("0".equals(retCode) || "B0116".equals(retCode)) {
							BankBeanQhisdOut out = (BankBeanQhisdOut) bbResponse.getOut();
							if (Emptys.isNotEmpty(out)) {
								if (out.getTotalNum() > 0) {
									resultList.addAll(out.getRds());
									nextTag = out.getNextTag();
								}
								flag = true;
							}
						}
					}
				}
				if (!flag) {
					log.error("下载账户当日交易流水失败");
					return false;
				}
			} while (Emptys.isNotEmpty(nextTag));
			result.put("data", resultList);
			return true;
		} catch (Exception e) {
			log.error("下载账户当日交易流水异常：", e);
			return false;
		}
	}

	@Override
	public boolean downloadNowDayBankData(Map<String, Object> result, String acctNo, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		try {
			log.info("查询参数：{}--{}--{}", acctNo, beginTime, endTime);
			List<BankBeanQpdOutRd> resultList = new ArrayList<BankBeanQpdOutRd>();
			String systemTranDate = Config.systemTranDate;
			if (Config.isEnableSystemTranDate) {
				systemTranDate = DateUtils.formatYYYYMMDD(new Date());
			}
			String nextTag = "";
			int queryPage = 1;
			do {
				log.info("查询页码：{}", queryPage++);
				String sTransCode = "QPD";// 交易代码
				BankBeanQpdIn bbi = new BankBeanQpdIn();
				bbi.setAccNo(acctNo);
				bbi.setMinAmt("0");
				bbi.setMaxAmt("0");
				bbi.setBeginTime(beginTime);
				bbi.setEndTime(endTime);
				bbi.setNextTag(nextTag);
				BankBeanRequest<BankBeanQpdIn> bbRequest = new BankBeanRequest<BankBeanQpdIn>(null, bbi);
				BankBeanResponseQpd bbResponse = NCExample.sendToBank(BankBeanResponseQpd.class, sTransCode, bbRequest,
						systemTranDate);
				nextTag = "";
				boolean flag = false;
				if (Emptys.isNotEmpty(bbResponse)) {
					BankBeanPubResponse pubResponse = bbResponse.getPub();
					if (Emptys.isNotEmpty(pubResponse)) {
						String retCode = pubResponse.getRetCode();
						log.info("查询参数：{}--{}--{}，工行返回：{}--{}", acctNo, beginTime, endTime, retCode,
								pubResponse.getRetMsg());
						result.put("retCode", retCode);
						result.put("retMsg", pubResponse.getRetMsg());
						if ("0".equals(retCode) || "B0116".equals(retCode)) {
							BankBeanQpdOut out = (BankBeanQpdOut) bbResponse.getOut();
							if (Emptys.isNotEmpty(out)) {
								if (out.getTotalNum() > 0) {
									resultList.addAll(out.getRds());
									nextTag = out.getNextTag();
								}
								flag = true;
							}
						}
					}
				}
				if (!flag) {
					log.error("下载账户当日交易流水失败");
					return false;
				}
			} while (Emptys.isNotEmpty(nextTag));
			result.put("data", resultList);
			return true;
		} catch (Exception e) {
			log.error("下载账户当日交易流水异常：", e);
			return false;
		}
	}

	@Override
	public boolean downloadAcctBal(Map<String, Object> result, String acctNo) {
		// TODO Auto-generated method stub
		try {
			String systemTranDate = Config.systemTranDate;
			if (Config.isEnableSystemTranDate) {
				systemTranDate = DateUtils.formatYYYYMMDD(new Date());
			}
			String sTransCode = "QACCBAL";// 交易代码
			BankBeanQaccbalIn bbi = new BankBeanQaccbalIn();
			bbi.setTotalNum(Integer.toString(1));
			List<BankBeanQaccbalInRd> rds = new ArrayList<BankBeanQaccbalInRd>();
			BankBeanQaccbalInRd inRd = new BankBeanQaccbalInRd();
			inRd.setISeqno(Integer.toString(1));
			inRd.setAccNo(acctNo);
			rds.add(inRd);
			bbi.setRds(rds);
			BankBeanRequest<BankBeanQaccbalIn> bbRequest = new BankBeanRequest<BankBeanQaccbalIn>(null, bbi);
			BankBeanResponseQaccbal bbResponse = (BankBeanResponseQaccbal) NCExample
					.sendToBank(BankBeanResponseQaccbal.class, sTransCode, bbRequest, systemTranDate);
			if (Emptys.isNotEmpty(bbResponse)) {
				BankBeanPubResponse pubResponse = bbResponse.getPub();
				if (Emptys.isNotEmpty(pubResponse)) {
					String retCode = pubResponse.getRetCode();
					log.info("查询参数：{}，工行返回：{}--{}", acctNo, retCode, pubResponse.getRetMsg());
					result.put("retCode", retCode);
					result.put("retMsg", pubResponse.getRetMsg());
					if ("0".equals(retCode)) {
						BankBeanQaccbalOut out = (BankBeanQaccbalOut) bbResponse.getOut();
						if (Emptys.isNotEmpty(out)) {
							result.put("data", out.getRds().get(0));
							return true;
						}
					}
				}
			}
		} catch (Exception e) {
			log.error("下载账户余额信息异常：", e);
			return false;
		}
		return false;
	}

}