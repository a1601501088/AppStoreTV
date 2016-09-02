package com.app.util;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DateUtil {
	public final static SimpleDateFormat SDF=new SimpleDateFormat("yyyy-MM-dd");
	public final static SimpleDateFormat SDF_TIME=new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public final static SimpleDateFormat SDF_DATETIME=new SimpleDateFormat("yyyy-MM-ddHH");
	public final static SimpleDateFormat SDF_DATE_HHMMSS=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public final static SimpleDateFormat SDF_YEAR=new SimpleDateFormat("yyyy");
	public final static SimpleDateFormat SDF_DAYS=new SimpleDateFormat("yyyyMMdd");
	
	public static String format(Date date){
		try {
			return SDF.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date formatDate(String date){
		try {
			return SDF.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date formatDateTime(String date){
		try {
			return SDF_DATE_HHMMSS.parse(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String formatDateTimeToStr(Date date){
		try {
			return SDF_DATE_HHMMSS.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String format(Date date,String temp){
		try {
			SimpleDateFormat sf=new SimpleDateFormat(temp);
			return sf.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static Date getDate(String str){
		try {
			return SDF.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getSysDate(){
		try {
			return SDF.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	public static String getSysTime(){
		try {
			return SDF_DATETIME.format(new Date());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 日期比较，如果start>=end 返回true 否则返回false
	 * @param start
	 * @param end
	 * @return
	 */
	public static boolean compareDate(String start,String end){
		if(formatDate(start)==null||formatDate(end)==null){
			return false;
		}
		return formatDate(start).getTime()>formatDate(end).getTime();
	}
	/**
	 * 计算从起始日期到结束日期间隔的天数
	 * @param from 起始日期
	 * @param to 结束日期
	 * @return 间隔天数
	 * @throws ParseException
	 */
	public static long dayDiff(String from,String to)throws ParseException{
		long diff=SDF.parse(to).getTime()-SDF.parse(from).getTime();
		return diff /=1000*60*60*24;
	}
	/**
	 * 计算从起始日期到今天的间隔天数
	 * @param from 起始日期
	 * @return 间隔天簌
	 * @throws ParseException
	 */
	public static long dayDiff(String from)throws ParseException{
		long diff=new Date().getTime()-SDF.parse(from).getTime();
		return diff /=1000*60*60*24;
	}
	/**
	 * 计算今天到结束日期的间隔天数
	 * @param to 结束日期
	 * @return 间隔天数
	 * @throws ParseException
	 */
	public static long dayDiffto(String to)throws ParseException{
		long diff=SDF.parse(to).getTime()-new Date().getTime();
		return diff /=1000*60*60*24;
	}
	/**
	 * 得到两日期之间所有日期List
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static List<String> getDateList(String startDate,String endDate){
		List<String> dateList=new ArrayList<String>();
		try {
			Date sDate=SDF.parse(startDate);
			Calendar c=Calendar.getInstance();
			c.setTime(sDate);
			int dayDiff=(int)dayDiff(startDate,endDate);
			for(int i=0;i<dayDiff;i++){
				dateList.add(SDF.format(c.getTime()));//第一个为开始日期
				c.add(Calendar.DAY_OF_YEAR,1);//+1
			}
			return dateList;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取某个日期的后一天日期
	 * @param date
	 * @return
	 */
	public static Date getNextDate(Date date){
		Calendar c=Calendar.getInstance();
		c.setTime(date);
		c.add(Calendar.DAY_OF_YEAR, 1);
		return c.getTime();
	}
	public static Calendar getCalendar(){
		Calendar c=Calendar.getInstance();
		c.set(Calendar.HOUR_OF_DAY, 0);
		c.set(Calendar.MINUTE, 0);
		c.set(Calendar.SECOND, 0);
		c.set(Calendar.MILLISECOND, 0);
		return c;
	}
	/**
	 * 根据当前日期获取上个月第一天和最后一天的日期
	 * @return map.startday 上个月第一天日期  map.endday 上个月最后一天日期
	 */
	public static Map<String,Date> getLastMonth(){
		Map<String,Date> map=new HashMap<String,Date>();
		Calendar c=getCalendar();
		c.add(Calendar.MONTH, -1);
		c.set(Calendar.DAY_OF_MONTH, c.getActualMinimum(Calendar.DAY_OF_MONTH));
		map.put("startday", c.getTime());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		map.put("endday", c.getTime());
		return map;
	}
	/**
	 * 获取当前日期上周一和周天日期
	 * @return map.startday 上周周日日期  map.endday 周一日期
	 */
	public static Map<String,Date> getLastWeek(){
		Map<String,Date>map=new HashMap<String,Date>();
		Calendar c=getCalendar();
		if(c.get(Calendar.DAY_OF_WEEK)==1)c.add(Calendar.DAY_OF_WEEK, -1);
		c.set(Calendar.DAY_OF_WEEK, 1);//上周的周日=这周的第一天
		map.put("startday", c.getTime());
		c.add(Calendar.DAY_OF_WEEK, -1);
		c.set(Calendar.DAY_OF_WEEK, 2);//上周的周一=上周的的第二天
		map.put("endday", c.getTime());
		return map;
	}
	/**
	 * 获取去年的第一天和最后一天
	 * @return
	 */
	public static Map<String,Object> getLastYear(){
		Map<String,Object> map=new HashMap<String,Object>();
		Calendar c=getCalendar();
		c.add(Calendar.DAY_OF_YEAR, -1);//-1 去年
		c.set(Calendar.DAY_OF_MONTH,0);//第一 月
		c.set(Calendar.DATE, 1);//第一天
		map.put("startday", c.getTime());
		c.set(Calendar.DAY_OF_MONTH,11);//0-11  11：最后一月
		c.set(Calendar.DATE, 31);//12月31日
		map.put("startday", c.getTime());
		return map;
	}
	/**
	 * 今年第一天到今天的范围
	 * @return
	 */
	public static Map<String,Object> getfromToday(){
		Map<String,Object> map=new HashMap<String,Object>();
		Calendar c=getCalendar();
		map.put("endday", c.getTime());
		c.set(Calendar.DAY_OF_MONTH, 0);
		c.set(Calendar.DATE, 1);
		map.put("startday", c.getTime());
		return map;
	}
	/**
	 * 获取上个季度的一天和最后一天
	 * @return
	 */
	public static Map<String,Object> getLastQuater(){
		Map<String,Object> map=new HashMap<String,Object>();
		Calendar c=getCalendar();
		c.set(Calendar.DATE, 1);
		//计算当前为当前季度的第几个月
		int dmonth=c.get(Calendar.MONTH)%3;//从0开始，如一月：0
		c.add(Calendar.MONTH, -dmonth);
		c.add(Calendar.MONTH, -3);
		map.put("startday", c.getTime());
		c.add(Calendar.MONTH, 2);
		c.set(Calendar.DATE,c.getActualMaximum(Calendar.DAY_OF_MONTH));
		map.put("endday", c.getTime());
		return map;
	}
	public  static String getCurrentTime(){
		Calendar calendar = Calendar.getInstance();
		String currentTime = DateFormatUtils.format(calendar, "yyyyMMddHHmmss");
		return  currentTime;
	}

	/**
	 * 订单的过期时间
	 * 如果当前时间大于订单的过期时间，则查询联创的订购关系接口，如果是续订则再次调用些方法更新过期时间,否则与联创的过期时间比较,
	 * 如果当前时间大于联创的过期时间，则需要订购
	 * @return
     */
	public static String getExpire(){
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MONTH,+1);
		calendar.set(calendar.DATE,2);
		calendar.set(Calendar.HOUR_OF_DAY,0);
		calendar.set(Calendar.MINUTE,0);
		calendar.set(calendar.SECOND,0);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = sdf.format(calendar.getTime());
		return d;
	}

	/**
	 * 把时间类型的字符串解析成date类型
	 * @param dateStr  yyyy-MM-dd hh:mm:ss
     * @return
     */
	public static Date parseDate(String dateStr){
		Date date = null;
		try {
			date = DateUtils.parseDate(dateStr, new String[]{"yyyy-MM-dd hh:mm:ss"});
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}


	
	public static void main(String[] args) throws ParseException {
		System.out.println(formatDate("2015-11-18"));
		System.out.println(formatDateTimeToStr(new Date()));
		Calendar c=Calendar.getInstance();
		c.add(Calendar.DAY_OF_YEAR,1);
		System.out.println(c.getTime());
		System.out.println("--------");
		System.out.println(getDateList("2015-11-18","2015-11-27"));
		System.out.println("today nextday:"+SDF_DATE_HHMMSS.format(getNextDate(new Date())));
		Map lastmonth=getLastMonth();
		System.out.println("上个月第一天日期："+lastmonth.get("startday").toString());
		System.out.println("上个月最后一天日期："+lastmonth.get("endday").toString());
		Map lastweek=getLastWeek();
		System.out.println("上周周日日期："+SDF_DATE_HHMMSS.format(lastweek.get("startday")));
		System.out.println("上周周一日期："+SDF_DATE_HHMMSS.format(lastweek.get("endday")));
		Map lastquater=getLastQuater();
		System.out.println("上季度第一天日期："+SDF_DATE_HHMMSS.format(lastquater.get("startday")));
		System.out.println("上季度最后一天日期："+SDF_DATE_HHMMSS.format(lastquater.get("endday")));
	}
}
