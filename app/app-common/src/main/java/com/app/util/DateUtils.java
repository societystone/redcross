package com.app.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期工具类 . <br>
 * 
 * @author wangtw <br>
 */
public final class DateUtils {

	/** 线程安全的日期格式对象-包含时分秒 */
	private static final ThreadLocal<DateFormat> DATE_FORMAT = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			// 完整日期
			return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		}

	};

	/** 线程安全的日期格式对象-年月日 */
	private static final ThreadLocal<DateFormat> YMD = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			// 年月日
			return new SimpleDateFormat("yyyy-MM-dd");
		}

	};

	/** 线程安全的日期格式对象-年月日 */
	private static final ThreadLocal<DateFormat> YYYYMMDD = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			// 年月日
			return new SimpleDateFormat("yyyyMMdd");
		}

	};

	/** 线程安全的日期格式对象-年月日 */
	private static final ThreadLocal<DateFormat> HHmmssSSS = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			// 年月日
			return new SimpleDateFormat("HHmmssSSS");
		}

	};

	/** 线程安全的日期格式对象-年月日 */
	private static final ThreadLocal<DateFormat> YYYYMMDDHHmmss = new ThreadLocal<DateFormat>() {

		@Override
		protected DateFormat initialValue() {
			// 年月日
			return new SimpleDateFormat("YYYYMMDDHHmmss");
		}

	};

	/**
	 * 私有构造函数
	 */
	private DateUtils() {
		super();
	}

	/**
	 * 格式化完整日期
	 * 
	 * @param date
	 * @return yyyy-MM-dd HH:mm:ss格式的字符串
	 */
	public static String formatDate(Date date) {
		return DATE_FORMAT.get().format(date);
	}

	/**
	 * 格式化年月日
	 * 
	 * @param date
	 * @return yyyy-MM-dd格式的字符串
	 */
	public static String formatYMD(Date date) {
		return YMD.get().format(date);
	}

	/**
	 * 格式化年月日
	 * 
	 * @param date
	 * @return yyyyMMdd格式的字符串
	 */
	public static String formatYYYYMMDD(Date date) {
		return YYYYMMDD.get().format(date);
	}

	/**
	 * 解析日期字符串
	 * 
	 * @param dateStr
	 * @return
	 */
	public static Date parseYYYYMMDD(String dateStr) {
		Date date = null;
		try {
			date = YYYYMMDD.get().parse(dateStr);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 格式化时分秒毫秒
	 * 
	 * @param date
	 * @return HHmmssSSS格式的字符串
	 */
	public static String formatHHmmssSSS(Date date) {
		return HHmmssSSS.get().format(date);
	}

	/**
	 * 格式化年月日时分秒
	 * 
	 * @param date
	 * @return YYYYMMDDHHmmss格式的字符串
	 */
	public static String formatYYYYMMDDHHmmss(Date date) {
		return YYYYMMDDHHmmss.get().format(date);
	}

	/**
	 * 获取指定日期的0点的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getZeroPointStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return formatDate(calendar.getTime());
	}

	/**
	 * 获取指定日期的末点的字符串
	 * 
	 * @param date
	 * @return
	 */
	public static String getLastPointStr(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return formatDate(calendar.getTime());
	}

	/**
	 * 获取指定日期的0点的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getZeroPointMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime().getTime();
	}

	/**
	 * 获取指定日期的末点的毫秒数
	 * 
	 * @param date
	 * @return
	 */
	public static long getLastPointMillisecond(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 23);
		calendar.set(Calendar.MINUTE, 59);
		calendar.set(Calendar.SECOND, 59);
		calendar.set(Calendar.MILLISECOND, 999);
		return calendar.getTime().getTime();
	}

	/**
	 * 日期增加天数
	 * 
	 * @param date
	 * @param day
	 * @return
	 */
	public static Date addDay(Date date, int day) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_MONTH, day);// 利用Calendar 实现 Date日期+1天
		return c.getTime();

	}

	/**
	 * 计算相差月数
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 * @throws ParseException
	 */
	public static int getMonthSpace(String date1, String date2) throws ParseException {
		int result = 0;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		Calendar c1 = Calendar.getInstance();
		Calendar c2 = Calendar.getInstance();
		c1.setTime(sdf.parse(date1));
		c2.setTime(sdf.parse(date2));
		result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
		return result == 0 ? 1 : Math.abs(result);
	}
}
