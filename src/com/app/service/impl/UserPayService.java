package com.app.service.impl;

import com.app.dao.UserPayMapper;
import com.app.model.AppInfo;
import com.app.model.PackgeInfo;
import com.app.model.UserPay;
import com.app.service.IAppInfoService;
import com.app.service.IPackgeInfo;
import com.app.service.IUserPay;
import com.app.util.DateUtil;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;




@Service("userPayServ")
public class UserPayService extends BaseServiceImpl<UserPay> implements IUserPay{
    @Resource
	private UserPayMapper userPayMapper;
    @Resource
    private IAppInfoService appInfoServ;
    @Resource
    private  IPackgeInfo packgeServ;
	@Resource
	public void setMapper(UserPayMapper userPayMapper) {
		super.setSqlMapper(userPayMapper);
	}
	
	public  int create2(UserPay userPay ) throws Exception{

		return  userPayMapper.create2(userPay);
	}

	@Override
	public UserPay queryLatelyByUserId(String userId) throws Exception {
		return userPayMapper.queryLatelyByUserId(userId);
	}

    /**
     * 创建支付日志
     * @param userId
     * @param appId
     * @param userToken
     * @param payType
     * @param commodiy
     * @param shareType
     * @param status
     * @return
     * @throws Exception
     */
	@Override
	public boolean payLog(String userId,String appId, boolean isApp,String userToken, String payType, String commodiy, String shareType,String status) throws Exception{
        List<AppInfo> appInfoList = null;
        String name = null;
        String price = null;
        if (isApp){
            //通过appId查找appName
             appInfoList = appInfoServ.queryAppById(appId);
            //如果没有找到,则返回返回false
            if (appInfoList.size()!=1) {
                throw new IllegalStateException("通过appId查找到的appName大于1条或没有此app");
            }
            name = appInfoList.get(0).getAppName();
            price = appInfoList.get(0).getPrice();
        }else {
            PackgeInfo packgeInfo = packgeServ.queryById(appId);
            if (null==packgeInfo){
                throw new IllegalStateException("没有这个专区");
            }
            name = packgeInfo.getPackgeName();
            price = packgeInfo.getPackgePrice();
        }
        if (null==name){
            throw new IllegalStateException("没有找到app与专区");
        }
        //如果找到,则进行添加包月日志
        else{

            UserPay userPay = new UserPay();
            userPay.setUserId(userId);
            userPay.setPayPat("1");
            userPay.setUserToken(userToken);
            userPay.setPayType(payType);
            userPay.setCommodity(commodiy);
            userPay.setShareType(shareType);
            userPay.setPayName(name);
            userPay.setPayPice(price);
            userPay.setExpireTime(DateUtil.getExpire());
            userPay.setPayStatus(status);
            //把信息插入userpay表
            int count = userPayMapper.create2(userPay);
            if (count==1){
                return  true;
            }else {
                throw new IllegalStateException("插入userpay失败");
            }

        }

	}

}
