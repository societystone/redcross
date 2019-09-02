package com.app.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.app.bankbean.BankBeanRequest;
import com.app.bankbean.pub.BankBeanPubResponse;
import com.app.bankbean.qaccbal.BankBeanQaccbalIn;
import com.app.bankbean.qaccbal.BankBeanQaccbalInRd;
import com.app.bankbean.qaccbal.BankBeanQaccbalOut;
import com.app.bankbean.qaccbal.BankBeanQaccbalOutRd;
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
import com.app.util.ExceptionUtil;

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
	public List<BankBeanQhisdOutRd> downloadHistDayBankData(String acctNo, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		List<BankBeanQhisdOutRd> resultList = new ArrayList<BankBeanQhisdOutRd>();
		String systemTranDate = Config.systemTranDate;
		if (Config.isEnableSystemTranDate) {
			systemTranDate = DateUtils.formatYYYYMMDD(new Date());
		}
		String nextTag = "";
		do {
			String sTransCode = "QHISD";// 交易代码
			BankBeanQhisdIn bbi = new BankBeanQhisdIn();
			bbi.setAccNo(acctNo);
			bbi.setMinAmt("0");
			bbi.setMaxAmt("0");
			bbi.setBeginDate(beginDate);
			bbi.setEndDate(endDate);
			bbi.setNextTag(nextTag);
			BankBeanRequest<BankBeanQhisdIn> bbRequest = new BankBeanRequest<BankBeanQhisdIn>(null, bbi);
			BankBeanResponseQhisd bbResponse = NCExample.sendToBank(BankBeanResponseQhisd.class, sTransCode, bbRequest,
					systemTranDate);
			String retCode = "";
			if (Emptys.isNotEmpty(bbResponse)) {
				BankBeanPubResponse pubResponse = bbResponse.getPub();
				if (Emptys.isNotEmpty(pubResponse)) {
					retCode = pubResponse.getRetCode();
					log.info("查询参数：{}--{}--{}，工行返回：{}--{}", acctNo, beginDate, endDate, retCode,
							pubResponse.getRetMsg());
					if ("0".equals(retCode)) {
						BankBeanQhisdOut out = (BankBeanQhisdOut) bbResponse.getOut();
						if (Emptys.isNotEmpty(out) && out.getTotalNum() > 0) {
							nextTag = out.getNextTag();
							List<BankBeanQhisdOutRd> rds = out.getRds();
							resultList.addAll(rds);
						}
					}
				}
			}
			if (!"0".equals(retCode)) {
				ExceptionUtil.throwCheckException("查询交易流水不成功");
			}
		} while (Emptys.isNotEmpty(nextTag));
		return resultList;
	}

	@Override
	public List<BankBeanQpdOutRd> downloadNowDayBankData(String acctNo, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		List<BankBeanQpdOutRd> resultList = new ArrayList<BankBeanQpdOutRd>();
		String systemTranDate = Config.systemTranDate;
		if (Config.isEnableSystemTranDate) {
			systemTranDate = DateUtils.formatYYYYMMDD(new Date());
		}
		String nextTag = "";
		do {
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
			String retCode = "";
			if (Emptys.isNotEmpty(bbResponse)) {
				BankBeanPubResponse pubResponse = bbResponse.getPub();
				if (Emptys.isNotEmpty(pubResponse)) {
					retCode = pubResponse.getRetCode();
					log.info("查询参数：{}--{}--{}，工行返回：{}--{}", acctNo, beginTime, endTime, retCode,
							pubResponse.getRetMsg());
					if ("0".equals(retCode)) {
						BankBeanQpdOut out = (BankBeanQpdOut) bbResponse.getOut();
						if (Emptys.isNotEmpty(out) && out.getTotalNum() > 0) {
							nextTag = out.getNextTag();
							List<BankBeanQpdOutRd> rds = out.getRds();
							resultList.addAll(rds);
						}
					}
				}
			}
			if (!"0".equals(retCode)) {
				ExceptionUtil.throwCheckException("查询交易流水不成功");
			}
		} while (Emptys.isNotEmpty(nextTag));
		return resultList;
	}

	@Override
	public List<BankBeanQaccbalOutRd> queryAcctBal(String acctNo) {
		// TODO Auto-generated method stub
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
		String retCode = "";
		if (Emptys.isNotEmpty(bbResponse)) {
			BankBeanPubResponse pubResponse = bbResponse.getPub();
			if (Emptys.isNotEmpty(pubResponse)) {
				retCode = pubResponse.getRetCode();
				log.info("查询参数：{}，工行返回：{}--{}", acctNo, retCode, pubResponse.getRetMsg());
				if ("0".equals(retCode)) {
					BankBeanQaccbalOut out = (BankBeanQaccbalOut) bbResponse.getOut();
					if (Emptys.isNotEmpty(out)) {
						return out.getRds();
					}
				}
			}
		}
		if (!"0".equals(retCode)) {
			ExceptionUtil.throwCheckException("查询账户余额不成功");
		}
		return null;
	}

}