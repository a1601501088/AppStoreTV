package com.basicframe.utils.page;

import com.basicframe.utils.CommonUtil;
import com.basicframe.utils.DateTool;


public class Function {
	
	
	public static String avatar(String uid, String sex, String state, String sizeType, boolean returnUrl) {
		return CommonUtil.avatar(uid, sex, state, sizeType, returnUrl);
	}
	
	public static int age(String date) {
		String ctime = DateTool.instance.getCurrentDateString().substring(0, 4);
		int age = 0;
		if(date != null && date != ""){
			age = Integer.parseInt(ctime) - Integer.parseInt(date.substring(0, 4));
		}
		return age;
	}

}
