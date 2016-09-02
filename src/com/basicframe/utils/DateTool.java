package com.basicframe.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * 日期工具类
 * @author: tyj
 * @time: Aug 4, 2010 9:43:22 PM
 * @modify：
 */
public class DateTool extends AbstractDate { 

	/**
	 * 没有分隔符的年月日
	 */
	public static final String NO_SEPARATE_YYYYMM_FORMAT_PATTERN = "yyyyMM";

	public static DateTool instance = new DateTool();

	private DateTool() {
		super();
	}

	/**
	 * 设置获取时间的方式
	 * 
	 * @return true 从数据库获取时间，否则从操作系统获取时间
	 */
	protected boolean isDatabase() {
		return false;
	}

	/*
	 * 设置oracle数据库
	 */
	protected int getDatabaseType() {
		return MYSQL_TYPE;
	}

   /** 返回日期格式的 字符串
    * fz
    * @param time
    * @param type
    * @return
    */
	public static String formatDateTime(long time, int type) {
		SimpleDateFormat df;
		switch (type) {
			case 0 :
				df = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 1 :
				df = new SimpleDateFormat("HH:mm:ss");
				break;
			case 2 :
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 3 :
				df = new SimpleDateFormat("MM-dd");
				break;
			case 4 :
				df = new SimpleDateFormat("HH:mm");
				break;
			case 5 :
				df = new SimpleDateFormat("MM-dd HH:mm:ss");
				break;
			case 6 :
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
				break;
			case 7 :
				df = new SimpleDateFormat("yyyy-MM");
				break;
			case 8 :
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
				break;
			default :
				df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
		}
		Calendar cal = Calendar.getInstance();
		cal.setTimeInMillis(time);
		return df.format(cal.getTime());
	}

}
