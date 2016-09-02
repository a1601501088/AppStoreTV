package com.basicframe.utils;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.exception.CoreException;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.Random;


public final class ToolBox {

	/**
	 * 定义 an empty string
	 */
	public final static String EMPTY_STRING = "";

	/**
	 * 判断字符串是否非空并且非EMPTY_STRING
	 */
	public static boolean isCorrect(String p_Parameter) {
		boolean bTmpCheckResult = false;
		if ((p_Parameter != null) && (!p_Parameter.equals(EMPTY_STRING))){
			bTmpCheckResult = true;
		}
		return bTmpCheckResult;
	}

	/**
	 * 判断Collection是否有值
	 */
	public static boolean isCorrect(Collection<?> p_Parameter) {
		if ((p_Parameter == null) || p_Parameter.isEmpty()){
			return false;
		}
		return true;
	}

	/**
	 * 判断Map是否有值
	 */
	public static boolean isCorrect(Map<?, ?> p_Parameter) {
		if ((p_Parameter == null) || p_Parameter.isEmpty()){
			return false;
		}
		return true;
	}


	/**
	 * @author：okey
	 * @notes: 判断对象是否非空或者有值  有值返回 true 没值返回false
	 * @param p_Parameter
	 * @return
	 * @date：Jul 5, 2010
	 * @modify：
	 */
	public static boolean isCorrect(Object p_Parameter) {
		if (p_Parameter instanceof String) {
			return isCorrect((String) p_Parameter);
		}
		if (p_Parameter instanceof Collection) {
			return isCorrect((Collection<?>) p_Parameter);
		}
		if (p_Parameter instanceof Map) {
			return isCorrect((Map<?, ?>) p_Parameter);
		}
		if (p_Parameter != null){
			return true;
		}
		return false;
	}

	/**
	 * 将字符串转换成整数,如果非法字符穿异常 add by xiaonek
	 * @param numberSring
	 * @return
	 */
	public static Integer string2Integer(String numberSring) {
		if (isDigit(numberSring)) {
			return Integer.parseInt(numberSring);
		}
		return null;
	}

	/**
	 * 将字符串转换成整数,如果非法字符穿异常 add by xiaonek
	 * @param numberSring
	 * @return
	 */
	public static Long string2Long(String numberSring) {
		if (isDigit(numberSring)) {
			return Long.parseLong(numberSring);
		}
		return null;
	}

	/**
	 * ob1,ob2对象比较. If ob1 if null then ob2 must be null. If ob1 if not null ob2
	 * must be equal to ob1.
	 */
	public static boolean isEqual(Object ob1, Object ob2) {
		boolean result = true;
		if (ob1 == null) {
			result = result && (ob2 == null);
		} else {
			result = result && (ob1.equals(ob2));
		}
		return result;
	}

	/**
	 * 在一个String中,用p_newText 替换 p_oldText
	 */
	public static String replacePattern(String p_stringToParse, String p_oldText, String p_newText) {
		int index;
		int lenght = p_oldText.length();
		while ((index = p_stringToParse.lastIndexOf(p_oldText)) != -1) {
			p_stringToParse = p_stringToParse.substring(0, index) + p_newText + p_stringToParse.substring(index + lenght);
		}
		return p_stringToParse;
	}

	/**
	 * returns 包的字符串不包含类名城
	 */
	public static String getPackageName(Object where) {
		String name = null;
		if (where instanceof java.lang.Class){
			name = getSubPackName(((Class<?>) where).getName());
		}else if(where instanceof String){
			name = (String) where;
		}else{
			name = getSubPackName(where.getClass().getName());
		}
		return name;
	}

	/**
	 * returns 类名称不包含包路径
	 */
	public static String getClassName(Object where) {
		String name = null;
		if (where instanceof java.lang.Class)
			name = getSubClassName(((Class<?>) where).getName());
		else if (where instanceof String)
			name = (String) where;
		else
			name = getSubClassName(where.getClass().getName());
		return name;
	}

	private static String getSubPackName(String p_classOrPackageName) {
		int index = p_classOrPackageName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException("Can't find SubPackName ");
		}
		return p_classOrPackageName.substring(0, index);
	}

	private static String getSubClassName(String p_classOrPackageName) {
		int index = p_classOrPackageName.lastIndexOf(".");
		if (index == -1) {
			throw new IllegalArgumentException("Can't find SubClassName ");
		}
		return p_classOrPackageName.substring(index + 1);
	}

	/**
	 * 补充0（前缀）
	 * 
	 * @param userSeq
	 * @param length
	 * @return
	 */
	public static String fillZero(String userSeq, int length) {
		return fillZero(userSeq, length, true);
	}

	/**
	 * 
	 * @param userSeq
	 * @param length
	 *            补0个数
	 * @param isBefore
	 *            是否在前补充零
	 * @return
	 */
	public static String fillZero(String userSeq, int length, boolean isBefore) {
		StringBuffer sb = new StringBuffer();
		while (length > 0) {
			sb.append("0");
			length--;
		}
		if (isBefore) {
			return sb.toString() + userSeq;
		} else {
			return userSeq + sb.toString();
		}
	}

	/**
	 * @author: 
	 * @param numberString
	 * @return
	 * @time: Aug 7, 2010 4:37:33 PM
	 * @modify：
	 */
	public static boolean isDigit(String numberString) {
		if (isCorrect(numberString)) {
			for (int i = 0; i < numberString.length(); i++) {
				if (!Character.isDigit(numberString.charAt(i))) {
					throw new IllegalArgumentException("字符串" + numberString + "不是数字");
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * @author：okey
	 * @notes: 判断是否为空 若为空 抛出异常
	 * @param obj
	 * @throws CoreException 
	 * @date：Jul 3, 2010
	 * @modify：
	 */
	public static void isNull(Object obj) throws BusException {
		if (obj == null || isCorrect(obj) == false)
			throw new BusException(CoreException.PARAM_NULL);
	}

	/**
	 * @author：okey
	 * @notes: 公用异常抛出
	 * @param exceptionCode
	 * @throws CoreException
	 * @date：Jul 3, 2010
	 * @modify：
	 */
	public static void busExcep(String exceptionCode) throws BusException {
		if (isCorrect(exceptionCode) == false) {
			throw new BusException(CoreException.PARAM_NULL);
		}
	}
	
	/**
	 * @author：okey
	 * @notes: 获取MD5加密串
	 * @param str
	 * @return
	 * @throws CoreException
	 * @date：Jul 21, 2010
	 * @modify：
	 */
	public static String getMD5String(String str){
		return new MD5().getMD5ofStr(str);
	}
	
	/**
	 * 获取随即名文件
	 * @author tyj
	 * @return String
	 * @date Nov 9, 2010
	 * @modify
	 */
	public static String getFileRandName(){
		//所有的字母A-Z,a-z,0-9
		String randStr = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz";
	    StringBuffer generateRandStr = new StringBuffer();
	    Random rand = new Random();
	    //你想生成的随机数的长度
	    int randStrLength = 20;
	    for (int i = 0; i < randStrLength; i++) {
	         int randNum = rand.nextInt(62);
	         generateRandStr.append(randStr.substring(randNum,randNum+1));
	    }
	    return generateRandStr.toString();
	}

	public static String toLocaleString(Date date){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String d = sdf.format(date);
		return d ;
	}

	public static String sha1(String text){
		MessageDigest md = null;
		try {
			md = MessageDigest.getInstance("SHA-1");
			byte[] digest = md.digest(text.getBytes());
			StringBuilder buf = new StringBuilder();
			for (int i = 0; i < digest.length; i++) {
				String tempStr = Integer.toHexString(digest[i] & 0xff);
				if (tempStr.length() == 1) {
					buf.append("0").append(tempStr);
				} else {
					buf.append(tempStr);
				}
			}
			return buf.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException(e);
		}

	}
	
}
