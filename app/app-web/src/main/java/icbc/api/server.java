/*
 * 创建于 2005-7-4
 *
 * 
 */

package icbc.api;

import org.mortbay.http.HttpContext;
import org.mortbay.http.HttpServer;
import org.mortbay.http.SunJsseListener;
import org.mortbay.jetty.servlet.ServletHandler;
import org.mortbay.util.InetAddrPort;

/**
 ***********************************************
 * 项目名称：APIforZHEJIANG<br>
 * 包名称  ：icbc.api<br>
 * 文件名称：server.java<br>
 * 程序描述： <pre>
 *            专业版银企互联API，服务端
 * 			  用于接收银行返回的信息  
 *           </pre><br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-7-4<br>
 * 变更者：   <br>
 * 变更日期： <br>
 ***********************************************
 */
public class server
{
	private static server HTTPSERVER = null;
	private HttpServer server = null;

	private server()
	{
		server = new HttpServer();
	}

	public static server GetServer()
	{
		if (HTTPSERVER == null)
		{
			HTTPSERVER = new server();
		}
		return HTTPSERVER;
	}

	public void stop()
	{
		try
		{
			server.stop();
			manage.DivScreen("","服务端关闭",80);
		}
		catch (InterruptedException e)
		{
			manage.DivScreen("","关闭服务端异常",80);
		}
	}
	/**
	 * 
	 ********************************************
	 * 方法名称：start<br>
	 * 方法功能：服务端起动<br>
	 * 
	 ********************************************
	 */
	public void start()
	{
		Enterprise e = Enterprise.getEnterprise();

		try
		{
			SunJsseListener safelistener =
				new SunJsseListener(new InetAddrPort(Integer.parseInt(e.getPort())));
			safelistener.setKeystore(e.getStore());
			safelistener.setKeyPassword(e.getStorepass());
			safelistener.setPassword(e.getStorepass());
			server.addListener(safelistener);
			manage.DivScreen("",e.getPort()+"端口打开",80);

			HttpContext context = new HttpContext();
			context.setContextPath("/");
			server.addContext(context);
			ServletHandler servlets = new ServletHandler();
			context.addHandler(servlets);

			servlets.addServlet("/*", "icbc.api.Eservlet");
			server.start();
			manage.DivScreen("","服务端启动完成",80);
		}
		catch (Exception e1)
		{
			manage.DivScreen("","服务端启动异常",80);
		}
	}

}
