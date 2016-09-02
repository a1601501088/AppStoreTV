package com.app.action.inter;

import com.app.model.UserPay;
import com.app.service.IUserPay;
import com.app.util.DateUtil;
import com.basicframe.common.action.BaseAction;
import com.basicframe.utils.ToolBox;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Calendar;


@Controller
public class UserPayInter extends BaseAction{

	@Resource
	private IUserPay userPayServ ;

	/**
	 * 失效
	 * 支付日志
	 * @param json
	 * @param request
	 * @param response
     */
	@RequestMapping(value = "/createUserPay",method=RequestMethod.POST)
	public void createUserPay(@RequestParam("json")String json ,HttpServletRequest request ,HttpServletResponse response){
		String code = "400";
		String message = "fail" ;
		try {
			JSONObject obj = JSONObject.fromObject(json);
			String userId = obj.getString("userId");
			String userToken = obj.getString("userToken");
			String payName = obj.getString("payName");
			String payPice = obj.getString("payPice");
			String payType = obj.getString("payType");
			String shareType = obj.getString("shareType");
			String commodity = obj.getString("commodity");//商品ID
			
			UserPay userPay = new UserPay();
			Calendar cal = Calendar.getInstance();
			userPay.setUserId(userId);
			userPay.setUserToken(userToken);
			userPay.setPayName(payName);
			userPay.setPayTime(ToolBox.toLocaleString(cal.getTime()));//购买时间
			userPay.setPayPice(payPice);
			userPay.setPayType(payType);
			userPay.setShareType(shareType);
			userPay.setPayPat("0");
			userPay.setExpireTime(DateUtil.getExpire());//到期时间
			userPay.setCommodity(commodity);

			//int payId = userPayServ.create2(userPay);
			code = "200";
			message = "success" ;
		} catch (Exception e) {
			e.printStackTrace();
			code = "500" ;
			message = "error";
		}
		JSONObject result = new JSONObject();
		result.put("code", code);
		result.put("message", message);
		ajaxJson(result.toString(), response);
	}
	
}
