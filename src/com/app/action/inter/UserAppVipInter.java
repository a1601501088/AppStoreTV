package com.app.action.inter;

import com.app.model.AppInfo;
import com.app.model.UserApp;
import com.app.model.UserAppVip;
import com.app.service.*;
import com.app.util.DateUtil;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.ws.service.WSOrderInfoService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Logger;


@Controller
public class UserAppVipInter extends BaseAction {
    private Logger logger = Logger.getLogger("log");
    @Resource
    private IUserAppVip userAVipServ;

    @Resource
    private IAppInfoService appInfoServ;

    @Resource
    private IPackgeInfo packgeServ;


    @Resource
    private IUserApp userAppServ;
    @Resource
    private IUserPackgeVip userPVipServ;
    @Resource
    private IUserPay userPayServ;
    @Resource
    private WSOrderInfoService wSOrderInfoServ;


    /**
     * 增加单个包月App接口
     */
    @RequestMapping(value = "/appvip/createAppVip", method = RequestMethod.POST)
    public void createAppVip(@RequestParam("json") String json,
                             HttpServletRequest request, HttpServletResponse response) {
        logger.info("/createAppVip-入参-->" + json);
        String code = "400";
        String message = "fail";

        //获取入参信息
        JSONObject obj = JSONObject.fromObject(json);
        String appId = obj.getString("appId");
        String userId = obj.getString("userId");
        String userToken = obj.getString("userToken");
        String payType = "1";
        String commodiy = "1";
        String shareType = "1";
        //通过userId与appId从appvip表中查找有没有该用户对此app的包月记录
        UserAppVip userAppVip =   userAVipServ.queryAppVipInfo(userId,appId);

        //如果有
        if (userAppVip!=null){
            //查看app的包月时间是否过期
            String vipStatus = userAppVip.getVipStatus();
            String appExpire = userAppVip.getAppExpire();
            boolean isExpire = false;
            try {
                Date date = DateUtils.parseDate(appExpire, new String[]{"yyyy-MM-dd HH:mm:ss"});
                Calendar calendar = Calendar.getInstance();
                 isExpire = calendar.getTime().before(date);
            } catch (ParseException e) {
                e.printStackTrace();
                logger.info("appvip过期时间解析错误");
            }
            //如果过期或包月状态为0
            if (isExpire||"0".equals(vipStatus)) {
                boolean isVip = false;
                //则包月
                try {
                     isVip = userAVipServ.vip(userId,userToken,appId);
                    code = "200";
                    message = "success";
                } catch (BusException e) {
                    e.printStackTrace();
                    code = "500";
                    message = "支付失败";
                }
                //包月成功或失败后,在userpay表中添加支付日志
                try {
                    boolean isLog = userPayServ.payLog(userId,appId,true,userToken,payType,commodiy,shareType,isVip?"0":"1");
                } catch (Exception e) {
                    e.printStackTrace();
                    message = e.getMessage();
                }
            }
           //如果没有过期或包月状态为1,则无需包月
            else {
                code = "400";
                message = "你已经包月,无需再包月";
            }
        }
        //如果没有
        else{
            boolean isVip = false;
            //则包月
            try {
                isVip = userAVipServ.vip(userId,userToken,appId);
                code = "200";
                message = "success";
            } catch (BusException e) {
                e.printStackTrace();
                code = "500";
                message = "支付失败";
            }
            //包月成功或失败后,在userpay表中添加支付日志
            try {
                boolean isLog = userPayServ.payLog(userId,appId,true,userToken,payType,commodiy,shareType,isVip?"0":"1");

            } catch (Exception e) {
                e.printStackTrace();
                message = e.getMessage();
            }
        }
        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("message",message);
        logger.info("增加单个包月App接口返回参-->" + result.toString());
        ajaxJson(request.toString(),response);
    }


    /**
     * 用户退订app
     */
    @RequestMapping(value = "/appvip/uncreate", method = RequestMethod.POST)
    public void uncreate(@RequestParam("json") String json,
                         HttpServletRequest request, HttpServletResponse response) {
        logger.info("用户退订app入参-->" + json);
        String code = "400";
        String message = "fail";
        JSONObject obj = JSONObject.fromObject(json);
        String appId = obj.getString("appId");
        String userId = obj.getString("userId");
        String userToken = obj.getString("userToken");
        Calendar calendar = Calendar.getInstance();
        try {
            AppInfo appInfo = appInfoServ.queryById(appId);
            String productId = appInfo.getProductId();
            //UnorderSingleServiceRsp rsp = wSOrderInfoServ.unorderSingleService(Config.SP_ID, userId, Integer.parseInt(Config.USERID_TYPE), userToken, productId);
            //logger.info("联创业务退定（单条）接口返回信息-->" + rsp.toString());
           // if ("0".equals(rsp.getResult())) {
            if (true) {
              //  logger.info("联创业务退定（单条）接口退订成功");
             

                UserApp ua = new UserApp();
                ua.setAppId(appId);
                ua.setUserId(userId);
                UserAppVip userAppVip = userAVipServ.queryApp(ua);
                String appExpire = userAppVip.getAppExpire();
                Date date = DateUtil.parseDate(appExpire);
                //获取当前时间
                Date current_time = calendar.getTime();
                //时间没有过期
                if (date.after(current_time)){
                    String vipStatus = "0";
                    if (userAppVip != null) {
                        vipStatus = userAppVip.getVipStatus();
                    }

                    if ("0".equals(vipStatus)) {//已经是退订状态
                        code = "200";
                        message = "此App已经是退订状态";
                    } else {
                        userAppVip.setVipStatus("0");
                        userAVipServ.modify(userAppVip);
                        code = "200";
                        message = "success";
                    }
                }
                //时间过期
                else{
                    message = "你没有对此应用包月，无需退订";
                }
                


            } else {
                logger.info("联创业务退定（单条）接口退订失败");
                code = "400";
                message = "联创业务退定失败";
            }


        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "error";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        ajaxJson(result.toString(), response);
        logger.info("用户退订app返回参-->" + result.toString());
    }

}
