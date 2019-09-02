package com.app.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.bankbean.BankBeanRequest;
import com.app.bankbean.BankBeanResponse;
import com.app.bankbean.pub.BankBeanPubRequest;
import com.app.bankbean.qpd.BankBeanQpdIn;
import com.app.bankbean.qpd.BankBeanQpdOut;
import com.app.util.JaxbUtil;
import com.app.util.MD5Utils;

/**
 * 测试基类 . <br>
 * SpringBootTest.WebEnvironment.RANDOM_POR 表示使用随机端口号
 * 
 * @author wangtw <br>
 */
@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Ignore
public class BaseTest {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

	public static void main(String[] args) {
		log.info("=================javabean to xml=============");
		BankBeanPubRequest bbpr = new BankBeanPubRequest();
		bbpr.setTransCode("0000");
		bbpr.setBankCode("1111");
		BankBeanQpdIn bbi = new BankBeanQpdIn();
		bbi.setAccNo("1234567890");
		bbi.setAreaCode("10");
		BankBeanRequest<BankBeanQpdIn> bbRequest = new BankBeanRequest<BankBeanQpdIn>(bbpr, bbi);
		String xml = JaxbUtil.convertToXml(BankBeanRequest.class, bbRequest);
		log.info(xml);

		log.info("=================xml to javabean=============");
		String xmlResponse = "<?xml version=\"1.0\" encoding = \"GBK\"?>\r\n" + "<CMS>\r\n" + "<eb>\r\n"
				+ "    <pub>\r\n" + "        <TransCode>0000</TransCode>\r\n" + "        <BankCode>1111</BankCode>\r\n"
				+ "    </pub>\r\n" + "    <out>\r\n" + "        <AccNo>1234567890</AccNo>\r\n"
				+ "        <AreaCode>10</AreaCode>\r\n" + "        <rd>\r\n" + "            <Drcrf>1</Drcrf>\r\n"
				+ "            <VouhNo>2343264234</VouhNo>\r\n" + "        </rd>\r\n" + "        <rd>\r\n"
				+ "            <Drcrf>2</Drcrf>\r\n" + "            <VouhNo>867867867</VouhNo>\r\n"
				+ "        </rd>\r\n" + "    </out>\r\n" + "</eb>\r\n" + "</CMS>";
		@SuppressWarnings("unchecked")
		BankBeanResponse<BankBeanQpdOut> bbResponse = JaxbUtil.convertToJavaBean(BankBeanResponse.class, xmlResponse);
		log.info(bbResponse.getPub().getBankCode());
		log.info(bbResponse.getOut().getAccNo());

		log.info(MD5Utils.MD5("admin"));
		log.info(MD5Utils.MD5("csshi"));
		log.info(MD5Utils.MD5("cs1"));
		log.info(MD5Utils.MD5("cs2"));
		log.info(MD5Utils.MD5("cs3"));
		log.info(MD5Utils.MD5("cs4"));
		log.info(MD5Utils.MD5("cs5"));
		log.info(MD5Utils.MD5("cs6"));
		log.info(MD5Utils.MD5("cs7"));
		log.info(MD5Utils.MD5("cs8"));
		log.info(MD5Utils.MD5("cs9"));
		log.info(MD5Utils.MD5("cs10"));
		log.info(MD5Utils.MD5("cs11"));
	}
}
