package com.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.app.bankbean.BankBeanRequest;
import com.app.bankbean.BankBeanResponse;
import com.app.bankbean.pub.BankBeanPubResponse;
import com.app.bankbean.qhisd.BankBeanQhisdIn;
import com.app.bankbean.qhisd.BankBeanQhisdOut;
import com.app.bankbean.qhisd.BankBeanQhisdOutRd;
import com.app.bankbean.qpd.BankBeanQpdIn;
import com.app.bankbean.qpd.BankBeanQpdOut;
import com.app.bankbean.qpd.BankBeanQpdOutRd;
import com.app.service.BankDataService;
import com.app.util.Emptys;

import icbc.api.NCExample;

/**
 * 银行数据接口实现类 . <br>
 * 
 * @author wangtw <br>
 */
@Service
public class BankDataServiceImpl implements BankDataService {

	@Override
	public List<BankBeanQhisdOutRd> downloadHistDayBankData(String acctNo, String beginDate, String endDate) {
		// TODO Auto-generated method stub
		List<BankBeanQhisdOutRd> resultList = new ArrayList<BankBeanQhisdOutRd>();
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
			BankBeanResponse<?> bbResponse = NCExample.sendToBank(sTransCode, bbRequest);
			if (Emptys.isNotEmpty(bbResponse)) {
				BankBeanPubResponse pubResponse = bbResponse.getPub();
				if (Emptys.isNotEmpty(pubResponse) && "0".equals(pubResponse.getRetCode())) {
					BankBeanQhisdOut out = (BankBeanQhisdOut) bbResponse.getOut();
					if (Emptys.isNotEmpty(out) && out.getTotalNum() > 0) {
						nextTag = out.getNextTag();
						List<BankBeanQhisdOutRd> rds = out.getRds();
						resultList.addAll(rds);
					}
				}
			}
		} while (Emptys.isNotEmpty(nextTag));
		return resultList;
	}

	@Override
	public List<BankBeanQpdOutRd> downloadNowDayBankData(String acctNo, String beginTime, String endTime) {
		// TODO Auto-generated method stub
		List<BankBeanQpdOutRd> resultList = new ArrayList<BankBeanQpdOutRd>();
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
			BankBeanResponse<?> bbResponse = NCExample.sendToBank(sTransCode, bbRequest);
			if (Emptys.isNotEmpty(bbResponse)) {
				BankBeanPubResponse pubResponse = bbResponse.getPub();
				if (Emptys.isNotEmpty(pubResponse) && "0".equals(pubResponse.getRetCode())) {
					BankBeanQpdOut out = (BankBeanQpdOut) bbResponse.getOut();
					if (Emptys.isNotEmpty(out) && out.getTotalNum() > 0) {
						nextTag = out.getNextTag();
						List<BankBeanQpdOutRd> rds = out.getRds();
						resultList.addAll(rds);
					}
				}
			}
		} while (Emptys.isNotEmpty(nextTag));
		return resultList;
	}

}