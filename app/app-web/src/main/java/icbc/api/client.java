/*
 * 创建于 2005-6-28
 *
 * 
 */

package icbc.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import cn.com.icbc.CMS.commontools.TranslationTool;

import cn.com.infosec.icbc.ReturnValue;

import org.apache.commons.httpclient.HttpConnection;
import org.apache.commons.httpclient.HttpState;
import org.apache.commons.httpclient.contrib.ssl.AuthSSLProtocolSocketFactory;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.protocol.Protocol;
;

/**
 ***********************************************
 * 项目名称：APIforZHEJIANG<br>
 * 包名称  ：icbc.api<br>
 * 文件名称：client.java<br>
 * 程序描述： <pre>
 *            专业版银企互联API，客户端
 * 			  用于企业向银行发数据
 *           </pre><br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-6-28<br>
 * 变更者：   <br>
 * 变更日期： <br>
 ***********************************************
 */
public class client
{
	public static void send(String t_cdoe, byte[] data)
	{
		Enterprise e = Enterprise.getEnterprise();
		ICBC i = ICBC.getICBC();

		String version = "0.0.0.1";
		String bank_code = e.getBankcode();
		String group_code = e.getCis();
		byte[] signature = null;
		String certificatename = null;
		byte[] certificate = null;
		String tran_code = t_cdoe;
		String id = "test.y.0200";//请根据实际修改证书名称
		String body = new String(data);
		boolean signflag = true;//交易是否需要签名，根据实际交易修改或者配置
		
		byte[] key = null;
		try
		{
			key = TranslationTool.readFile(e.getKey());
		}
		catch (IOException e2)
		{
			manage.DivScreen("","无法读取企业数据层私钥文件",80);
			return;
		}
		char[] password = e.getKeypass().toCharArray();
		try
		{
			signature = ReturnValue.sign(data, data.length, key, password);
		}
		catch (Exception e3)
		{
			manage.DivScreen("","无法生成签名",80);
			return;
		}
		
		try
		{
			certificatename = e.getCerfile();
		}
		catch (Exception e4)
		{
			manage.DivScreen("","没有配置企业数据层公钥文件",80);
			return;
		}
		
		try
		{
			FileInputStream fii = new FileInputStream(new File(e.getCerfile()));
			certificate = new byte[fii.available()];
			fii.read(certificate);//私钥
			fii.close();
		}
		catch (IOException e4)
		{
			manage.DivScreen("","无法读取配置企业数据层公钥文件",80);
			return;
		}
		
		PostMethod mypost = new PostMethod();
		mypost.addParameter("Version", version);
		mypost.addParameter("GroupCIS", group_code);
		mypost.addParameter("BankCode", bank_code);
		mypost.addParameter("TransCode", tran_code);
		
		if (signflag){//需要签名做以下操作，否则放明文
			String length = String.valueOf(body.length());
			if (length.length() <= 10)
				length = ("0000000000").substring(0,10-length.length())+ length;//长度补齐10位，左补0
			body = length + body + "ICBCCMP" + getrevFromBASE64(signature);
			
		}
		
		mypost.addParameter("reqData", getrevFromBASE64(body.getBytes()));//请根据实际修改上送包xml数据
		mypost.addParameter("ID", id);
		mypost.addParameter("PackageID", "200212230000001");//要求永远不能重复,可以参考接口文档的说明
		mypost.addParameter("Cert", getrevFromBASE64(certificate));
		
		try
		{
			Protocol myhttps =
				new Protocol(
					"https",
					new AuthSSLProtocolSocketFactory(
						null,
						null,
						new URL("file:" + i.getTruststore()),
						null),
					443);
			HttpConnection myconn =
				new HttpConnection(i.getIp(), Integer.parseInt(i.getPort()), myhttps);
			int re_code = mypost.execute(new HttpState(), myconn);
			if (re_code==200)
			{
				manage.DivScreen("","已成功发送一笔 "+t_cdoe,80);
				String repMsg = mypost.getResponseBodyAsString();
				manage.DivScreen("","已接收到返回信息 "+repMsg,80);
			}
			else
			{
				manage.DivScreen("","发送失败，http错误码"+re_code,80);
			}
		}
		catch (MalformedURLException e1)
		{
			manage.DivScreen("","无法读取工行通讯层证书",80);
		}
		catch (Exception e1)
		{
			manage.DivScreen("","通讯异常",80);
		}
	}
	
	public static String getrevFromBASE64(byte[] s) {
		if (s == null)
			return null;
		sun.misc.BASE64Encoder encoder = new sun.misc.BASE64Encoder();
		try {
			return encoder.encode(s);
		} catch (Exception e) {
			return null;
		}
	}

//		public static void main(String[] args)
//		{
//			try
//			{
//				byte []data=TranslationTool.readFile("E:\\BICE_SERVER_CMS\\testdata\\shell_PFB.xml");
//				client.send("PFB",data);
//			}
//			catch (IOException e)
//			{
//				manage.DivScreen("","无法读取指定数据",80);
//			}
//			
//		}
}
