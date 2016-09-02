package com.app.service.impl;

import com.app.dao.AppPackgeMapper;
import com.app.dao.UserAppMapper;
import com.app.dao.UserAppVipMapper;
import com.app.dao.UserPackgeVipMapper;
import com.app.model.AppInfo;
import com.app.model.PackgeInfo;
import com.app.model.UserApp;
import com.app.model.UserPackgeVip;
import com.app.service.IUserApp;
import com.app.util.TextUtils;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Service("userAppServ")
public class UserAppService extends BaseServiceImpl<UserApp> implements
		IUserApp {

	@Resource
	private UserAppMapper userAppMapper;

	@Resource
	private UserAppVipMapper userAppVipMapper;

	@Resource
	private UserPackgeVipMapper userPackgeVipMapper;

	@Resource
	private AppPackgeMapper appPackgeMapper;

	@Resource
	public void setMapper(UserAppMapper userAppMapper) {
		super.setSqlMapper(userAppMapper);
	}

	/**
	 * 增加用户使用APP记录
	 */
	public UserApp queryApp(UserApp userApp) {
		// 查询是否存在记录
		UserApp app = userAppMapper.selectByBean(userApp);


		// 减少免费次数，增加使用次数
		Integer appSurplus = Integer.parseInt(app.getAppSurplus());
		if (app.getAppSurplus() != null && appSurplus > 0) {
			// 剩余免费次数不为0，则减一
			Integer i = Integer.parseInt(app.getAppSurplus()) - 1;
			app.setAppSurplus(i.toString());
		}
		// 使用次数加一
		Integer f = Integer.parseInt(app.getAppFreeTime()) + 1;
		app.setAppFreeTime(f.toString());
		userAppMapper.update(app);
		return app;
	}

	/**
	 * 
	 * 根据传过来的数据到表User_app中查询是否存在这条记录 如果存在则首先查询所属专区,得到专区名字。
	 * 在到专区表中查询，查询对应的专区是否包月已经包月并且专区状态Vip_stauts是否是可用，可用就返回可已使用，
	 * 如果对应专区没有包月，则查询user_app的App_ surplus如过免费使用次数不<=0则返回可以使用， 否则返回不可以使用
	 * 如果在UserApp表中没有查询到相对应记录则根据app_id,app_name到表app_info中查询查询收费类型，
	 * 如果是免费则返回可以使用，收费则在表中查询是否存在免费使用次数是则返回可以使用， 不是则返回不可以使用,同时在user_app表中增加一条记录
	 */

	public UserApp queryUserApp(UserApp userApp) {
		userApp = userAppMapper.selectByBean(userApp);
		return userApp;
	}

	/**
	 * 
	 * @param appId
	 * @return app所属专区
	 */
	public PackgeInfo queryPackgeByApp(String appId) {
		PackgeInfo packge = appPackgeMapper.selectByAppId(appId);
		return packge;
	}

	/**
	 * 
	 * @param userId
	 *            用户ID
	 * @param packgeId
	 *            专区名
	 * @return 用户对专区的包月情况
	 */
	public UserPackgeVip queryUPVip(String userId, String packgeId) {
		UserPackgeVip upv = userPackgeVipMapper.selectById(new UserPackgeVip(
				userId, packgeId));
		return upv;
	}

	@Override
	public UserApp queryAppByBean(UserApp app)  {

		return userAppMapper.queryAppByBean(app);
	}

	public Map<String,Object> validate(AppInfo appInfo,  UserApp app)throws Exception{

		Map<String,Object> map =	new HashMap<String,Object>();

		String message = "fail";
		String	available = "0";
		int	applyNum = -1;
		//判断有没有免费次数
		String freeFrequency = appInfo.getFreeFrequency();
		//没有免费次数
		if ("0".equals(freeFrequency) || TextUtils.isEmpty(freeFrequency)) {
			available = "0";
			applyNum = 0;
		} else {
			available = "1";
			message = "success";

			applyNum = Integer.parseInt(freeFrequency);

			//有没有剩余免费次数
			//   UserApp userApp = userAppServ.queryApp(app);
			UserApp userApp = queryAppByBean(app);

			if (userApp == null) {
				app.setAppSurplus((applyNum - 1) + "");
				//insert
				app.setTerm("1");

				create(app);
			} else {
				String appSurplus = userApp.getAppSurplus();
				if ("0".equals(appSurplus)) {
					available = "0";
					applyNum = -1;
				} else {
					applyNum = Integer.parseInt(appSurplus);
					app.setAppSurplus((Integer.parseInt(appSurplus) - 1) + "");
					//update
					app.setTerm(Integer.parseInt(userApp.getTerm()) + 1 + "");
					modify(app);
				}
			}

		}
		map.put("message", message);
		map.put("available", available);
		map.put("applyNum", applyNum);

		return map;
	}

}
