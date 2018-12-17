/*
 * 创建于 2005-7-4
 *
 * 
 */

package icbc.api;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


import sun.misc.BASE64Decoder;

import cn.com.icbc.CMS.commontools.TranslationTool;
import cn.com.infosec.icbc.ReturnValue;

/**
 ***********************************************
 * 项目名称：APIforZHEJIANG<br>
 * 包名称  ：icbc.api<br>
 * 文件名称：Eservlet.java<br>
 * 程序描述： <pre>
 *            
 *           </pre><br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-7-4<br>
 * 变更者：   <br>
 * 变更日期： <br>
 ***********************************************
 */
public class Eservlet extends HttpServlet
{
	protected void service(HttpServletRequest req, HttpServletResponse rsp)
		throws ServletException, IOException
	{
		ICBC i = ICBC.getICBC();
		String version = null;
		String tran_code = null;
		String body = null;
		String signature = null;
		String errorCode = null;

		byte[] cert = null;

		int state = 1;

		if (req.getMethod() != "POST")
		{
			rsp.setStatus(405);
			rsp.flushBuffer();
		}
		else
		{
			manage.DivScreen("", "收到 1 笔返回数据", 80);
			version = req.getParameter("Version");
			tran_code = req.getParameter("TransCode");//交易代码
			body = req.getParameter("reqData");//返回包信息
			signature = req.getParameter("PackageID");//指令包序列号

			try
			{

				cert = TranslationTool.readFile(i.getCerfile());
			}
			catch (IOException e4)
			{
				manage.DivScreen("", "无法读取工行数据层公钥文件", 80);
			}

//			try
//			{
//				message = B64Code.decode(body.toCharArray());
//			}
//			catch (Exception e)
//			{
//				manage.DivScreen("", "非法数据编码", 80);
//			}
			
			try{
				if (tran_code.equals("DZD") || tran_code.equals("ACI")){//交易签名做如下步骤
					try{
						String postbody = getstrFromBASE64(body);//首先做整字段解码
						
						int mLength = Integer
								.parseInt((postbody.substring(0, 10)).trim());
						String message = postbody.substring(10, 10 + mLength);
						String seperator = postbody.substring(10 + mLength,
								10 + mLength + 7);
						if (seperator == null || !seperator.equals("ICBCCMP")) {
							System.out.println("返回包分隔符错误");
						}
		
						byte[] crymessage = getbyteFromBASE64(postbody.substring(10 + mLength + 7));
						
						try
						{
							state =
								ReturnValue.verifySign(
									message.getBytes(),
									mLength,
									cert,
									crymessage);
						}
						catch (Exception e)
						{
							manage.DivScreen("", "无法验证工行签名", 80);
						}
						if (state != 0)
						{
							manage.DivScreen("", "非法工行签名", 80);
						}
						else
						{
							String filename = System.currentTimeMillis() + tran_code + ".xml";
							File outputfile = new File(filename);
							FileOutputStream fos = new FileOutputStream(outputfile);
							fos.write(message.getBytes());
							fos.close();
							manage.DivScreen("", "验证签名成功,返回数据记录在文件 " + filename, 80);
						}
						rsp.setStatus(200);
						rsp.flushBuffer();
					}catch(Exception e1){
						System.out.println("交易异常："+body);
						rsp.setStatus(200);
						rsp.flushBuffer();
						return;
					}
				}else{
					try{
						String postbody = getstrFromBASE64(body);//首先做整字段解码
						
						String filename = System.currentTimeMillis() + tran_code + ".xml";
						File outputfile = new File(filename);
						FileOutputStream fos = new FileOutputStream(outputfile);
						fos.write(postbody.getBytes());
						fos.close();
						manage.DivScreen("", "返回数据记录在文件 " + filename, 80);
						
						rsp.setStatus(200);
						rsp.flushBuffer();
					}catch(Exception e1){
						System.out.println("交易异常："+body);
						rsp.setStatus(200);
						rsp.flushBuffer();
						return;
					}
				}
			}catch(Exception e){
				
			}
		}
	}
		
		public static String getstrFromBASE64(String s) {
			if (s == null)
				return null;
			sun.misc.BASE64Decoder decoder = new sun.misc.BASE64Decoder();
			try {
				byte[] b = decoder.decodeBuffer(s);
				return new String(b);
			} catch (Exception e) {
				return null;
			}
		}
		/**
		 * base64解码
		 * @param s
		 * @return
		 */
		public static byte[] getbyteFromBASE64(String s) {
			if (s == null)
				return null;
			BASE64Decoder decoder = new BASE64Decoder();
			try {
				return decoder.decodeBuffer(s);
			} catch (Exception e) {
				return null;
			}
		}

}
