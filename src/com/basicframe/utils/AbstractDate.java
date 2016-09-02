package com.basicframe.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * <p>Description: 日期工具类</p>
 * <p>提供日期之间的互相转换</p>
 * <p>提供日期和字符串各种格式之间的互相转换</p>
 * <p>提供获取日期的各种年份月份和日期及时间</p>
 * 
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public abstract class AbstractDate {

	/**
	 * 简单模式只包含年月日
	 */
	public static final String SIMPLE_FORMAT_PATTERN = "yyyy-MM-dd";

	/**
	 * 普通模式包含年月日小时分钟秒
	 */
	public static final String NORMAL_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 简单完全模式模式包含年月日小时分钟秒 毫秒
	 */
	public static final String SFULL_FORMAT_PATTERN = "yyyy-MM-dd HH:mm:ss SSS";

	/**
	 * 没有分隔符的年月日
	 */
	public static final String NO_SEPARATE_YYYYMMDD_FORMAT_PATTERN = "yyyyMMdd";
	
	/**
	 * 时分秒的格式
	 */
	public static final String SIMPLE_HHMMSS_FORMAT_PATTERN = "HH:mm:ss";
	
	/**
	 * 年月日时分的格式
	 */
	public static final String SIMPLE_YYYYMMDDHH_FORMAT_PATTERN = "yyyyMMddHHmm";

	/**
	 * ORACLE数据库
	 */
	protected static final int ORACLE_TYPE = 1;
	
	/**
	 * 微软mssqlserver数据库
	 */
	protected static final int MSSQL_TYPE = 2;
	
	/**
	 * mysql数据库
	 */
	protected static final int MYSQL_TYPE = 3;
	
	
	private static Calendar calendar =  Calendar.getInstance(Locale.CHINA);
	private static Map<String, DateFormat> formats = new HashMap<String, DateFormat>();

	protected AbstractDate(){
		init();
	}
	
	//mysql
	//now()：以'yyyy-mm-dd hh:mm:ss'返回当前的日期时间，可以直接存到datetime字段中。
	//curdate()：’yyyy-mm-dd’的格式返回今天的日期，可以直接存到date字段中。
	
	//sqlserver
	//GETDATE()
	
	//oracle
	//sysdate

	/**
	 * 获取系统的当前日期。 one:获取操作系统日期 two:获取数据库日期 返回java.util.Date类型日期
	 */
	public java.util.Date getCurrentDate() {
		java.util.Date currentTime = null;
		if(isDatabase()){
			currentTime = loadDateFromDatabase();
		}else{
			currentTime = new java.util.Date();
		}
		return currentTime;
	}
	
	private java.util.Date loadDateFromDatabase() {
		String SQL = null ;
		if(getDatabaseType() == ORACLE_TYPE){
			SQL ="SELECT SYSDATE FROM DUAL";
		}else if (getDatabaseType() == MSSQL_TYPE){
			SQL ="SELECT GETDATE()";
		}else if(getDatabaseType() == MYSQL_TYPE){
			SQL = "SELECT NOW()";
		}else{
			throw new IllegalArgumentException("未支持的数据库");
		}
		SQL.toString();
		//Timestamp t = (Timestamp) getJdbcTemplate().queryForObject(SQL, Timestamp.class);
		return null;
	}
	
	/**
	 * 设置数据库种类
	 * @return
	 */
	protected abstract int getDatabaseType();
	
	/**
	 * 设置获取时间的方式
	 * @return true 从数据库获取时间，否则从操作系统获取时间
	 */
	protected abstract boolean isDatabase() ;
	
	/**
	 * 获取当前时间的sql.date类型日期
	 */
	public java.sql.Date getCurrentSqlDate() {
		return date2SqlDate(getCurrentDate());
	}

	/**
	 * 获取当前时间的Timestamp类型日期
	 */
	public java.sql.Timestamp getCurrentTimestamp() {
		return date2Timestamp(getCurrentDate());
	}
	
	/**
	 * 获得当前时间的字符串
	 * @return String 日期字符串 格式:"yyyy-mm-dd hh24:mm:ss" 
	 * 例:"2000-7-6 13:13:13"  "2000-11-6 13:13:13"
	 * @exception
	 */
	public String getCurrentDateString() {
		return date2String(getCurrentDate(), NORMAL_FORMAT_PATTERN);
	}
	
	/**
	 * 获得当前日期简单模式的字符串
	 * @return String 日期字符串 格式:"yyyy-MM-dd"
	 */
	public String getCurrentYMDString() {
		return date2String(getCurrentDate(), SIMPLE_FORMAT_PATTERN);
	}

	/**
	 * 获得当前日期不带分隔符号的简单模式的字符串
	 * @return String 日期字符串 格式:"yyyyMMdd"
	 */
	public String getCurrentNSYMDString() {
		return date2String(getCurrentDate(), NO_SEPARATE_YYYYMMDD_FORMAT_PATTERN);
	}

	/**
	 * 获得当前日期的时分秒的字符串
	 * @return String 日期字符串 格式:"hh24:mm:ss"
	 */
	public String getCurrentHMSString() {
		return date2String(getCurrentDate(), SIMPLE_HHMMSS_FORMAT_PATTERN);
	}
	
	
	/**
	 * 获得当前日期不带分隔符号的简单模式的字符串
	 * @return String 日期字符串 格式:"yyyyMMddHHmm"
	 */
	public String getCurrentYDMHMSString() {
		return date2String(getCurrentDate(), SIMPLE_YYYYMMDDHH_FORMAT_PATTERN);
	}
	/**
	 * 获得当前日期的年份
	 * @return String 日期字符串 格式:"yyyy"
	 */
	public String getCurrentYear() {
		calendar.setTime(getCurrentDate());
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(year);
	}
	
	/**
	 * 获得当前日期的月份
	 * @return String 日期字符串 格式:"mm"
	 */
	public String getCurrentMonth() {
		calendar.setTime(getCurrentDate());
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		return firstFill(month,2);
	}
	/**
	 * 获得当前的日期
	 */
	public String getCurrentDay() {
		calendar.setTime(getCurrentDate());
		String day = String.valueOf(calendar.get(Calendar.DATE));
		return firstFill(day,2);
	}

	/**
	 * 将日期转换成间普通模式日期的字符串
	 * @return String 日期字符串 格式:"yyyy-mm-dd hh24:mm:ss" 
	 * 例:"2000-7-6 13:13:13"  "2000-11-6 13:13:13"
	 * @exception
	 */
	public static String date2NormalString(java.util.Date date) {
		return date2String(date, NORMAL_FORMAT_PATTERN);
	}
	
	/**
	 * 将日期转换成简单模式的字符串
	 * @return String 日期字符串 格式:"yyyy-MM-dd"
	 */
	public static String date2YMDString(java.util.Date date) {
		return date2String(date, SIMPLE_FORMAT_PATTERN);
	}

	/**
	 * 将日期转换成不带分隔符号的简单模式字符串
	 * @return String 日期字符串 格式:"yyyyMMdd"
	 */
	public static String date2NSYMDString(java.util.Date date) {
		return date2String(date, NO_SEPARATE_YYYYMMDD_FORMAT_PATTERN);
	}

	/**
	 * 获取日期的时间字符串
	 * @return String 日期字符串 格式:"hh24:mm:ss"
	 */
	public static String date2HMSString(java.util.Date date) {
		return date2String(date, SIMPLE_HHMMSS_FORMAT_PATTERN);
	}
	
	/**
	 * 将普通模式日期的字符串转换成日期
	 * @param normal日期字符串 格式:"yyyy-mm-dd hh24:mm:ss" 
	 */
	public static java.util.Date stringNormal2Date(String normal) {
		return string2Date(normal, NORMAL_FORMAT_PATTERN);
	}
	
	/**
	 * 将简单模式的字符串转换成日期
	 * @return String 日期字符串 格式:"yyyy-MM-dd"
	 */
	public static java.util.Date stringYMD2Date(String YMDString) {
		return string2Date(YMDString, SIMPLE_FORMAT_PATTERN);
	}

	/**
	 * 将不带分隔符号的简单模式字符串转换成日期
	 * @return String 日期字符串 格式:"yyyyMMdd"
	 */
	public static java.util.Date stringNSYMD2Date(String NSYMDString) {
		return string2Date(NSYMDString, NO_SEPARATE_YYYYMMDD_FORMAT_PATTERN);
	}

	/**
	 * 获取日期的时间字符串
	 * @return String 日期字符串 格式:"hh24:mm:ss"
	 */
	public static java.util.Date stringHMS2Date(String HMSString) {
		return string2Date(HMSString, SIMPLE_HHMMSS_FORMAT_PATTERN);
	}
	
	/**
	 * 获得当前日期的年份
	 * @return String 日期字符串 格式:"yyyy"
	 */
	public static String getYearOfDate(java.util.Date date) {
		calendar.setTime(date);
		int year = calendar.get(Calendar.YEAR);
		return String.valueOf(year);
	}
	
	/**
	 * 获得当前日期的月份
	 * @return String 日期字符串 格式:"mm"
	 */
	public static String getMonthOfDate(java.util.Date date) {
		calendar.setTime(date);
		String month = String.valueOf(calendar.get(Calendar.MONTH) + 1);
		return firstFill(month,2);
	}
	
	/**
	 * 获得当前的日期
	 */
	public static String getDayOfDate(java.util.Date date) {
		calendar.setTime(date);
		String day = String.valueOf(calendar.get(Calendar.DATE));
		return firstFill(day,2);
	}
	
    /**
	 * 获得毫秒数
	 */
	public static String date2Millisecond(java.util.Date date) {
		return String.valueOf(date.getTime());
	}

	/**
	 * 将毫秒数转换成日期
	 */
	public static java.util.Date millisecond2Date(String millisecond) {
		Long l = Long.valueOf(millisecond);
		return new java.util.Date(l);
	}

	
	/**
	 * 获得某时间的前后几小时的日期
	 * @param date	 当前时间间
	 * @param hour	 小时 >0后几小时，<0前几小时
	 */
	public static java.util.Date addHour(java.util.Date date, int hour) {
		calendar.setTime(date);
		calendar.add(Calendar.HOUR_OF_DAY, hour);
		return calendar.getTime();
	}
	
	
	/**
	 * 获得某时间的前后几分钟的日期
	 * @param date	 当前时间间
	 * @param minute	 分钟 >0后几分钟，<0前几分钟
	 */
	public static java.util.Date addMinute (java.util.Date date, int minute) {
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute );
		return calendar.getTime();
	}
	
	
	/**
	 * 获得某时间的前后几天的日期
	 * @param date	 当前时间间
	 * @param day	 天数 >0后几天，<0前几天
	 */
	public static java.util.Date addDay(java.util.Date date, int day) {
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}
	
	/**
	 * 获得某时间的前后几月的日期
	 * @param date	 当前时间间
	 * @param day	 天数 >0后几月，<0前几月
	 */
	public static java.util.Date addMonth(java.util.Date date, int month) {
		calendar.setTime(date);
		calendar.add(Calendar.MONTH, month);
		return calendar.getTime();
	}
	
	/**
	 * 比较俩日期的前后
	 * @param date 比较日期
	 * @param anthorDate 被比较的日期
	 * @return 0相等 1 比较日期后于被比较日期   -1 比较日期前于被比较日期
	 */
	public static int compare(java.util.Date date, java.util.Date anthorDate) {
		return date.compareTo(anthorDate);
	}

	/**
	 * 将java.util.Date日期转成java.sql.Date日期类型
	 * @param java.util.Date
	 * @return Date
	 */
	public static java.sql.Date date2SqlDate(java.util.Date date) {
		return new java.sql.Date(date.getTime());
	}

	/**
	 * 将java.util.Date日期转成Timestamp日期类型
	 * @param date java.util.Date
	 * @return java.sql.Timestamp
	 */
	public static java.sql.Timestamp date2Timestamp(java.util.Date date) {
		return new java.sql.Timestamp(date.getTime());
	}

	/**
	 * 将java.sql.Timestamp日期转成date日期类型
	 * @param date java.util.Date
	 * @return java.sql.Timestamp
	 */
	public static java.util.Date timestamp2Date(java.sql.Timestamp date) {
		return new java.util.Date(date.getTime());
	}

	/**
	 * 将字符串转换成java.util.Date类型的日期
	 * @param dateStr日期字符串
	 * @param pattern字符串格式 例如 2008-08-01 23:18:59对应的格式应该是：yyyy-MM-dd HH:mm:ss
	 * @return
	 */
	public static java.util.Date string2Date(String dateStr, String pattern) {
		java.util.Date result = null;
		try {
			if(ToolBox.isCorrect(dateStr)){
				result = getDateFormat(pattern).parse(dateStr);
			}
		} catch (ParseException e) {
			throw new IllegalArgumentException(e);
		}
		return result;
	}

	/**
	 * 将日期转换成字符串
	 * @param date 日期
	 * @param pattern  字符串格式
	 * @see AbstractDate.SIMPLE_FORMAT_PATTERN
	 * @return
	 */
	public static String date2String(java.util.Date date, String pattern) {
		String result = null;
		result = getDateFormat(pattern).format(date);
		return result;
	}
	
	/**
	 * 字符串长度不足是前补充指定字符
	 * @param len 指定结果应该是几位，如果字符串长度超过len将默认返回字符串，不做截取处理
	 */
	private static String firstFill(String p_str, int len) {
		int i = len-p_str.length();
		StringBuffer sb = new StringBuffer();
		while (i > 0) {
			sb.append("0");
			i--;
		}
		sb.append(p_str);
		return sb.toString();
	}

	private static DateFormat getDateFormat(String pattern) {
		DateFormat sdf = (DateFormat) formats.get(pattern);
		if (sdf == null) {
			sdf = new java.text.SimpleDateFormat(pattern,Locale.CHINA);
			formats.put(pattern, sdf);
		}
		return sdf;
	}

	/**
	 * 此方法应该在系统启动项中配置，提升日期工具类的性能 将首次应用最多的format放在次方法中实例化
	 */
	public  void init() {
		formats.put(SIMPLE_FORMAT_PATTERN, new java.text.SimpleDateFormat(SIMPLE_FORMAT_PATTERN,Locale.CHINA));
		formats.put(NORMAL_FORMAT_PATTERN, new java.text.SimpleDateFormat(NORMAL_FORMAT_PATTERN,Locale.CHINA));
		formats.put(SFULL_FORMAT_PATTERN, new java.text.SimpleDateFormat(SFULL_FORMAT_PATTERN,Locale.CHINA));
		formats.put(NO_SEPARATE_YYYYMMDD_FORMAT_PATTERN, new java.text.SimpleDateFormat(NO_SEPARATE_YYYYMMDD_FORMAT_PATTERN,Locale.CHINA));
		formats.put(SIMPLE_HHMMSS_FORMAT_PATTERN, new java.text.SimpleDateFormat(SIMPLE_HHMMSS_FORMAT_PATTERN,Locale.CHINA));
	}

	/**
	 * 判断时间间隔
	 * @param start
	 * @param point
	 * @param days
	 * @return
	 */
	public static boolean isAccordTime(Date start, Date point, int days) {
		boolean res = false;
		if (!start.after(point)) {
			if (getTwoDay(point.toString(), start.toString()) >= days) {
				res = true;
			}
		}
		return res;
	}

	/**
	 * 判断是否是同一天
	 * @param start
	 * @param point
	 * @return
	 */
	public static boolean isSameDay(Timestamp start, Timestamp point) {
		boolean res = false;
		if (!start.after(point)) {
			if (getTwoDay(point.toString(), start.toString()) == 0) {
				res = true;
			}
		}
		return res;
	}

	/**
	 * 判断是否是同一天 修改版 --add by okey
	 * @param start
	 * @param point
	 * @return
	 */
	public static boolean isSameDay2(Timestamp start, Timestamp point) {
		boolean res = false;
		if (getTwoDay(point.toString(), start.toString()) == 0) {
			res = true;
		}
		return res;
	}
	
	/**
	 * 获取指定日期的 前后某个日期 --YYYY-MM-DD
	 * @param date1  YYYY-MM-DD
	 * @param day int 负数为指定日期前的某个日期
	 * @return
	 * @throws Exception
	 */
	public static String getAfterDay(String date1,int day) throws Exception{
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		java.util.Date date;
		try {
			date = myFormatter.parse(date1);
			date.setTime(date.getTime() + day * 24 * 60 * 60 * 1000);
			//获取日期
		return date2String(date, SIMPLE_FORMAT_PATTERN);
		} catch (ParseException e) {
			e.printStackTrace();
			throw e;
		}

	}

	/**
	 * 得到二个日期间的间隔天数
	 * @param sj1
	 * @param sj2
	 * @return
	 */
	public static long getTwoDay(String sj1, String sj2) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(sj1);
			java.util.Date mydate = myFormatter.parse(sj2);
			day = (date.getTime() - mydate.getTime()) / (24 * 60 * 60 * 1000);
		} catch (Exception e) {
			return day;
		}
		return day;
	}

	/**
	 * 得到二个日期间的间隔分钟数
	 * @param start
	 * @param point
	 * @return
	 */
	public static long getTwoDayHours(Timestamp start, Timestamp point) {
		SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long day = 0;
		try {
			java.util.Date date = myFormatter.parse(point.toString());
			java.util.Date mydate = myFormatter.parse(start.toString());
			day = (date.getTime() - mydate.getTime()) / (60 * 1000);
		} catch (Exception e) {
			return day;
		}
		return day;
	}

	/**
	 * 得到二个日期间的间隔天数 含半天
	 */
	/*
	 * public static long getTwoHalfDay(String sj1, String sj2) {
	 * SimpleDateFormat myFormatter = new SimpleDateFormat("yyyy-MM-dd HH");
	 * long day = 0; try { java.util.Date date = myFormatter.parse(sj1);
	 * java.util.Date mydate = myFormatter.parse(sj2); day = (date.getTime() -
	 * mydate.getTime()) / (60 * 60 * 1000); } catch (Exception e) { return day; }
	 * return day ; }
	 */

	/**
	 * 根据当前时间获取当天时间的开始与结束时间
	 * @return
	 */
	public static Timestamp[] getDayStartAndEnd() {
		Timestamp[] t = new Timestamp[2];
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(DateTool.instance.getCurrentTimestamp());
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			t[0] = DateTool.date2Timestamp(dateFormat1.parse(time + " 00:00:00"));
			t[1] = DateTool.date2Timestamp(dateFormat1.parse(time + " 23:59:59"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(t[0]);
		System.out.println(t[1]);
		return t;
	}

	/**
	 * 根据当前时间获取半天时间的开始与结束时间
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static Timestamp[] getHalfDayStartAndEnd() {
		Timestamp[] t = new Timestamp[2];
		Timestamp timestamp = DateTool.instance.getCurrentTimestamp();
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String time = dateFormat.format(timestamp);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			if (timestamp.getHours() >= 12) {
				// 下午
				t[0] = DateTool.date2Timestamp(dateFormat1.parse(time + " 12:00:00"));
				t[1] = DateTool.date2Timestamp(dateFormat1.parse(time + " 23:59:59"));
			} else {
				// 上午
				t[0] = DateTool.date2Timestamp(dateFormat1.parse(time + " 00:00:00"));
				t[1] = DateTool.date2Timestamp(dateFormat1.parse(time + " 11:59:59"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(t[0]);
		System.out.println(t[1]);
		return t;
	}

	/**
	 *  根据时间戳获取日期
	 * @param t
	 * @return
	 */
	public static String getDateStr(Timestamp t) {
		String res = "";
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		res = dateFormat.format(t);
		return res;
	}
	
	/**
	 * 返回当前时间的上个月 格式 'YYYYMM'
	 * @return
	 * @throws Exception
	 */
	public static String getLastMonthFormCurrentTime() throws Exception {
		Calendar cal = Calendar.getInstance();
		cal.add(Calendar.MONTH, -1);
		if ((cal.get(Calendar.MONTH) + 1) < 10)
			return String.valueOf(cal.get(Calendar.YEAR)) + "0"	+ String.valueOf(cal.get(Calendar.MONTH) + 1);
		else
			return String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * 返回当前时间的年月 格式 'YYYYMM'
	 * @return
	 * @throws Exception
	 */
	public static String getCurrentYearMonth() throws Exception {
		Calendar cal = Calendar.getInstance();
		if ((cal.get(Calendar.MONTH) + 1) < 10)
			return String.valueOf(cal.get(Calendar.YEAR)) + "0" + String.valueOf(cal.get(Calendar.MONTH) + 1);
		else
			return String.valueOf(cal.get(Calendar.YEAR)) + String.valueOf(cal.get(Calendar.MONTH) + 1);
	}

	/**
	 * @param date1 日期
	 * @param date2 被比较日期
	 * @return 若date1 在date2 之前返回 true;
	 * @throws Exception
	 */
	public static boolean compareDateBefore(Date date1, Date date2) {
		return date1.before(date2);
	}

	/**
	 * @param date1 日期
	 * @param date2 被比较日期
	 * @return 若date1 在date2 之后返回 true;
	 * @throws Exception
	 */
	public static boolean compareDateAfter(Date date1, Date date2) {
		return date1.after(date2);
	}
	
	public static Date getDateFromString(String dateString) throws Exception {
		Format format = new SimpleDateFormat("yyyy-MM-dd hh24:mm:ss");
		Date date1 = (Date) format.parseObject(dateString);
		return date1;
	}
	
	/**
     * 偏移时间
     * @param date Date 初始时间
     * @param second long 偏移秒数
     * @return Date
     */
    public static Date offsetSecond(Date date, long seconds) {
        long time = date.getTime();
        time = time + (seconds * 1000);
        return new Date(time);
    }

    /**
     * 偏移时间
     * @param date Date 初始时间
     * @param minute long 偏移分钟数
     * @return Date
     */
    public static Date offsetMinute(Date date, long minutes) {
        return offsetSecond(date, 60 * minutes);
    }

    /**
     * 偏移时间
     * @param date Date 初始时间
     * @param hour long 偏移小时数
     * @return Date
     */
    public static Date offsetHour(Date date, long hours) {
        return offsetMinute(date, 60 * hours);
    }

    /**
     * 偏移时间
     * @param date Date 初始时间
     * @param day long 偏移天数
     * @return Date
     */
    public static Date offsetDay(Date date, int days) {
        return offsetHour(date, 24 * days);
    }

    /**
     * 偏移时间
     * @param date Date 初始时间
     * @param week long 偏移周数
     * @return Date
     */
    public static Date offsetWeek(Date date, int weeks) {
        return offsetDay(date, 7 * weeks);
    }
    /**
     * 得到本月的最后时间
     * @param date Date 要偏移的时间
     * @return Date
     */
    public static Date getLastday(Date date) {
      Calendar calendar = Calendar.getInstance();
      calendar.setTime(date);
      int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
      calendar.set(Calendar.DAY_OF_MONTH,maxDay);
      calendar.set(Calendar.HOUR_OF_DAY,23);
      calendar.set(Calendar.MINUTE,59);
      calendar.set(Calendar.SECOND,59);
      date.setTime(calendar.getTimeInMillis());
      return date;
    }

    /**
     * 偏移时间(按月)
     * 规则:
     * 1. 如果要偏移的时间是月末, 偏移后也是月末
     * 2. 要偏移的时间的当前天大于偏移后的月份的最大天数也调整为月末, 比如12月30号(非月末)偏移两个月
     * 应变为2月28(29)号
     * @param date Date 要偏移的时间
     * @param months int 要偏移的月份
     * @return Date
     */
    public static Date offersetMonth(Date date, int months) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int curDay = calendar.get(Calendar.DAY_OF_MONTH);
        int maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //将当前天设置为1号, 然后增加月份数 (先加月份, 再加天)
        calendar.set(Calendar.DAY_OF_MONTH, 1);
        calendar.add(Calendar.MONTH, months);

        //加过月份以后的日期当月的最大天数
        int newMaxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);

        //如果当前天为月底, 加过以后也调整为月底
        if (curDay == maxDay) {
            calendar.set(Calendar.DAY_OF_MONTH, newMaxDay);

        } else {
            //如果要加的初始日期的天大于新的月份的最大天数, 则调整为月底, 比如12月30号加两个月
            //不是2 * 30天 到 3月2号, 而是到2月底
            if (curDay > newMaxDay) {
                calendar.set(Calendar.DAY_OF_MONTH, newMaxDay);
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, curDay);
            }
        }

        date.setTime(calendar.getTimeInMillis());
        return date;
    }

    /**
     * 检查指定时间是否在某个时间范围内(闭区间)
     * @param date Date 指定时间
     * @param beginDate Date 范围开始时间
     * @param endDate Date 范围结束时间
     * @return boolean true-在范围内, false-不在范围内
     */
    public static boolean isInRange(Date date, Date beginDate, Date endDate) {
        long time = date.getTime();
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();

        if (time >= beginTime && time <= endTime) {
            return true;
        } else {
            return false;
        }
    }
    /**
     * 检查指定时间比较大小
     * * @param beginDate Date 范围开始时间
     * @param endDate Date 范围结束时间
     * @return boolean 0-小于, 1-等于，2-大于
     */
    public static int isCompare(Date beginDate, Date endDate) {
        int ret=1;
        long beginTime = beginDate.getTime();
        long endTime = endDate.getTime();

        if (beginTime >endTime) {
            ret=2;
        }
        if(beginTime==endTime){
            ret=1;
        }
        if(beginTime<endTime){
           ret=0;
        }
        return ret;
    }
	
	
}
