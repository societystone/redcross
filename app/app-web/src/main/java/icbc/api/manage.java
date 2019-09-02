/*
 * 创建于 2005-7-4
 *
 * 
 */

package icbc.api;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.com.icbc.CMS.commontools.TranslationTool;

/**
 ***********************************************
 * 项目名称：APIforZHEJIANG<br>
 * 包名称 ：icbc.api<br>
 * 文件名称：manage.java<br>
 * 程序描述：
 * 
 * <pre>
 *            专业版银企互联API，简易ERP接口演示系统
 * </pre>
 * 
 * <br>
 * 编写者 ：icbcsdc---dujh<br>
 * 编写日期 ：2005-7-4<br>
 * 变更者： <br>
 * 变更日期： <br>
 ***********************************************
 */
public class manage {
	/**
	 * 日志对象
	 */
	private static final Logger log = LoggerFactory.getLogger(manage.class);

	/**
	 * 
	 ********************************************
	 * 方法名称：DivScreen<br>
	 * 方法功能：将屏幕分屏显示<br>
	 * 
	 * @param l     左屏幕的字符串
	 * @param r     右屏幕的字符串
	 * @param width 屏幕宽度
	 ********************************************
	 */
	public static void DivScreen(String l, String r, int width) {
		String left = l;
		String right = r;
		int string_len = width / 2;
		if (width < 2 || width > 140 || width % 2 != 0) {
			throw new IllegalStateException("非法屏幕宽度");
		}

		else {
			int left_lines, right_lines;
			if (left.length() % string_len == 0) {
				left_lines = left.length() / string_len;
			} else {
				left_lines = left.length() / string_len + 1;
			}
			if (right.length() % string_len == 0) {
				right_lines = right.length() / string_len;
			} else {
				right_lines = right.length() / string_len + 1;
			}

			int max;
			if (left_lines > right_lines) {
				max = left_lines;
			} else {
				max = right_lines;
			}
			StringBuffer lsb = new StringBuffer(max * string_len);
			StringBuffer rsb = new StringBuffer(max * string_len);
			for (int i = 0; i < max * string_len; i++) {
				lsb.append(' ');
				rsb.append(' ');
			}
			lsb.replace(0, left.length(), left);
			rsb.replace(0, right.length(), right);
			for (int i = 0; i < max; i++) {
				System.out.print(lsb.substring(i * string_len, i * string_len + string_len));
				log.info(rsb.substring(i * string_len, i * string_len + string_len));
			}
		}
	}

	public static void main(String[] args) throws IOException {
		int command = 0;
		String tran_code = null;
		String file = null;
		server s = null;
		byte[] data = null;

		DivScreen("简易ERP接口演示系统 V1.0 by ICBCSDC", "", 80);
		DivScreen("命令（1send 2start 3stop 4exit）", "信息", 80);
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		do {
			DivScreen("COMMAND:", "", 80);
			command = Integer.parseInt(br.readLine());
			switch (command) {

			case 1:
				DivScreen("输入交易代码：", "", 80);
				tran_code = br.readLine();
				DivScreen("输入数据文件名及路径：", "", 80);
				file = br.readLine();
				try {
					data = null;
					data = TranslationTool.readFile(file);
				} catch (IOException e) {
					DivScreen("", "无法读取指定数据", 80);
				}
				if (data != null)
					client.send(tran_code, data);
				break;
			case 2:
				s = server.GetServer();
				s.start();
				break;
			case 3:
				s = server.GetServer();
				s.stop();
				break;
			}
		} while (command != 4);
		DivScreen("演示系统退出", "", 80);
	}
}
