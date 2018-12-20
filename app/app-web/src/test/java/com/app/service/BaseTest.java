package com.app.service;

import org.junit.Ignore;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import com.app.bankbean.BankBean01In;
import com.app.bankbean.BankBean01Out;
import com.app.bankbean.BankBeanPubRequest;
import com.app.bankbean.BankBeanRequest;
import com.app.bankbean.BankBeanResponse;
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
	public static void main(String[] args) {
		System.out.println("=================javabean to xml=============");
		BankBeanPubRequest bbpr = new BankBeanPubRequest();
		bbpr.setTransCode("0000");
		bbpr.setBankCode("1111");
		BankBean01In bbi = new BankBean01In();
		bbi.setAccNo("1234567890");
		bbi.setAreaCode("10");
		BankBeanRequest<BankBean01In> bbRequest = new BankBeanRequest<BankBean01In>(bbpr, bbi);
		String xml = JaxbUtil.convertToXml(BankBeanRequest.class, bbRequest);
		System.out.println(xml);

		System.out.println("=================xml to javabean=============");
		String xmlResponse = "<?xml version=\"1.0\" encoding = \"GBK\"?>\r\n" + "<CMS>\r\n" + "<eb>\r\n"
				+ "    <pub>\r\n" + "        <TransCode>0000</TransCode>\r\n" + "        <BankCode>1111</BankCode>\r\n"
				+ "    </pub>\r\n" + "    <out>\r\n" + "        <AccNo>1234567890</AccNo>\r\n"
				+ "        <AreaCode>10</AreaCode>\r\n" + "        <rd>\r\n" + "            <Drcrf>1</Drcrf>\r\n"
				+ "            <VouhNo>2343264234</VouhNo>\r\n" + "        </rd>\r\n" + "        <rd>\r\n"
				+ "            <Drcrf>2</Drcrf>\r\n" + "            <VouhNo>867867867</VouhNo>\r\n"
				+ "        </rd>\r\n" + "    </out>\r\n" + "</eb>\r\n" + "</CMS>";
		@SuppressWarnings("unchecked")
		BankBeanResponse<BankBean01Out> bbResponse = JaxbUtil.convertToJavaBean(BankBeanResponse.class, xmlResponse);
		System.out.println(bbResponse.getPub().getBankCode());
		System.out.println(bbResponse.getOut().getAccNo());

		System.out.println(MD5Utils.MD5("admin"));
		System.out.println(MD5Utils.MD5("csshi"));
		System.out.println(MD5Utils.MD5("cs1"));
		System.out.println(MD5Utils.MD5("cs2"));
		System.out.println(MD5Utils.MD5("cs3"));
		System.out.println(MD5Utils.MD5("cs4"));
		System.out.println(MD5Utils.MD5("cs5"));
		System.out.println(MD5Utils.MD5("cs6"));
		System.out.println(MD5Utils.MD5("cs7"));
		System.out.println(MD5Utils.MD5("cs8"));
		System.out.println(MD5Utils.MD5("cs9"));
		System.out.println(MD5Utils.MD5("cs10"));
		System.out.println(MD5Utils.MD5("cs11"));
		System.out.println("2018-02".compareTo("2018-01"));
	}
}
