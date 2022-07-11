package com.xinyao.util;

import org.apache.commons.lang3.time.DateUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;


/**
 * @author 栗梧桐 日期类型处理类 2019-3-12 下午1:19:35
 */
public class DateUtil extends DateUtils {

	public static final String FORMAT_DATETIME = "yyyy-MM-dd HH:mm:ss";

	public static final String FORMAT_DATE = "yyyy-MM-dd";

	public static final String FORMAT_YYYYMMDD = "yyyyMMdd";

	private static SimpleDateFormat SDF;


	/**
	 * 获取当前年月日 yyyyMMdd
	 * @return 交易日
	 */
	public static String getTransactionDate(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_YYYYMMDD);
		return formatter.format(LocalDateTime.now());
	}

	/**
	 * 获取当前年月日-1 yyyyMMdd
	 * @return 交易日
	 */
	public static String getTheDayBeforeTransactionDate(){
		//获取当前日期-1
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Calendar c = Calendar.getInstance();
		c.setTime(new Date());
		c.add(Calendar.DATE, -1);
		Date start = c.getTime();
		return format.format(start);
	}

	/**
	 * 获取当前年月日+1 yyyyMMdd
	 * @return 交易日
	 */
	public static String getTheDayAfterTransactionDate(String dateTime){
		//获取当前日期-1
		SimpleDateFormat format = new SimpleDateFormat(FORMAT_YYYYMMDD);
		Date date = null;
		try {
			 date = format.parse(dateTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DATE, +1);
		Date start = c.getTime();
		return format.format(start);
	}

	/**
	 * 获取当前日期
	 * @return
	 */
	public static String getNowDate(){
		return LocalDate.now().toString();
	}

	/**
	 * 获取当前年月日 yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static String getNowDateTime(){
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMAT_DATETIME);
		return formatter.format(LocalDateTime.now());
	}



	/**
	 * getDate() method.
	 * 
	 * @date 2014-1-20 下午1:27:29
	 *       <p>
	 *       转换字符串为指定格式日期时间
	 *       </p>
	 * @param dtstring
	 *            字符型日期
	 * @param pattern
	 *            需要转换的格式
	 * @return Date 转换后的日期
	 */
	public static Date getDate(String dtstring, String pattern) {
		int pmIndex = dtstring.toUpperCase().indexOf("PM");
		int amIndex = dtstring.toUpperCase().indexOf("AM");
		int aIndex = pattern.indexOf("a");

		if ((pmIndex >= 0 || amIndex >= 0) && aIndex == -1) {
			if (pattern.indexOf("HH:mm:ss") != -1) {

				pattern = pattern.replaceAll("HH:mm:ss", "hh:mm:ss a");
			} else {
				pattern = pattern.replaceAll("hh:mm:ss", "hh:mm:ss a");
			}
		}

		if (pmIndex == -1 && amIndex == -1 && aIndex >= 0) {
			pattern = pattern.replaceAll("hh:mm:ss a", "HH:mm:ss");
		}

		SDF = new SimpleDateFormat(pattern, Locale.getDefault());
		Date date = null;
		try {
			date = SDF.parse(dtstring);
		} catch (Exception e) {
		}

		if (date == null) {
			try {
				SDF = new SimpleDateFormat(pattern, Locale.US);
				date = SDF.parse(dtstring);
			} catch (Exception e) {
			}
		}

		if (date == null) {
			try {
				SDF = new SimpleDateFormat(pattern, Locale.CHINA);
				date = SDF.parse(dtstring);
			} catch (Exception e) {
			}
		}

		if (amIndex >= 0) {// 解决 12:15 AM 类似问题, 应该是 00:15 AM
			Calendar cal = Calendar.getInstance();
			cal.setTime(date);
			if (cal.get(Calendar.HOUR_OF_DAY) == 12) {
				cal.add(Calendar.HOUR_OF_DAY, -12);
				date = cal.getTime();
			}
		}
		return date;
	}
	/**
	 * @Description <p style="color:blue">
	 *  long转为date
	 * </p>
	 * @Since 2016年11月29日上午10:09:51
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	public static String formatDateFromLong(long time,String pattern){
		return convertString(new Date(time),pattern);
	}
	/**
	 * convertString() method.
	 * 
	 * @date 2014-1-20 下午1:57:06
	 *       <p>
	 *       转换日期类型为指定格式字符串
	 *       </p>
	 * @param date
	 *            日期
	 * @param pattern
	 *            需要转换的格式
	 * @return String 转换后的日期字符串
	 */
	public static String convertString(Date date, String pattern) {
		SDF = new SimpleDateFormat(pattern);
		try {
			return SDF.format(date);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

	/**
	 * getDateTimeByString() method.
	 * 
	 * @date 2014-1-20 下午1:41:51
	 *       <p>
	 *       日期转换
	 *       </p>
	 * @param dtstring
	 * @return Date 返回带时分秒的日期
	 */
	public static Date getDateTimeByString(String dtstring) {
		return getDate(dtstring, FORMAT_DATETIME);
	}

	/**
	 * getDateByString() method.
	 * 
	 * @date 2014-1-20 下午1:44:01
	 *       <p>
	 *       日期转换
	 *       </p>
	 * @param dtstring
	 * @return Date 只返回年月日
	 */
	public static Date getDateByString(String dtstring) {
		if (dtstring == null) {
			return null;
		}
		return getDate(dtstring, FORMAT_DATE);
	}

	/**
	 * getStringByDateTime() method.
	 * 
	 * @date 2014-1-20 下午1:58:29
	 *       <p>
	 *       日期转换
	 *       </p>
	 * @param date
	 * @return String 返回包含时分秒的字符串日期
	 */
	public static String getStringByDateTime(Date date) {
		return convertString(date, FORMAT_DATETIME);
	}

	/**
	 * getStringByDate() method.
	 * 
	 * @date 2014-1-20 下午1:59:07
	 *       <p>
	 *       日期转换
	 *       </p>
	 * @param date
	 * @return
	 * @return String 仅返回年月日字符串
	 */
	public static String getStringByDate(Date date) {
		return convertString(date, FORMAT_DATE);
	}

	/**
	 * getStringByDate() method.
	 * 
	 * @date 2014-9-4 下午3:36:57
	 *       <p>
	 *       转换日期为指定格式字符串
	 *       </p>
	 * @param date
	 * @param pattern
	 * @return
	 * @return String
	 */
	public static String getStringByDate(Date date, String pattern) {
		return convertString(date, pattern);
	}

	/**
	 * getDateByDate() method.
	 * 
	 * @date 2014-1-20 下午1:47:22
	 *       <p>
	 *       日期转换
	 *       </p>
	 * @param day
	 *            日期类型
	 * @return Date 只保留年月日
	 */
	public static Date getDateByDateTime(Date day) {
		return truncate(day, Calendar.DAY_OF_MONTH);
	}

	/**
	 * calculateSeconds() method.
	 * 
	 * @date 2014-1-20 下午3:46:29
	 *       <p>
	 *       计算两个时间相差的秒
	 *       </p>
	 * @param sDay
	 *            起码日期
	 * @param eDay
	 *            结束日期
	 * @return int
	 */
	public static int calculateSeconds(Date sDay, Date eDay) {
		return minusDateSeconds(sDay.getTime(), eDay.getTime());
	}

	public static int calculateSeconds(Calendar sDay, Calendar eDay) {
		return minusDateSeconds(sDay.getTimeInMillis(), eDay.getTimeInMillis());
	}

	private static int minusDateSeconds(Long startSeconds, Long endSeconds) {
		return (int) ((endSeconds - startSeconds) / 1000);
	}

	/**
	 * calculateMintues() method.
	 * 
	 * @date 2014-1-20 下午4:06:11
	 *       <p>
	 *       计算两个时间相差的分钟
	 *       </p>
	 * @param sDay
	 * @param eDay
	 * @return double
	 */
	public static double calculateMintues(Date sDay, Date eDay) {
		return minusMinutes(minusDateSeconds(sDay.getTime(), eDay.getTime()));
	}

	public static double calculateMintues(Calendar sDay, Calendar eDay) {
		return minusMinutes(minusDateSeconds(sDay.getTimeInMillis(), eDay.getTimeInMillis()));
	}

	private static double minusMinutes(Integer seconds) {
		return Math.abs(seconds / (60 * 1.0));
	}

	/**
	 * calculateHours() method.
	 * 
	 * @date 2014-1-20 下午4:05:43
	 *       <p>
	 *       计算两个时间相差的小时
	 *       </p>
	 * @param sDay
	 * @param eDay
	 * @return double
	 */
	public static double calculateHours(Date sDay, Date eDay) {
		return minusHours(minusDateSeconds(sDay.getTime(), eDay.getTime()));
	}

	public static double calculateHours(Calendar sDay, Calendar eDay) {
		return minusHours(minusDateSeconds(sDay.getTimeInMillis(), eDay.getTimeInMillis()));
	}

	private static double minusHours(Integer seconds) {
		return seconds / (60 * 60 * 1.0);
	}

	/**
	 * daysBetween() method.
	 * 
	 * @date 2014-1-20 下午3:42:37
	 *       <p>
	 *       计算两个日期相差的天数
	 *       </p>
	 * @param sDay
	 * @param eDay
	 * @return int
	 */
	public static int calculateDay(Date sDay, Date eDay) {
		Calendar s = Calendar.getInstance();
		s.setTime(sDay);
		Calendar e = Calendar.getInstance();
		e.setTime(eDay);
		return calculateDay(s, e);
	}

	public static int calculateDay(Calendar sDay, Calendar eDay) {
		sDay = truncate(sDay, Calendar.DAY_OF_MONTH);
		eDay = truncate(eDay, Calendar.DAY_OF_MONTH);
		return (int) (minusDateSeconds(sDay.getTimeInMillis(), eDay.getTimeInMillis()) / (60 * 60 * 24));
	}

	/**
	 * calculateDay() method.
	 * 
	 * @date 2014-1-20 下午5:11:05
	 *       <p>
	 *       增减天数
	 *       </p>
	 * @param day
	 * @param num
	 * @return Date
	 */
	public static Date calculateAddDay(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.DAY_OF_MONTH, num);
	}

	public static Date calculateAddDay(Calendar day, int num) {
		return calculateMinusOrPlus(day, Calendar.DAY_OF_MONTH, num);
	}

	/**
	 * 增减月数
	 * @param day 当前日期
	 * @param num 要增加的月份
	 * @return 增加月份后的日期
	 */
	public static Date calculateAddMonth(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.MONTH, num);
	}

	/**
	 * calulateAddMinute() method.
	 * 
	 * @date 2014-1-20 下午5:11:28
	 *       <p>
	 *       增减分
	 *       </p>
	 * @param day
	 * @param num
	 * @return
	 * @return Date
	 */
	public static Date calculateAddMinute(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.MINUTE, num);
	}

	public static Date calculateAddMinute(Calendar day, int num) {
		return calculateMinusOrPlus(day, Calendar.MINUTE, num);
	}

	/**
	 * calulateAddSecond() method.
	 * 
	 * @date 2014-1-20 下午5:11:31
	 *       <p>
	 *       增减秒
	 *       </p>
	 * @param day
	 * @param num
	 * @return
	 * @return Date
	 */
	public static Date calculateAddSecond(Date day, int num) {
		return calculateMinusOrPlus(day, Calendar.SECOND, num);
	}

	public static Date calculateAddSecond(Calendar day, int num) {
		return calculateMinusOrPlus(day, Calendar.SECOND, num);
	}

	/**
	 * calculateMinusOrPlus() method.
	 * 
	 * @date 2014-1-20 下午5:00:48
	 *       <p>
	 *       根据操作类型增减时间
	 *       </p>
	 * @param date
	 *            需要操作的日期
	 * @param type
	 *            操作类型
	 * @param num
	 *            增减时间值
	 * @return Date 增减后的日期
	 */
	private static <T> Date calculateMinusOrPlus(T date, Integer type, int num) {
		Calendar day = null;
		if (date instanceof Date) {
			day = Calendar.getInstance();
			day.setTime((Date) date);
		} else if (date instanceof Calendar) {
			day = (Calendar) date;
		} else {
		}

		if (null != day) {
			day.add(type, num);
			return day.getTime();
		} else {
			return null;
		}
	}

	/**
	 * compareToMax() method.
	 * 
	 * @date 2014-1-20 下午5:45:02
	 *       <p>
	 *       取两个日期大的
	 *       </p>
	 * @param sDay
	 * @param eDay
	 * @return Date
	 */
	public static Date compareMax(Date sDay, Date eDay) {
		if (sDay.compareTo(eDay) > 0) {
			return sDay;
		} else {
			return eDay;
		}
	}

	/**
	 * compareMin() method.
	 * 
	 * @date 2014-1-20 下午5:51:05
	 *       <p>
	 *       取两个日期小的
	 *       </p>
	 * @param sDay
	 * @param eDay
	 * @return Date
	 */
	public static Date compareMin(Date sDay, Date eDay) {
		if (sDay.compareTo(eDay) < 0) {
			return sDay;
		} else {
			return eDay;
		}
	}
	
	//转换日期时间为指定格式字符串
	public static String formatDateTime(Date myDate, String pattern) {
		DateFormat fd = new SimpleDateFormat(pattern);
		return fd.format(myDate);
	}

	/**
	 * getUTCStringByDate method.
	 * 
	 * @date 2015年4月9日 下午1:59:23
	 *       <p>
	 *       将日期转换为UTC格式的字符串
	 *       </p>
	 * @param date
	 *            原始日期
	 * @return String UTC格式
	 */
	public static String getUTCStringByDate(Date date) {
		// 1、取得本地时间：
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);

		// 2、取得时间偏移量：
		int zoneOffset = cal.get(Calendar.ZONE_OFFSET);

		// 3、取得夏令时差：
		int dstOffset = cal.get(Calendar.DST_OFFSET);

		// 4、从本地时间里扣除这些差量，即可以取得UTC时间：
		cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));

		// 调用cal.get(int x)或cal.getTimeInMillis()方法所取得的时间即是UTC标准时间。
		return getStringByDateTime(new Date(cal.getTimeInMillis()));
	}
	
	/**
	 * 根据当前时间获取本周的第一天和最后一天
	 * @param date 当前时间
	 * @return 
	 */
	public static String getWeekDay(Date date) {
		SDF = new SimpleDateFormat(FORMAT_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		// 判断要计算的日期是否是周日，如果是则减一天计算周六的，否则会出问题，计算到下一周去了
		int dayWeek = cal.get(Calendar.DAY_OF_WEEK);// 获得当前日期是一个星期的第几天
		if (1 == dayWeek) {
			cal.add(Calendar.DAY_OF_MONTH, -1);
		}
		// System.out.println("要计算日期为:" + sdf.format(cal.getTime())); // 输出要计算日期
		// 设置一个星期的第一天，按中国的习惯一个星期的第一天是星期一
		cal.setFirstDayOfWeek(Calendar.MONDAY);
		// 获得当前日期是一个星期的第几天
		int day = cal.get(Calendar.DAY_OF_WEEK);
		// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
		cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - day);
		String imptimeBegin = SDF.format(cal.getTime());
		// System.out.println("所在周星期一的日期：" + imptimeBegin);
		cal.add(Calendar.DATE, 6);
		String imptimeEnd = SDF.format(cal.getTime());
		// System.out.println("所在周星期日的日期：" + imptimeEnd);
		return imptimeBegin + "," + imptimeEnd;
	}

	/**
	 * 根据当前时间获取当月的第一天和最后一天
	 * @param date 当前时间
	 * @return
	 */
	public static String getMonthDay(Date date) {
		SDF = new SimpleDateFormat(FORMAT_DATE);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.MONTH, 0);
		cal.set(Calendar.DAY_OF_MONTH, 1);// 设置为1号,当前日期既为本月第一天
		String first = SDF.format(cal.getTime());

		Calendar ca = Calendar.getInstance();
		ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));
		String last = SDF.format(ca.getTime());

		return first + "," + last;
	}

	public static String getLastMonth() {
		LocalDate today = LocalDate.now();
		today = today.minusMonths(1);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
		return formatters.format(today);
	}

	public static String getNowMonth() {
		LocalDate today = LocalDate.now();
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
		return formatters.format(today);
	}

	public static String getNextMonth() {
		LocalDate today = LocalDate.now();
		today = today.plusMonths(1);
		DateTimeFormatter formatters = DateTimeFormatter.ofPattern("yyyy-MM");
		return formatters.format(today);
	}

	public static void main(String[] args) {

		System.out.println(getDate("2020-07-15", "yyyy-MM-dd"));




		Date sday = getDateTimeByString("2014-01-01 00:10:11");
		Date eday = getDateTimeByString("2013-01-01 10:10:10");
		System.out.println(calculateDay(sday, eday));
		System.out.println(calculateHours(sday, eday));
		System.out.println(calculateMintues(sday, eday));
		System.out.println(calculateSeconds(sday, eday));
		System.out.println("----");
		System.out.println(calculateAddDay(sday, 10));
		System.out.println(calculateAddMinute(sday, 10));
		System.out.println(calculateAddSecond(sday, 10));

		System.out.println("----");
		System.out.println(getDate("Sep 19 2011 12:07:09 PM", "MMM dd yyyy HH:mm:ss"));
		System.out.println(getDate("Fri May 31 2013", "EEE MMM dd yyyy"));
		System.out.println(getDate("Mon Apr 01 09:46:19 2013", "EEE MMM dd HH:mm:ss yyyy"));
		System.out.println(getDate("Sep 12, 2013 3:27:17 Pm", "MMM dd, yyyy hh:mm:ss"));

		System.out.println("----");
		System.out.println(compareMax(sday, eday));
		System.out.println(compareMin(sday, eday));

		System.out.println("----");
		System.out.println(new Date());
		System.out.println(getDateByDateTime(new Date()));

		System.out.println(getDate("五月 22 2004 Pm 6:05:42", "MMM dd, yyyy hh:mm:ss"));

		System.out.println(getTransactionDate());
	}
}
