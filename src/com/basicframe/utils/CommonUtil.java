package com.basicframe.utils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.text.StringCharacterIterator;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import javax.servlet.http.HttpServletRequest;

import org.apache.oro.text.regex.MalformedPatternException;
import org.apache.oro.text.regex.Perl5Compiler;
import org.apache.oro.text.regex.Perl5Matcher;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.basicframe.common.exception.BusException;

/**
 * <p>Description: 工具类 - 公共</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class CommonUtil {
	
	
	public static final String CHARSET = "UTF-8";
	private static final String randChars = "0123456789ABCDEFGHIGKMNPQRSTUVWXYZ";
	private static final char[] pregChars = {'.', '\\', '+', '*', '?', '[', '^', ']', '$', '(', ')', '{',
		'}', '=', '!', '<', '>', '|', ':'};
	private static Random random = new Random();

	/**
	 * 随机获取UUID字符串(无中划线)
	 * 
	 * @return UUID字符串
	 */
	public static String getUUID() {
		String uuid = UUID.randomUUID().toString();
		return uuid.substring(0, 8) + uuid.substring(9, 13) + uuid.substring(14, 18) + uuid.substring(19, 23) + uuid.substring(24);
	}
	
	/**
	 * 随机获取字符串
	 * 
	 * @param length
	 *            随机字符串长度
	 * 
	 * @return 随机字符串
	 */
	public static String getRandomString(int length) {
		if (length <= 0) {
			return "";
		}
		char[] randomChar = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd',
				'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm' };
		Random random = new Random();
		StringBuffer stringBuffer = new StringBuffer();
		for (int i = 0; i < length; i++) {
			stringBuffer.append(randomChar[Math.abs(random.nextInt()) % randomChar.length]);
		}
		return stringBuffer.toString();
	}

	/**
	 * 根据指定长度 分隔字符串
	 * 
	 * @param str
	 *            需要处理的字符串
	 * @param length
	 *            分隔长度
	 * 
	 * @return 字符串集合
	 */
	public static List<String> splitString(String str, int length) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < str.length(); i += length) {
			int endIndex = i + length;
			if (endIndex <= str.length()) {
				list.add(str.substring(i, i + length));
			} else {
				list.add(str.substring(i, str.length() - 1));
			}
		}
		return list;
	}

	/**
	 * 将字符串List转化为字符串，以分隔符间隔.
	 * 
	 * @param list
	 *            需要处理的List.
	 *            
	 * @param separator
	 *            分隔符.
	 * 
	 * @return 转化后的字符串
	 */
	public static String toString(List<String> list, String separator) {
		StringBuffer stringBuffer = new StringBuffer();
		for (String str : list) {
			stringBuffer.append(separator + str);
		}
		stringBuffer.deleteCharAt(0);
		return stringBuffer.toString();
	}
	
	/**
	 * 判断是否为空
	 * @param obj
	 * @return
	 */
	public static boolean empty(Object obj) {
		if (obj == null) {
			return true;
		} else if (obj instanceof String && (obj.equals("") || obj.equals("0"))) {
			return true;
		} else if (obj instanceof Number && ((Number) obj).doubleValue() == 0) {
			return true;
		} else if (obj instanceof Boolean && !((Boolean) obj)) {
			return true;
		} else if (obj instanceof Collection && ((Collection<?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Map && ((Map<?,?>) obj).isEmpty()) {
			return true;
		} else if (obj instanceof Object[] && ((Object[]) obj).length == 0) {
			return true;
		}
		return false;
	}
	
	/**
	 * 去空格
	 * @param text
	 * 				
	 * @return
	 */
	public static String trim(String text) {
		if (text == null) {
			return "";
		}
		return text.trim();
	}
	
	/**
	 * 
	 * @param s
	 * @return
	 */
	public static int intval(String s) {
		return intval(s, 10);
	}
	public static int intval(String s, int radix) {
		if (s == null || s.length() == 0) {
			return 0;
		}
		if (radix == 0) {
			radix = 10;
		} else if (radix < Character.MIN_RADIX) {
			return 0;
		} else if (radix > Character.MAX_RADIX) {
			return 0;
		}
		int result = 0;
		int i = 0, max = s.length();
		int limit;
		int multmin;
		int digit;
		boolean negative = false;
		if (s.charAt(0) == '-') {
			negative = true;
			limit = Integer.MIN_VALUE;
			i++;
		} else {
			limit = -Integer.MAX_VALUE;
		}
		if (i < max) {
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				return 0;
			} else {
				result = -digit;
			}
		}
		multmin = limit / radix;
		while (i < max) {
			digit = Character.digit(s.charAt(i++), radix);
			if (digit < 0) {
				break;
			}
			if (result < multmin) {
				result = limit;
				break;
			}
			result *= radix;
			if (result < limit + digit) {
				result = limit;
				break;
			}
			result -= digit;
		}
		if (negative) {
			if (i > 1) {
				return result;
			} else {
				return 0;
			}
		} else {
			return -result;
		}
	}
	
	/**
	 * 字符串长度
	 * 
	 * @param text 字符串
	 * 			
	 * @return	字符串长度
	 */
	public static int strlen(String text) {
		return strlen(text, CHARSET);
	}
	
	public static int strlen(String text, String charsetName) {
		if (text == null || text.length() == 0) {
			return 0;
		}
		int length = 0;
		try {
			length = text.getBytes(charsetName).length;
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return length;
	}
	
	/**
	 * 截断字符串
	 * 
	 * @param text
	 * 				需要截断的字符串
	 * @param length
	 * 				字符串长度
	 * 
	 * @return	截断后的字符串
	 */
	public static String cutstr(String text, int length) {
		return cutstr(text, length, " ...");
	}
	
	public static String cutstr(String text, int length, String dot) {
		int strBLen = strlen(text);
		if (strBLen <= length) {
			return text;
		}
		int temp = 0;
		StringBuffer sb = new StringBuffer(length);
		char[] ch = text.toCharArray();
		for (char c : ch) {
			sb.append(c);
			if (c > 256) {
				temp += 2;
			} else {
				temp += 1;
			}
			if (temp >= length) {
				if (dot != null) {
					sb.append(dot);
				}
				break;
			}
		}
		return sb.toString();
	}
	
	/**
	 *  是否为数字
	 *  
	 * @param obj
	 * 				对象
	 * 
	 * @return 为数字返回ture,否则返回false
	 */
	public static boolean isNumeric(Object obj) {
		if (obj instanceof String && !obj.equals("")) {
			String temp = (String) obj;
			if (temp.endsWith("d") || temp.endsWith("f")) {
				return false;
			} else {
				try {
					Double.parseDouble(temp);
				} catch (Exception e) {
					return false;
				}
			}
			return true;
		} else if (obj instanceof Number) {
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 是否是邮箱
	 * 
	 * @param email	
	 * 		    邮箱地址
	 * 
	 * @return 邮箱格式正确返回ture,否则返回false
	 */
	public static boolean isEmail(String email) {
		return CommonUtil.strlen(email) > 6 && email.matches("^[\\w\\-\\.]+@[\\w\\-\\.]+(\\.\\w+)+$");
	}
	
	/**
	 * 使用指定的编码机制对 application/x-www-form-urlencoded字符串解码。
	 * 
	 * @param s
	 * 			url地址
	 * 
	 * @return	解码后的url地址
	 */
	public static String urlEncode(String s) {
		return urlEncode(s, CHARSET);
	}
	
	public static String urlEncode(String s, String enc) {
		if (!empty(s)) {
			try {
				return URLEncoder.encode(s, enc);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	/**
	 * 使用指定的编码机制将字符串转换为 application/x-www-form-urlencoded格式。
	 * 
	 * @param s
	 * 			url地址
	 * 
	 * @return 编码后的url地址
	 */
	public static String urlDecode(String s) {
		return urlDecode(s, CHARSET);
	}
	
	public static String urlDecode(String s, String enc) {
		if (!empty(s)) {
			try {
				return URLDecoder.decode(s, enc);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return s;
	}
	
	/**
	 * 字符前加反斜线，这些字符是单引号（'）、双引号（"）、反斜线（\）与 NUL（NULL 字符）
	 * 
	 * @param text
	 * 				需要处理的字符串
	 * 
	 * @return	处理后的字符串
	 */
	public static String addSlashes(String text) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
				case '\'':
				case '"':
				case '\\':
					sb.append("\\");
				default:
					sb.append(character);
					break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 去掉单引号（'）、双引号（"）、反斜线（\）与 NUL（NULL 字符）前面的反斜线
	 * 
	 * @param text
	 * 			要处理的字符串	
	 * 
	 * @return 处理后的字符串	
	 */
	public static String stripSlashes(String text) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length());
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
				case '\'':
					sb.append("'");
					break;
				case '"':
					sb.append('"');
					break;
				case '\\':
					sb.append(iterator.next());
					break;
				default:
					sb.append(character);
					break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 参数characters列表中的字符前都加上反斜线
	 * 
	 * @param text
	 * 			规定要检查的字符串
	 * 
	 * @param characters
	 * 			需要在前面添加反斜杠的字符数组
	 * 
	 * @return	处理后的字符串
	 */
	public static String addCSlashes(String text, char[] characters) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			for (char c : characters) {
				if (character == c) {
					sb.append("\\");
					break;
				}
			}
			sb.append(character);
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 去掉字符前的反斜线
	 * 
	 * @param text
	 * 			规定要检查的字符串
	 * 
	 * @return  处理后的字符串
	 */
	public static String stripCSlashes(String text) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length());
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		boolean flag = true;
		while (character != StringCharacterIterator.DONE) {
			if (character == '\\' && flag) {
				flag = false;
			} else {
				flag = true;
				sb.append(character);
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 函数把一些预定义的字符转换为 HTML 实体
	 * 预定义的字符是：
	 *		& （和号）   成为 &amp;
	 *		" （双引号） 成为 &quot;
	 *		' （单引号） 成为 &#039;
	 *		< （小于）   成为 &lt;
	 *		> （大于）   成为 &gt;
	 *
	 * @param string 
	 * 				规定要检查的字符串
	 * 			
	 * @return 处理后的字符串
	 */
	public static String htmlSpecialChars(String string) {
		return htmlSpecialChars(string, 1);
	}
	public static String htmlSpecialChars(String text, int quotestyle) {
		if (text == null || text.equals("")) {
			return "";
		}
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			switch (character) {
				case '&':
					sb.append("&amp;");
					break;
				case '<':
					sb.append("&lt;");
					break;
				case '>':
					sb.append("&gt;");
					break;
				case '"':
					if (quotestyle == 1 || quotestyle == 2) {
						sb.append("&quot;");
					} else {
						sb.append(character);
					}
					break;
				case '\'':
					if (quotestyle == 2) {
						sb.append("&#039;");
					} else {
						sb.append(character);
					}
					break;
				default:
					sb.append(character);
					break;
			}
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 加强版htmlspecialchars
	 * 		把html特殊字符和unicode编码中被错误转换为&amp;的部分转换会&
	 * 			
	 * @param obj
	 * 				需要处理的对象
	 * 
	 * @return	处理后的对象
	 */
	
	@SuppressWarnings("unchecked")
	public static Object sHtmlSpecialChars(Object obj) {
		if (obj instanceof String) {
			return htmlSpecialChars((String) obj).replaceAll(
					"&amp;((#(\\d{3,5}|x[a-fA-F0-9]{4})|[a-zA-Z][a-z0-9]{2,5});)", "&$1");
		} else if (obj instanceof Map) {
			Map<Object, Object> temp = (Map<Object, Object>) obj;
			Set<Object> keys = temp.keySet();
			for (Object key : keys) {
				temp.put(key, sHtmlSpecialChars(temp.get(key)));
			}
			return temp;
		} else if (obj instanceof List) {
			List<Object> temp = new ArrayList<Object>();
			for (Object str : (List<?>) obj) {
				temp.add(sHtmlSpecialChars(str));
			}
			return temp;
		} else if (obj instanceof Object[]) {
			Object[] temp = (Object[]) obj;
			for (int i = 0; i < temp.length; i++) {
				temp[i] = sHtmlSpecialChars(temp[i]);
			}
			return temp;
		} else {
			return obj;
		}
	}
	
	/**
	 * 获取随即数
	 * 
	 * @param length
	 * 				长度
	 * 	
	 * @param isOnlyNum
	 * 					是否只有数字
	 * 
	 * @return 随即字符串
	 */
	public static String getRandStr(int length, boolean isOnlyNum) {
		int size = isOnlyNum ? 10 : 34;
		StringBuffer hash = new StringBuffer(length);
		for (int i = 0; i < length; i++) {
			hash.append(randChars.charAt(random.nextInt(size)));
		}
		return hash.toString();
	}
	
	/**
	 * 去除标签
	 * 
	 * @param content
	 * 				需要处理的内容
	 * 
	 * @return	处理后的字符串
	 */
	public static String stripTags(String content) {
		return content == null ? "" : content.replaceAll("<[\\s\\S]*?>", "");
	}
	
	/**
	 * 过滤,转义字符串
	 * 
	 * @param string
	 * 				需要过滤的字符串
	 * 
	 * @param filterFlag
	 * 				是否替换系统管理员定义的字符串
	 * 
	 * @param htmlFlag
	 * 				为true时去掉\,<>,[]以及里面包含的字符串,再进行html代码转义
	 * 
	 * @return	处理后的字符串
	 */
	public static String getStr(String string, boolean filterFlag, boolean htmlFlag) {
		string = CommonUtil.trim(string);
		if (htmlFlag) {
			string = string.replaceAll("(?is)(\\<[^\\<]*\\>|\\r|\\n|\\s|\\[.+?\\])", " ");
			string = (String) sHtmlSpecialChars(string);
		} else {
			string = (String) sHtmlSpecialChars(string);
		}
		if (filterFlag) {
			//这里是系统里面定义关键字的替换(暂未实现)
		}
		return string.trim();
	}
	
	/**
	 * 检查用户名是否是系统保留的用户名
	 * 
	 * @param content
	 * 				
	 * @param censoruser
	 * 					
	 * @return
	 */
	public static boolean checkUser(String content,String censoruser) {
		if(content!=null&&content.length()>0){
			String censorexp = censoruser.replaceAll("\\*", ".*");
			censorexp = censorexp.replaceAll("(\r\n|\r|\n)", "|");
			censorexp = censorexp.replaceAll("\\s", "");
			censorexp = "^(" + censorexp + ")";
			String guestexp = "\\xA1\\xA1|^Guest|^\\xD3\\xCE\\xBF\\xCD|\\xB9\\x43\\xAB\\xC8";
			if (CommonUtil.matches(content, "^\\s*$|^c:\\con\\con$|[%,\\\\'\\*\"\\s\\t\\<\\>\\&]|"+ guestexp) 
					|| (censoruser.length()>0 && CommonUtil.matches(content, censorexp))) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 获取站点url
	 * 
	 * @param request
	 * 				请求
	 * @return url
	 */
	public static String getSiteUrl(HttpServletRequest request) {
		int port = request.getServerPort();
		return request.getScheme() + "://" + request.getServerName() + (port == 80 ? "" : ":" + port) + request.getContextPath() + "/";
	}
	
	/**
	 * 正则表达式匹配方法
	 * @param content
	 * 				比对的字符
	 * @param regex
	 * 				正则表达式
	 * @return 比对成功返回ture,否则返回false
	 */
	public static boolean matches(String content, String regex) {
		boolean flag = false;
		try {
			flag = new Perl5Matcher().contains(content, new Perl5Compiler().compile(regex));
		} catch (MalformedPatternException e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	/**
	 * 获取用户邀请KEY
	 * 
	 * @param userId
	 * 				用户ID	
	 * 
	 * @return MD5加密后的字符串
	 */
	public static String getInviteKey(int userId) {
		return ToolBox.getMD5String("ff98deHgCIQOEP3d" + "|" + (userId == 0 ? "" : userId)).substring(8, 24);
	}
	
	/**
	 * MD5加密
	 * @param arg0
	 * @return
	 */
	public static String md5(String arg0) {
		return ToolBox.getMD5String(arg0);
	}
	
	/**
	 * 获取授权码
	 * 
	 * @param string
	 * 				
	 * @param operation
	 * 					
	 * @param key
	 * 				
	 * @param expiry
	 * 				
	 * @return 
	 */
	public static String authCode(String string, String operation, String key, int expiry) {
		long currentTime = System.currentTimeMillis();
		int time = (int) (currentTime / 1000);
		try {
			int ckey_length = 4;
			key = md5((key == null ? "123" : key));
			String keya = md5(key.substring(0, 16));
			String keyb = md5(key.substring(16, 32));
			String keyc = ckey_length > 0 ? ("DECODE".equals(operation) ? string.substring(0, ckey_length)
					: md5(String.valueOf(currentTime)).substring(32 - ckey_length)) : "";
			String cryptkey = keya + md5(keya + keyc);
			int key_length = cryptkey.length();
			string = "DECODE".equals(operation) ? Base64.decode(string.substring(ckey_length), "UTF-8") : 
				(expiry > 0 ? expiry + time : "0000000000")	+ md5(string + keyb).substring(0, 16) + string;
			int string_length = string.length();
			StringBuffer result = new StringBuffer(string_length);
			int range = 128;
			int[] rndkey = new int[range];
			for (int i = 0; i < range; i++) {
				rndkey[i] = cryptkey.charAt(i % key_length);
			}
			int tmp;
			int[] box = new int[range];
			for (int i = 0; i < range; i++) {
				box[i] = i;
			}
			for (int i = 0, j = 0; i < range; i++) {
				j = (j + box[i] + rndkey[i]) % range;
				tmp = box[i];
				box[i] = box[j];
				box[j] = tmp;
			}
			for (int a = 0, i = 0, j = 0; i < string_length; i++) {
				a = (a + 1) % range;
				j = (j + box[a]) % range;
				tmp = box[a];
				box[a] = box[j];
				box[j] = tmp;
				result.append((char) ((int) string.charAt(i) ^ (box[(box[a] + box[j]) % range])));
			}
			if ("DECODE".equals(operation)) {
				int resulttime = intval(result.substring(0, 10));
				if ((resulttime == 0 || resulttime - time > 0) 
						&& result.substring(10, 26).equals(md5(result.substring(26) + keyb).substring(0, 16))) {
					return result.substring(26);
				} else {
					return "";
				}
			} else {
				return keyc	+ (Base64.encode(result.toString(), "UTF-8")).replaceAll("=", "");
			}
		} catch (Exception e) {
			return "";
		}
	}
	
	/**
	 * 获取当前的IP地址
	 * 
	 * @param request
	 * 				request请求
	 * @return IP地址
	 */
	public static String getOnlineIP(HttpServletRequest request) {
		return getOnlineIP(request, false);
	}
	
	/**
	 * 获取当前的IP地址
	 * 
	 * @param request
	 * 				request请求
	 * @param format
	 * 				是否格式化
	 * @return	IP地址
	 */
	public static String getOnlineIP(HttpServletRequest request, boolean format) {
		String onlineip = request.getRemoteAddr();
		if (onlineip == null) {
			onlineip = request.getHeader("x-forwarded-for");
			if (empty(onlineip) || "unknown".equalsIgnoreCase(onlineip)) {
				onlineip = request.getHeader("X-Real-IP");
			}
			if (empty(onlineip) || "unknown".equalsIgnoreCase(onlineip)) {
				onlineip = request.getRemoteAddr();
			}
			onlineip = onlineip != null && onlineip.matches("^[\\d\\.]{7,15}$") ? onlineip : "unknown";
		}
		if (format) {
			String[] ips = onlineip.split("\\.");
			String stip = "000";
			StringBuffer temp = new StringBuffer();
			for (int i = 0; i < 3; i++) {
				int ip = 0;
				if (i < ips.length) {
					ip = intval(ips[i]);
				}
				temp.append(sprintf(stip, ip));
			}
			return temp.toString();
		} else {
			return onlineip;
		}
	}
	
	/**
	 * 数字格式化
	 * 
	 * @param format
	 *				format						
	 * @param number
	 * 				double
	 * @return 格式化后的字符
	 */
	public static String sprintf(String format, double number) {
		return new DecimalFormat(format).format(number);
	}
	
	public static boolean ipAccess(String ip, Object ipAccess) {
		return empty(ipAccess) ? true : ip.matches("^("
				+ pregQuote(String.valueOf(ipAccess), '/').replaceAll("\r\n", "|").replaceAll(" ", "") + ").*");
	}
	
	public static boolean ipBanned(String ip, Object ipBanned) {
		return empty(ipBanned) ? false : ip.matches("^("
				+ pregQuote(String.valueOf(ipBanned), '/').replaceAll("\r\n", "|").replaceAll(" ", "") + ").*");
	}
	
	public static String pregQuote(String text, char... delimiter) {
		StringBuffer sb = new StringBuffer(text.length() * 2);
		StringCharacterIterator iterator = new StringCharacterIterator(text);
		char character = iterator.current();
		while (character != StringCharacterIterator.DONE) {
			boolean flag = false;
			for (char c : pregChars) {
				if (character == c) {
					flag = true;
					break;
				}
			}
			if (!flag && delimiter != null) {
				for (char d : delimiter) {
					if (character == d) {
						flag = true;
						break;
					}
				}
			}
			if (flag) {
				sb.append('\\');
			}
			sb.append(character);
			character = iterator.next();
		}
		return sb.toString();
	}
	
	/**
	 * 检查用户头像是否存在
	 * @param userId
	 * @param url
	 * @return
	 */
	public static boolean ckavatar(String userId, String url){
		File file = new File(url + "upload/avatar/" + avatarFile(userId, "2", "middle"));
		return file.exists();
	}
	
	public static String avatar(String userId, String sex, String state, String sizeType, boolean returnUrl) {
		String avatarPath = "/xmp/upload/avatar/" + avatarFile(userId, state, sizeType);
		return returnUrl ? avatarPath : "<img src=\"" + avatarPath + 
				"\" class=\"imgBox\" onerror=\"this.onerror=null;this.src=\'/xmp/upload/avatar/noavatar_" + sex + "_" + sizeType + ".jpg\'\">";
	}
	
	public static String avatarFile(String userId, String state, String sizeType) {
		StringBuffer avater = new StringBuffer();
		avater.append(userId + "/" + userId);
		avater.append("_avatar_" + sizeType + "_" + state + ".jpg");
		String avatarPath = avater.toString();
		return avatarPath;
	}
	
	public static int time() {
		return (int) (System.currentTimeMillis() / 1000);
	}
	
	public static SimpleDateFormat getSimpleDateFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf;
	}
	
	public static String gmdate(SimpleDateFormat sdf, Date timestamp) {
		return sdf.format(timestamp);
	}
	
	public static String gmdate(String format, Date timestamp) {
		return getSimpleDateFormat(format).format(timestamp);
	}
	
	public static String sgmdate(String dateformat, Date timestamp) {
		return sgmdate(dateformat, timestamp, true);
	}
	
	public static String sgmdate(String dateformat, Date timestamp, boolean format) {
		int currentTime = time();
		String result = null;
		if (format) {
			int time = (Integer) currentTime - (int)(timestamp.getTime() / 1000);
			if (time > 86400) {
				result = gmdate(dateformat, timestamp);
			} else if (time > 3600) {
				result = (time / 3600) + "小时前";
			} else if (time > 60) {
				result = (time / 60) + "分钟前";
			} else if (time > 0) {
				result = time + "秒前";
			} else {
				result = "刚刚";
			}
		} else {
			result = gmdate(dateformat, timestamp);
		}
		return result;
	}
	
	public static String getImageType(File imgFile) {
		String imageType = null;
		try {
			ImageInputStream iis = ImageIO.createImageInputStream(imgFile);
			Iterator<ImageReader> iter = ImageIO.getImageReaders(iis);
			if (iter.hasNext()) {
				ImageReader reader = iter.next();
				imageType = reader.getFormatName().toLowerCase();
			}
			iis.close();
		} catch (IOException e) {
		}
		return imageType;
	}
	
	public static String getPath(HttpServletRequest request) {
		String path = request.getSession().getServletContext().getRealPath("/");
		return path;
	}
	
	public static long getByteSizeByBKMG(String unitSize) {
		long maxSize = 0;
		String maxFileSize = unitSize;
		Matcher matcher = Pattern.compile("\\d+([bkmg]?)").matcher(maxFileSize.toLowerCase());
		if (matcher.matches()) {
			String ch = matcher.replaceAll("$1");
			if (ch.equals("k")) {
				maxSize = intval(maxFileSize) * 1024;
			} else if (ch.equals("m")) {
				maxSize = intval(maxFileSize) * 1024 * 1024;
			} else if (ch.equals("g")) {
				maxSize = intval(maxFileSize) * 1024 * 1024;
			} else {
				maxSize = intval(maxFileSize);
			}
		}
		return maxSize;
	}
	
	/**
	 * 文件大小和文件类型验证
	 * @author tyj
	 * @param file 文件
	 * @param fileType 文件类型,格式为"*.gif,*.jpg"
	 * @throws BusException
	 * @date Nov 10, 2010
	 * @modify
	 */
	public static long validateFile(MultipartFile file, String fileType) throws BusException {
		//检验文件大小
	    if(file.getSize() < 0 || file.getSize() > 1024*1024*5){
	    	throw new BusException("上传的文件太大了！");
	    }
	    //获取文件名
	    String filename = file.getOriginalFilename();
	    //获取文件扩展名
	    String extName = filename.substring(filename.lastIndexOf("."));
	    //文件类型验证
	    if(fileType.indexOf(extName) == -1){
	    	throw new BusException("不支持"+ extName +"类型的文件上传！");
	    }
	    return file.getSize();
	}
	
	/**
	 * 文件上传
	 * @author tyj
	 * @param request
	 * @param file 上传文件的控件名
	 * @param directory 指定上传文件夹,为null时默认上传到upload文件夹
	 * @param fileType 文件类型，例："*.gif,*.jpg,*.png"
	 * @return Map 文件属性,包含(fileName, fileSize, filePath)
	 * @throws BusException
	 * @date Nov 11, 2010
	 * @modify
	 */
	public static Map<String, Object> uploadFile(HttpServletRequest request, String file, String directory, String fileType) throws BusException {
		Map<String, Object> map = new HashMap<String, Object>();
		//转型为MultipartHttpRequest
        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;   
        //根据页面的name名称得到上传的文件  
        MultipartFile mFile  =  multipartRequest.getFile(file);
        //文件类型验证
        long size = validateFile(mFile, fileType);
        //拼装文件名
        String fileName = ToolBox.getFileRandName() + mFile.getOriginalFilename().substring(mFile.getOriginalFilename().indexOf("."));
        //文件路径
        String path = request.getSession().getServletContext().getRealPath("/");
		//硬盘保存路径
		String dstFile = "";
		//数据库保存路径
		String relPath = "";
		//如果directory不为空,则创建子文件夹
		if(directory != null && !"".equals(directory)){
	        File Subfile = new File(path + "upload/" + directory);
			if (!Subfile.exists()) {
				Subfile.mkdir();
			}
			dstFile = path + "upload/" + directory + "/" + fileName;
			relPath = "upload/" + directory + "/" + fileName;
		}else{
			dstFile = path + "upload/" + fileName;
			relPath = "upload/" + fileName;
		}
		File addFile = new File(dstFile);
        try {
        	//文件保存
			mFile.transferTo(addFile);
		} catch (Exception e) {
			throw new BusException("上传文件失败！");
		}
		map.put("fileName", mFile.getOriginalFilename());
		map.put("fileSize", size);
		map.put("filePath", relPath);
		return map;
	}
	
	public static int rand(int max) {
		return rand(0, max);
	}
	
	public static int rand(int min, int max) {
		if (min < max) {
			return random.nextInt(max - min + 1) + min;
		} else {
			return min;
		}
	}
	
	 /**
	 * 随机指定范围内N个不重复的数
	 * 最简单最基本的方法
	 * @param min 指定范围最小值
	 * @param max 指定范围最大值
	 * @param n 随机数个数
	 */
	public static int[] randomCommon(int min, int max, int n){
		if (n > (max - min + 1) || max < min) {
            return null;
        }
		int[] result = new int[n];
		int count = 0;
		while(count < n) {
			int num = (int) (Math.random() * (max - min)) + min;
			boolean flag = true;
			for (int j = 0; j < n; j++) {
				if(num == result[j]){
					flag = false;
					break;
				}
			}
			if(flag){
				result[count] = num;
				count++;
			}
		}
		return result;
	}
	
	 public static int[] arraySubtract(int[] array1, int[] array2) {
        ArrayList<Integer> list = new ArrayList<Integer>();
        //选出属于数组1但不属于数组2的元素
        for(int i = 0; i < array1.length; ++i) {
            boolean bContained = false;
            for(int j = 0; j < array2.length; ++j) {
                if (array1[i] == array2[j]) {
                    bContained = true;
                    break;
                }
            }
            if (!bContained) {
                list.add(array1[i]);
            }
        }
         
        int res[] = new int[list.size()];
        for(int i = 0; i < list.size(); ++i)
            res[i] = list.get(i);
        return res;
    }

	
}