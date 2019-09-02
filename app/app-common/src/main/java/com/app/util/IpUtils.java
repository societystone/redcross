package com.app.util;

import javax.servlet.http.HttpServletRequest;

/**
 * 获取客户端IP地址 . <br>
 * 
 * @author wangtw <br>
 */
public final class IpUtils {

	/**
	 * 私有构造函数
	 */
	private IpUtils() {
		super();
	}

	/**
	 * 获取客户端ip地址
	 * 
	 * @param request
	 * @return String
	 */
	public static String getCliectIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		String unknownStr = "unknown";
		if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || ip.length() == 0 || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
			ip = request.getHeader("HTTP_CLIENT_IP");
		}
		if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
			ip = request.getHeader("HTTP_X_FORWARDED_FOR");
		}
		if (StringUtils.isEmpty(ip) || StringUtils.equalsIgnoreCase(unknownStr, ip)) {
			ip = request.getRemoteAddr();
		}

		// 多个路由时,取第一个非unknown的ip
		final String[] arr = ip.split(",");
		for (final String str : arr) {
			if (!StringUtils.equalsIgnoreCase(ip, unknownStr)) {
				ip = str;
				break;
			}
		}
		return ip;
	}

}
