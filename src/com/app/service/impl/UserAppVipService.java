package com.app.service.impl;

import javax.annotation.Resource;

import com.app.model.AppInfo;
import com.app.model.UserApp;
import com.app.model.UserPay;
import com.app.service.IAppInfoService;
import com.app.service.IUserPay;
import com.app.util.DateUtil;
import com.basicframe.common.exception.BusException;
import org.springframework.stereotype.Service;

import com.app.dao.UserAppVipMapper;
import com.app.model.UserAppVip;
import com.app.service.IUserAppVip;
import com.basicframe.common.service.IBaseService;
import com.basicframe.common.service.impl.BaseServiceImpl;

import java.util.*;




@Service("userAVipServ")
public class UserAppVipService extends BaseServiceImpl<UserAppVip> implements IUserAppVip{
	@Resource
	private  UserAppVipMapper userAppVipMapper;
	@Resource
	private IAppInfoService  appInfoServ;
	@Resource
	private IUserPay userPayServ;
	@Resource
	public void setMapper(UserAppVipMapper userAppVipMapper) {
		super.setSqlMapper(userAppVipMapper);
	}

	@Override
	public UserAppVip queryApp(UserApp app)throws Exception {


		return userAppVipMapper.queryApp(app);
	}

	@Override
	public List<UserAppVip> queryAppList(Map<String, Object> map) throws Exception{
		return userAppVipMapper.queryAppList(map);
	}
	@Override
	public  Map<String,String> wrapMon( UserApp userApp,UserAppVip uav)throws Exception{

		Map<String,String> map =	new HashMap<String,String>();

		String code = "400";
		String message = "fail";
		UserAppVip userAppVip = queryApp(userApp);
		UserPay userPay = new UserPay();
        //在app vip表中
		if (userAppVip!=null){
			//查询状态与时间有没有过期
			String vipStatus = userAppVip.getVipStatus();
			String appExpire = userAppVip.getAppExpire();
			Date date = DateUtil.formatDateTime(appExpire);
			//过期
			if (date.before(new Date())||vipStatus.equals("0")){
				//update
				modify(uav);
				code = "200";
				message = "success";
                //修改支付日志
                updateUserPay(userPay);
            }else {
				code = "400";
				message = "app已经包月，无需再包月";
			}
		}else {
			create(uav);
			code = "200";
			message = "success";
            //修改支付日志
            updateUserPay(userPay);
		}
		map.put("code",code);
		map.put("message",message);
		return map;
	}

    public void updateUserPay(UserPay userPay) throws Exception{
        Calendar calendar = Calendar.getInstance();
//        UserPay userPay = userPayServ.queryLatelyByUserId(userApp.getUserId());



			userPay.setPayPat("1");
            userPay.setPayTime(DateUtil.formatDateTimeToStr(calendar.getTime()));

            userPay.setExpireTime(DateUtil.getExpire());
            userPayServ.modify(userPay);
    }

	/**
	 * 通过userId与appId查找app包月记录
	 * @param userId 用户名
	 * @param appId
     * @return   UserAppVip
     */
	@Override
	public UserAppVip queryAppVipInfo(String userId, String appId) {
		Map<Object, Object> map = new HashMap<>();
		map.put("appId",appId);
		map.put("userId",userId);
		List<UserAppVip> userAppVipList = userAppVipMapper.queryAppVipInfo(map);
		if (userAppVipList.size()>0){
			return userAppVipList.get(0);
		}
		return null;
	}

	/**
	 * 对app进行包月  (插入)
	 * @param userId 用户名
	 * @param userToken token
	 * @param appId  appId
     * @return
     */
	@Override
	public boolean vip(String userId, String userToken, String appId)  {
		//通过appId查找appName
		List<AppInfo> appInfoList = appInfoServ.queryAppById(appId);
			//如果没有找到,则返回返回false
			if (appInfoList.size()==0) {
				return false;
			}
			//如果找到,则进行包月
			else{
				//获取appName
				String appName = appInfoList.get(0).getAppName();
                //获取包月价格
                String price = appInfoList.get(0).getPrice();
                UserAppVip userAppVip = new UserAppVip();
                userAppVip.setVipStatus("1");
                userAppVip.setUserId(userId);
                userAppVip.setUserToken(userToken);
                userAppVip.setAppName(appName);
                userAppVip.setAppExpire(DateUtil.getExpire());
                userAppVip.setAppId(appId);
               				userAppVip.setAppDay("30");
				userAppVip.setAppPrice(price);
                //插入appvip表
                int count = userAppVipMapper.createVip(userAppVip);
                if (count==1){


                    return true;
                }
            }
		return false;
	}


}
