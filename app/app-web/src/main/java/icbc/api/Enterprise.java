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
 * 文件名称：Enterprise.java<br>
 * 程序描述： <pre>
 *            企业信息，单体
 *           </pre><br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-6-30<br>
 * 变更者：   <br>
 * 变更日期： <br>
 ***********************************************
 */
public class Enterprise
{
	private String name = null;
	private String bankcode = null;
	private String cis = null;
	private String port = null;
	private String store = null;
	private String storepass = null;
	private String cerfile = null;
	private String key = null;
	private String keypass = null;
	private static Enterprise e = null;

	private Enterprise()
	{
		byte[] data = null;
		XMLIO reader = new XMLIO();
		try
		{
			data = TranslationTool.readFile("para.xml");
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
			xo.setXpath("/paras/name");
			name = xo.getNodeValue();
			xo.setXpath("/paras/bankcode");
			bankcode = xo.getNodeValue();
			xo.setXpath("/paras/CIS");
			cis = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/port");
			port = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/commlevel/store");
			store = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/commlevel/storepass");
			storepass = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/datalevel/cerfile");
			cerfile = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/datalevel/key");
			key = xo.getNodeValue();
			xo.setXpath("/paras/enterprise/datalevel/keypass");
			keypass = xo.getNodeValue();
		}
		catch (JDOMException e2)
		{
			manage.DivScreen("","参数不存在",80);
		}

	}

	//		public static void main(String[] args)
	//		{
	//			
	//		}

	public static Enterprise getEnterprise()
	{
		if (e == null)
			e = new Enterprise();
		return e;
	}

	/**
	 ********************************************
	 * 方法名称：getBankcode<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getBankcode()
	{
		return bankcode;
	}

	/**
	 ********************************************
	 * 方法名称：getCis<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getCis()
	{
		return cis;
	}

	/**
	 ********************************************
	 * 方法名称：getName<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getName()
	{
		return name;
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
	 * 方法名称：getKey<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getKey()
	{
		return key;
	}

	/**
	 ********************************************
	 * 方法名称：getKeypass<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getKeypass()
	{
		return keypass;
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

	/**
	 ********************************************
	 * 方法名称：getStore<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getStore()
	{
		return store;
	}

	/**
	 ********************************************
	 * 方法名称：getStorepass<br>
	 * 方法功能：<br>
	 * @return
	 ********************************************
	 */
	public String getStorepass()
	{
		return storepass;
	}

}
