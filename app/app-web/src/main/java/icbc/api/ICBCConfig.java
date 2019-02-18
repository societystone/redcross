/*
 * 创建于 2005-6-30
 *
 * 
 */

package icbc.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jdom.Document;
import org.jdom.Element;
import org.springframework.util.ResourceUtils;

import com.app.util.Emptys;

import cn.com.icbc.CMS.commontools.TranslationTool;
import cn.com.icbc.CMS.commontools.XMLIO;
import cn.com.icbc.CMS.commontools.XpathOperater;

/**
 ***********************************************
 * 项目名称：APIforZHEJIANG<br>
 * 包名称 ：icbc.api<br>
 * 文件名称：ICBC.java<br>
 * 程序描述：
 * 
 * <pre>
 *            工行信息，单体
 * </pre>
 * 
 * <br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-6-30<br>
 * 变更者： <br>
 * 变更日期： <br>
 ***********************************************
 */
public class ICBCConfig {
	private String NCIp;// NC IP地址
	private String NCPort;// NC HTTP服务端口号
	private String SCoding;// 编码格式
	private String SBankCode;// 银行编码
	private String SGroupCIS;// 集团CIS号
	private String SID;// 证书ID
	private List<Map<String, String>> tranCodes;// 交易码列表
	private static ICBCConfig icbc = null;

	private ICBCConfig() {
		byte[] data = null;
		XMLIO reader = new XMLIO();
		try {
//			String path = "src/main/resources/ICBC-config.xml";
			String path = ResourceUtils.getURL("classpath:/ICBC-config.xml").getPath();
			data = TranslationTool.readFile(path);
			reader.build(data);

			Document jdom = reader.getJdom();
			XpathOperater xo = new XpathOperater();
			xo.setDom(jdom);
			xo.setXpath("/config/NCIp");
			NCIp = xo.getNodeValue();
			xo.setXpath("/config/NCPort");
			NCPort = xo.getNodeValue();
			xo.setXpath("/config/SCoding");
			SCoding = xo.getNodeValue();
			xo.setXpath("/config/SBankCode");
			SBankCode = xo.getNodeValue();
			xo.setXpath("/config/SGroupCIS");
			SGroupCIS = xo.getNodeValue();
			xo.setXpath("/config/SID");
			SID = xo.getNodeValue();
			xo.setXpath("/config/TranCodes/TranCode");

			List<Element> tcs = xo.getList();
			if (Emptys.isNotEmpty(tcs)) {
				tranCodes = new ArrayList<Map<String, String>>();
				for (Element e : tcs) {
					Map<String, String> tranCodeMap = new HashMap<String, String>();
					tranCodeMap.put("code", e.getChildText("code"));
					tranCodeMap.put("version", e.getChildText("version"));
					tranCodeMap.put("name", e.getChildText("name"));
					tranCodes.add(tranCodeMap);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		ICBCConfig icbc = ICBCConfig.getICBC();
		String NCIp = icbc.getNCIp();// NC IP地址
		String NCPort = icbc.getNCPort();// NC HTTP服务端口号
		String SCoding = icbc.getSCoding();// 编码格式
		String SBankCode = icbc.getSBankCode();// 银行编码
		String SGroupCIS = icbc.getSGroupCIS();// 集团CIS号
		String SID = icbc.getSID();// 证书ID
		List<Map<String, String>> tranCodes = icbc.getTranCodes();// 交易码列表
		System.out.println(NCIp + ";" + NCPort + ";" + SCoding + ";" + SBankCode + ";" + SGroupCIS + ";" + SID);

		if (Emptys.isNotEmpty(tranCodes)) {
			for (Map<String, String> map : tranCodes) {
				System.out.println(map.get("code") + ";" + map.get("name") + ";" + map.get("version"));
			}
		}
	}

	public static ICBCConfig getICBC() {
		if (icbc == null)
			icbc = new ICBCConfig();
		return icbc;
	}

	public String getNCIp() {
		return NCIp;
	}

	public String getNCPort() {
		return NCPort;
	}

	public String getSCoding() {
		return SCoding;
	}

	public String getSBankCode() {
		return SBankCode;
	}

	public String getSGroupCIS() {
		return SGroupCIS;
	}

	public String getSID() {
		return SID;
	}

	public List<Map<String, String>> getTranCodes() {
		return tranCodes;
	}

	public static ICBCConfig getIcbc() {
		return icbc;
	}

}
