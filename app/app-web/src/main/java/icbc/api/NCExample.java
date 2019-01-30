package icbc.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;

import com.app.bankbean.BankBeanRequest;
import com.app.bankbean.BankBeanResponse;
import com.app.bankbean.pub.BankBeanPubRequest;
import com.app.bankbean.qaccbal.BankBeanQaccbalIn;
import com.app.bankbean.qaccbal.BankBeanQaccbalInRd;
import com.app.util.DateUtils;
import com.app.util.Emptys;
import com.app.util.JaxbUtil;

import cn.com.infosec.icbc.ReturnValue;
import sun.misc.BASE64Decoder;

public class NCExample {

	public static void main(String[] args) throws Exception {
//		String sTransCode = "QPD";// 交易代码
//		BankBeanQpdIn bbi = new BankBeanQpdIn();
//		bbi.setAccNo("4402263029100139502");
//		bbi.setMinAmt("0");
//		bbi.setMaxAmt("0");
//		BankBeanRequest<BankBeanQpdIn> bbRequest = new BankBeanRequest<BankBeanQpdIn>(null, bbi);
//		BankBeanResponse bbResponse = NCExample.sendToBank(sTransCode, bbRequest);
//		System.out.println(bbResponse.getPub().getRetMsg());

		String[] accNoArr = new String[] { "4402263009100188886", "4402263029105888858", "4402263029100139502" };
		String sTransCode = "QACCBAL";// 交易代码
		BankBeanQaccbalIn bbi = new BankBeanQaccbalIn();
		bbi.setTotalNum(Integer.toString(accNoArr.length));
		List<BankBeanQaccbalInRd> rds = new ArrayList<BankBeanQaccbalInRd>();
		int index = 0;
		for (String accNo : accNoArr) {
			BankBeanQaccbalInRd inRd = new BankBeanQaccbalInRd();
			inRd.setISeqno(Integer.toString(++index));
			inRd.setAccNo(accNo);
			rds.add(inRd);
		}
		bbi.setRds(rds);
		BankBeanRequest<BankBeanQaccbalIn> bbRequest = new BankBeanRequest<BankBeanQaccbalIn>(null, bbi);
		BankBeanResponse bbResponse = NCExample.sendToBank(sTransCode, bbRequest);
		System.out.println(bbResponse.getPub().getRetMsg());
	}

	/**
	 * 往银行发送请求，获取数据
	 * 
	 * @param tranCode        交易代码
	 * @param bankBeanRequest 请求实体
	 * @return
	 */
	public static BankBeanResponse<?> sendToBank(String tranCode, BankBeanRequest<?> bankBeanRequest) {
		try {
			ICBCConfig icbc = ICBCConfig.getICBC();
			String NCIp = icbc.getNCIp();// NC IP地址
			String NCPort = icbc.getNCPort();// NC HTTP服务端口号
			String SCoding = icbc.getSCoding();// 编码格式
			String SBankCode = icbc.getSBankCode();// 银行编码
			String SGroupCIS = icbc.getSGroupCIS();// 集团CIS号
			String SID = icbc.getSID();// 证书ID
			List<Map<String, String>> tranCodes = icbc.getTranCodes();// 交易码列表
			String cmpVersion = "0.0.1.0";// 默认版本
			if (Emptys.isNotEmpty(tranCodes)) {
				for (Map<String, String> map : tranCodes) {
					if (tranCode.equals(map.get("code"))) {
						cmpVersion = map.get("version");
						break;
					}
				}
			}

			Date nowDate = new Date();
			String nowDateStr = DateUtils.formatYYYYMMDD(nowDate);
			String tranDate = "20190228";
			String tranTime = DateUtils.formatHHmmssSSS(nowDate);
			// 下面字段与明文xml包中保持一致
			String sPackageID = "PACKAGE" + nowDateStr + tranTime;// 包序列号与xml包中保持一致

			BankBeanPubRequest bbpr = new BankBeanPubRequest();
			bbpr.setTransCode(tranCode);
			bbpr.setCIS(SGroupCIS);
			bbpr.setBankCode(SBankCode);
			bbpr.setID(SID);
			bbpr.setTranDate(tranDate);
			bbpr.setTranTime(tranTime);
			bbpr.setFSeqno(sPackageID);
			bankBeanRequest.setPub(bbpr);
			String sContent = JaxbUtil.convertToXml(BankBeanRequest.class, bankBeanRequest);
			System.out.println(sContent);

			String sendTime = DateUtils.formatYYYYMMDDHHmmss(nowDate);

//			String urlStr = "http://" + NCIp + ":" + NCPort + "/servlet/ICBCCMPAPIReqServlet?userID=" + SID
//					+ "&PackageID=" + sPackageID + "&SendTime=" + sendTime;
			String urlStr = NCIp + "/servlet/ICBCCMPAPIReqServlet?userID=" + SID + "&PackageID=" + sPackageID
					+ "&SendTime=" + sendTime;

			System.out.println("url==" + urlStr);

			HttpClient client = new HttpClient(); // 构建http客户端
			PostMethod post = new PostMethod(urlStr); // 构建http post方法
			post.setRequestHeader("Content-Type", "application/x-www-form-urlencoded;charset=GBK");

			post.addParameter("Version", cmpVersion);
			post.addParameter("TransCode", tranCode);
			post.addParameter("BankCode", SBankCode);
			post.addParameter("GroupCIS", SGroupCIS);
			post.addParameter("ID", SID);
			post.addParameter("PackageID", sPackageID);
			post.addParameter("Cert", "");
			post.addParameter("reqData", sContent);

			System.out.println("开始发送。。。");
			int returnFlag = client.executeMethod(post); // 获得http返回码
			System.out.println("发送成功。。。，HTTP响应状态：" + returnFlag);
			try {
				String postResult = post.getResponseBodyAsString();

				String responseXml = "";
				if (postResult.startsWith("reqData=")) {
					postResult = postResult.substring(8);
					System.out.println("retMessage==" + new String(postResult));
					byte[] decodeResult = getFromBASE64(postResult);
					responseXml = new String(decodeResult, SCoding);
				} else {
					responseXml = new String(postResult);
				}
				System.out.println("******************************银企互联返回数据******************************\n");
				System.out.println(responseXml);
				return JaxbUtil.convertToJavaBean(BankBeanResponse.class, responseXml);
			} catch (Exception e) {
				e.printStackTrace();
			}
			post.releaseConnection(); // 释放http连接

		} catch (IOException e) {
			e.printStackTrace();
			e.printStackTrace(System.out);
		} catch (Exception e) {
			e.printStackTrace();
			e.printStackTrace(System.out);
			System.err.println("error:" + e.getMessage());
		}
		return null;
	}

	public static byte[] getFromBASE64(String s) {
		if (s == null)
			return null;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			return decoder.decodeBuffer(s);
		} catch (Exception e) {
			return null;
		}
	}

	public static int verifySign(String message, byte[] signinfo, int mlength, String certpath) {
		try {
			FileInputStream fii = new FileInputStream(new File(certpath));
			byte[] bPubCert = new byte[fii.available()];
			fii.read(bPubCert);// 公钥
			return ReturnValue.verifySign(message.getBytes(), mlength, bPubCert, ReturnValue.base64dec(signinfo));
		} catch (InvalidKeyException e) {
			System.out.println("InvalidKeyException:" + e);
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			System.out.println("NoSuchAlgorithmException:" + e);
			e.printStackTrace();
		} catch (SignatureException e) {
			System.out.println("SignatureException:" + e);
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("IOException:" + e);
			e.printStackTrace();
		}
		return -1;
	}
}
