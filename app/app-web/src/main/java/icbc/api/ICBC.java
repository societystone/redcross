/*
 * 创建于 2005-6-30
 *
 * 
 */

package icbc.api;

import java.io.IOException;

import org.jdom.Document;
import org.jdom.JDOMException;

import cn.com.icbc.CMS.commontools.TranslationTool;
import cn.com.icbc.CMS.commontools.XMLIO;
import cn.com.icbc.CMS.commontools.XpathOperater;

/**
 ***********************************************
 * 项目名称：APIforZHEJIANG<br>
 * 包名称  ：icbc.api<br>
 * 文件名称：ICBC.java<br>
 * 程序描述： <pre>
 *            工行信息，单体
 *           </pre><br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-6-30<br>
 * 变更者：   <br>
 * 变更日期： <br>
 ***********************************************
 */
public class ICBC
{
	private String ip = null;
	private String port = null;
	private String truststore = null;
	private String cerfile = null;
	private static ICBC i = null;

	private ICBC()
	{
		byte[] data = null;
		XMLIO reader = new XMLIO();
		try
		{
			data = TranslationTool.readFile("src/main/resources/para.xml");
		}
		catch (IOException e)
		{
			manage.DivScreen("","无法读取参数配置文件 para.xml",80);
		}
		try
		{
			reader.build(data);
		}
		catch (Exception e1)
		{
			manage.DivScreen("","参数文件不正确",80);
		}

		Document jdom = reader.getJdom();
		XpathOperater xo = new XpathOperater();
		try
		{
			xo.setDom(jdom);
			xo.setXpath("/paras/ICBC/IP");
			ip = xo.getNodeValue();
			xo.setXpath("/paras/ICBC/port");
			port = xo.getNodeValue();
			xo.setXpath("/paras/ICBC/commlevel/truststore");
			truststore = xo.getNodeValue();
			xo.setXpath("/paras/ICBC/datalevel/cerfile");
			cerfile = xo.getNodeValue();
		}
		catch (JDOMException e2)
		{
			manage.DivScreen("","参数不存在",80);
		}

	}

			public static void main(String[] args)
			{
				ICBC icbc= ICBC.getICBC();
				System.out.println(icbc.getCerfile());
			}

	public static ICBC getICBC()
	{
		if (i == null)
			i = new ICBC();
		return i;
	}

	/**
	 ********************************************
	 * 方法名称：getCerfile<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getCerfile()
	{
		return cerfile;
	}

	/**
	 ********************************************
	 * 方法名称：getTruststore<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getTruststore()
	{
		return truststore;
	}

	
	/**
	 ********************************************
	 * 方法名称：getIp<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getIp()
	{
		return ip;
	}

	/**
	 ********************************************
	 * 方法名称：getPort<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getPort()
	{
		return port;
	}

}
