package com.app.action.inter;

import com.Config;
import com.app.model.*;
import com.app.service.*;
import com.app.util.DateUtil;
import com.app.util.TextUtils;
import com.basicframe.common.action.BaseAction;
import com.ws.model.GetOrderInfoRsp;
import com.ws.model.Product;
import com.ws.service.WSOrderInfoService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.logging.Logger;

@Controller
public class UserAppInter extends BaseAction {
    Logger logger = Logger.getLogger("log");
    @Resource
    private IUserApp userAppServ;

    @Resource
    private IAppInfoService appInfoServ;

    @Resource
    private IUserAppVip userAVipServ;

    @Resource
    private IUserPackgeVip userPVipServ;
    @Resource
    private IPackgeInfo packgeServ;
    //联系人的webserivce接口
    @Resource
    private WSOrderInfoService wSOrderInfoServ;

    /**
     * 增加用户使用app次数
     */
    @RequestMapping("/addAppTime")
    public void getTime(@RequestParam("json") String json,
                        HttpServletRequest request, HttpServletResponse response) {

        JSONObject obj = JSONObject.fromObject(json);
        String appId = obj.getString("apId");
        String appName = obj.getString("appName");
        String userId = obj.getString("userId");
        String token = obj.getString("userToken");
        String code = "400";
        String message = "fail";

        UserApp app = new UserApp();
        try {
            app.setAppId(appId);
            app.setAppName(appName);
            app.setUserId(userId);
            app.setUserToken(token);
            app = userAppServ.queryApp(app);
            code = "200";
            message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "error";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("result", app);
        ajaxJson(result.toString(), response);
    }


    /**
     * 查询单个用户单个App是否能够使用
     *
     * @param json
     * @param request
     * @param response
     */
    @RequestMapping("/checkUserApp")
    public void checkUserApp(@RequestParam("json") String json,
                             HttpServletRequest request, HttpServletResponse response) {
        String code = "400";
        String message = "fail";

        // 获取请求中传递过来的参数
        JSONObject obj = JSONObject.fromObject(json);
        String userId = obj.getString("userId");
        String appId = obj.getString("appId");
        String tooken = obj.getString("userToken");
        String appName = obj.getString("appName");
        // 把参数封装成bean
        UserApp userApp = new UserApp();
        userApp.setUserId(userId);
        userApp.setUserToken(tooken);
        userApp.setAppId(appId);
        userApp.setAppName(appName);
        userApp.setAppFreeTime("1");

        try {
            // 判断记录是否存在
            UserApp app = userAppServ.queryUserApp(userApp);
            if (app != null) {
                // 如果对应专区没有包月，则查询user_app的App_surplus如过免费使用次数不<=0则返回可以使用，
                // 否则返回不可以使用
                Integer surplus = Integer.parseInt(app.getAppSurplus());
                if (surplus > 0) {
                    code = "200";
                    message = "success";
                    userApp.setAppFreeTime("0");
                } else {
                    // 查询App所属专区
                    PackgeInfo packge = userAppServ.queryPackgeByApp(appId);
                    // 根据专区名字和用户ID查询用户对专区的包月情况
                    UserPackgeVip upv = userAppServ.queryUPVip(
                            userApp.getUserId(), packge.getPackgeId());
                    // 判断Vip_stauts,得知是否可用 0.可用,1.不可用
                    if (upv != null && "0".equals(upv.getVipStatus())) {
                        code = "200";
                        message = "success";
                        userApp.setAppFreeTime("0");
                    }
                }
            } else {
                AppInfo appinfo = appInfoServ.queryById(appId);
                // 查询App所属专区
                PackgeInfo packge = userAppServ.queryPackgeByApp(appId);
                String chargeType = "";
                Integer free = -1;
                if (appinfo != null) {
                    // 收费类型0.免费，1.收费
                    chargeType = appinfo.getChargeType();
                    // 收费app的免费次数
                    String freeFrequency = appinfo.getFreeFrequency();
                    free = Integer.parseInt(freeFrequency);
                }
                // 免费app或收费app免费次数大于0
                if ("0".equals(chargeType) || "1".equals(chargeType)
                        && free > 0) {
                    code = "200";
                    message = "success";
                    // 设置已经使用次数
                    userApp.setFreeTime("0");
                    // 设置剩余免费次数
                    userApp.setAppSurplus(free.toString());
                    // 设置app所属专区
                    userApp.setAppPackge(packge.getPackgeId());
                    userApp.setAppFreeTime("0");
                    userAppServ.create(userApp);
                }
            }
        } catch (Exception e) {
            code = "500";
            message = "error";
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("result", userApp);
        ajaxJson(result.toString(), response);
    }


    /**
     * 检查app是否可用
     *
     * @param json
     * @param request
     * @param response
     */
    @RequestMapping("/checkApp")
    public void checkApp(@RequestParam("json") String json,
                         HttpServletRequest request, HttpServletResponse response) {
        logger.info("检查app是否可用入参:"+json);

        String code = "400";
        String message = "fail";
        String available = "1"; //0不可用 1可用
        String isFee = "1";//是否付费
        Calendar calendar = Calendar.getInstance();
        String expireTime = ""; //失效时间
        Integer applyNum = -1; //可以使用次数
        String feeType = "0";//通知第三方app是否可以收费,0不能收费,1可以收费
        UserApp app = new UserApp();
        String appId = null;
        try {
            JSONObject obj = JSONObject.fromObject(json);
             appId = obj.getString("appId");
            String appName = obj.getString("appName");
            String userId = obj.getString("userId");
            String token = obj.getString("userToken");
            expireTime = DateFormatUtils.format(calendar, "yyyy-MM-dd HH:mm:ss");
            app.setAppId(appId);
            app.setAppName(appName);
            app.setUserId(userId);
            app.setUserToken(token);
            AppInfo appInfo = appInfoServ.queryApp(app);
            String jsonData = appInfo.getJsonData();
            if (!TextUtils.isEmpty(jsonData)){
                JSONObject jsonObject = JSONObject.fromObject(jsonData);
                if(jsonObject.has("feeType")){
                    feeType =   jsonObject.getString("feeType");
                }
            }


            /**
             * 到user_packge_vip中查找是否
             */
            String packgeName = appInfo.getPackge();//专区名称
            app.setAppPackge(packgeName);
            PackgeInfo packgeInfo = packgeServ.queryByName(packgeName);
            UserPackgeVip userPackgeVip = null;
            if (packgeInfo!=null){
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("userId", userId);
                map.put("packgeId", packgeInfo.getPackgeId());
                 userPackgeVip = userPVipServ.queryApp(map);
            }
            if (appInfo != null) {
                code = "200";
                isFee = "0";
                //免费
                if (TextUtils.isEmpty(appInfo.getChargeType()) || "0".equals(appInfo.getChargeType())) {
                    available = "1";
                    message = "success";

                } else {//收费

                    available = "0";
                    /**
                     * 到user_app_vip查该app有没有购买
                     */
                    UserAppVip userAppVip = userAVipServ.queryApp(app);
                    String appExpire = "";
                    if (userAppVip != null) {
                        appExpire = userAppVip.getAppExpire();
                    }

                    Date date = null;
                    if (!TextUtils.isEmpty(appExpire)) {
                        expireTime = appExpire;//失效时间
                        date = DateUtil.formatDateTime(appExpire);
                    }
                    Date current_date = new Date();
                    //到期
                    if (date == null || current_date.getTime() > date.getTime()) {
                        isFee = "0";//未付费标识
                        available = "0";
                        //调用联创的订购关系接口
                        GetOrderInfoRsp rep = wSOrderInfoServ.queryOrderInfo(Config.SP_ID, userId, Integer.parseInt(Config.USERID_TYPE), token);
                        logger.info("调用联创的订购关系接口返回的数据-->"+rep.toString());
                        if ("0".equals(rep.getResult())) {
                            Product[] products = new Product[0];
                            if (rep.getProductList()!=null){
                               products = rep.getProductList().get(0);
                            }

                            for (int i = 0; i < products.length; i++) {
                                String productID = products[i].getProductID();
                                //是否自动续定0：是 1：否
                                String extend = products[i].getSubscriptionExtend() + "";
                                if (appInfo!=null&& productID.equals(appInfo.getProductId())) {
                                    logger.info("从联创的订购关系接口中查到对应的单个App的productID");
                                    if ("0".equals(extend)) {
                                        logger.info("从联创的订购关系接口中查到对应的单个App续订");
                                        //重新设置过期时间
                                        userAppVip.setAppExpire(DateUtil.getExpire());
                                        expireTime = DateUtil.getExpire();
                                        userAppVip.setVipStatus("1");
                                        userAVipServ.modify(userAppVip);
                                        available = "1";
                                        isFee = "1";
                                    } else {
                                        calendar = Calendar.getInstance();
                                        expireTime = products[i].getExpiredTime();
                                        Date expriredDate = DateUtils.parseDate(expireTime, new String[]{"yyyyMMddHHmmss"});

                                        //过期
                                        if (expriredDate.before(calendar.getTime())) {
                                            logger.info("从联创的订购关系接口中查到对应的单个App没有续订且过期");
                                            available = "0";
                                            isFee = "0";//没有收费
                                        } else {
                                            logger.info("从联创的订购关系接口中查到对应的单个App没有续订且没有过期");
                                            available = "1";
                                            isFee = "1";//已经收费
                                        }
                                    }
                                    break;
                                }
                                else if(packgeInfo!=null&& productID.equals(packgeInfo.getProductId())){
                                    System.out.println("从联创的订购关系接口中查到对应的专区的productID");
                                    if ("0".equals(extend)) {
                                        logger.info("从联创的订购关系接口中查到对应的专区为续订");
                                        //重新设置过期时间
                                        userPackgeVip.setPackgeExpire(DateUtil.getExpire());
                                        expireTime = DateUtil.getExpire();
                                        userPackgeVip.setVipStatus("1");
                                        userPVipServ.modify(userPackgeVip);
                                        available = "1";
                                        isFee = "1";
                                    } else {
                                        logger.info("从联创的订购关系接口中查到对应的专区为取消续订");
                                        calendar = Calendar.getInstance();
                                        expireTime = products[i].getExpiredTime();
                                        Date expriredDate = DateUtils.parseDate(expireTime, new String[]{"yyyyMMddHHmmss"});
                                        //过期
                                        if (expriredDate.before(calendar.getTime())) {
                                            logger.info("从联创的订购关系接口中查到对应的专区时间过期");
                                            available = "0";
                                            isFee = "0";//没有收费
                                        } else {
                                            logger.info("从联创的订购关系接口中查到对应的专区时间没有过期");
                                            available = "1";
                                            isFee = "1";//已经收费
                                        }
                                    }
                                    break;
                                }else{
                                    logger.info("从联创的订购关系接口中没有查到购买商品对应的productId");
                                    available = "0";
                                    isFee = "0";//没有收费
                                }

                            }

                        } else {
                            available = "0";
                            code="400";
                            message = "联创接口查询订购关系失败";
                            isFee = "0";//没有收费
                        }
                        if ("0".equals(available)) {
                            code = "200";
                            if (packgeInfo != null) {
                                String packgeExpire = null;
                                if (userPackgeVip != null) {
                                    packgeExpire = userPackgeVip.getPackgeExpire();
                                }
                                Date pack_date = null;
                                if (!TextUtils.isEmpty(packgeExpire)) {
                                    expireTime = packgeExpire;//失效时间
                                    pack_date = DateUtil.formatDateTime(packgeExpire);
                                }
                                //没有包专区或专区过期
                                if (userPackgeVip == null || pack_date == null || TextUtils.isEmpty(userPackgeVip.getPackgeName()) || current_date.getTime() > pack_date.getTime()) {
                                    available = "0";
                                    isFee = "0";
                                    Map<String, Object> validate = userAppServ.validate(appInfo, app);
                                    if (validate != null) {

                                        available = (String) validate.get("available");
                                        message = (String) validate.get("message");
                                        applyNum = (Integer) validate.get("applyNum");
                                    }
                                } else {
                                    isFee = "1";//已付费标识
                                    available = "1";
                                    message = "success";
                                }
                            } else {
                                isFee = "0";//没有收费
                                Map<String, Object> validate = userAppServ.validate(appInfo, app);
                                if (validate != null) {
                                    available = (String) validate.get("available");
                                    message = (String) validate.get("message");
                                    applyNum = (Integer) validate.get("applyNum");
                                }
                            }
                        }
                    } else {
                        code = "200";
                        available = "1";
                        message = "success";
                        isFee = "1";//已经付费

                    }
                }
            } else {
                available = "0";
                code = "400";
                message = "没有这个app信息";
                isFee = "0";//没有付费
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "error";
            available = "0";
        }

        JSONObject jsonData = new JSONObject();
        jsonData.put("appId", appId);
        jsonData.put("feeType", feeType);
        jsonData.put("isFee", isFee);
        jsonData.put("expireTime", expireTime);

        JSONObject result = new JSONObject();
        result.put("jsonData", jsonData);
        result.put("code", code);
        result.put("message", message);
        result.put("available", available);
        result.put("applyNum", applyNum);
        System.out.println("checkApp:response--->" + result.toString());
        ajaxJson(result.toString(), response);
        logger.info("检查app是否可用返回参:"+result.toString());
    }

    /**
     * 查询单个app剩余使用次数
     */
    @RequestMapping("/getAppSurNum")
    public void getAppSurNum(@RequestParam("json") String jsonStr, HttpServletRequest request, HttpServletResponse response) {
        String code = "400";
        String message = "fail";
        int surNum = 0;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            String appId = json.getString("appId");
            String userId = json.getString("userId");
            String userToken = json.getString("userToken");
            UserApp userApp = new UserApp();
            userApp.setAppId(appId);
            userApp.setUserId(userId);
            userApp.setUserToken(userToken);
            UserApp userApp1 = userAppServ.queryAppByBean(userApp);
            if (null == userApp1) {
                AppInfo appInfo = appInfoServ.queryApp(userApp);
                if (null == appInfo) {
                    message = "没有这个app信息";
                } else {
                    String freeFrequency = appInfo.getFreeFrequency();
                    if (TextUtils.isEmpty(freeFrequency)) {
                        surNum = 0;
                    } else {
                        surNum = Integer.parseInt(freeFrequency);
                    }

                    code = "200";
                    message = "success";
                }
            } else {
                surNum = Integer.parseInt(userApp1.getAppSurplus());
                code = "200";
                message = "success";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "fail";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("surNum", surNum);
        ajaxJson(result.toString(), response);

    }

    /**
     * 查询单个用户所有包月的App
     */

    @RequestMapping("/getAllAppPackge")
    public void getAllApp(@RequestParam("json") String json, HttpServletRequest request, HttpServletResponse response) {
        logger.info("查询单个用户所有包月的App入参-->"+json);
        String code = "400";
        String message = "fail";
        JSONObject result = new JSONObject();
        try {
            JSONObject jsonObject = JSONObject.fromObject(json);
            String userId = jsonObject.getString("userId");
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userId);
            String s = DateUtil.formatDateTimeToStr(Calendar.getInstance().getTime());
            map.put("currentDate", s);
            //单个用户的所有包月的app
            List<UserAppVip> userAppVips = userAVipServ.queryAppList(map);
            result.put("app", userAppVips);

            code = "200";
            message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }
        result.put("code", code);
        result.put("message", message);
        logger.info("查询单个用户所有包月的App返回参-->"+result.toString());
        ajaxJson(result.toString(), response);

    }
}
