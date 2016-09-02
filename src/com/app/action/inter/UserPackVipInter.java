package com.app.action.inter;

import com.app.model.PackgeInfo;
import com.app.model.UserPackgeVip;
import com.app.service.IPackgeInfo;
import com.app.service.IUserAppVip;
import com.app.service.IUserPackgeVip;
import com.app.service.IUserPay;
import com.app.util.DateUtil;
import com.basicframe.common.action.BaseAction;
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
import java.util.*;
import java.util.logging.Logger;


@Controller
public class UserPackVipInter extends BaseAction {
    private Logger logger = Logger.getLogger("log");
    @Resource
    private WSOrderInfoService wSOrderInfoServ;
    @Resource
    private IUserPackgeVip userPVipServ;

    @Resource
    private IUserPay userPayServ;

    @Resource
    private IPackgeInfo packgeServ;
    @Resource
    private IUserAppVip userAVipServ;

    /**
     * 查询单个用户包的所有专区详细信息
     */
    @RequestMapping(value = "/packgeapp/findPackgeByUser", method = RequestMethod.POST)
    public void findPackgeByUser(@RequestParam("json") String jsonStr,
                                 HttpServletRequest request, HttpServletResponse response) {

        logger.info("查询单个用户包的所有专区详细信息入参-->" + jsonStr);
        String code = "400";
        String message = "fail";
        JSONObject obj = JSONObject.fromObject(jsonStr);
        String userId = obj.getString("userId");
        String userToken = obj.getString("userToken");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("userToken", userToken);
        List<UserPackgeVip> userPackgeVips = null;
        try {
            userPackgeVips = userPVipServ.queryAppList(map);
            code = "200";
            message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "fail";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", userPackgeVips);
        ajaxJson(result.toString(), response);
        logger.info("查询单个用户包的所有专区详细信息返回参-->" + result.toString());

    }


    /**
     * 5.查询单个用户已经包月专区
     */
    @RequestMapping(value = "/packgeapp/findUserPackge", method = RequestMethod.POST)
    public void findUserPackge(@RequestParam("json") String json,
                               HttpServletRequest request, HttpServletResponse response) {
        String code = "400";
        String message = "fail";
        JSONObject obj = JSONObject.fromObject(json);
        String userId = obj.getString("userId");
        String userToken = obj.getString("userToken");
        String packgeId = obj.getString("packgeId");
        try {
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("userId", userId);
            map.put("userToken", userToken);
            map.put("packgeId", packgeId);
            UserPackgeVip userPackgeVip = userPVipServ.queryApp(map);
            code = "200";
            if (userPackgeVip == null) {
                message = "此App未在专区包月内";
            } else {
                String packgeExpire = userPackgeVip.getPackgeExpire();
                Date date = DateUtil.formatDateTime(packgeExpire);

                boolean before = date.before(new Date());
                //表示过期
                if (before) {
                    message = "此App包月时间已经过期";
                } else {
                    message = "success";
                }
            }
        } catch (Exception e) {
            code = "500";
            message = "fail";
            e.printStackTrace();
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        ajaxJson(result.toString(), response);
    }

    /**
     * 增加单个包月专区接口
     */
    @RequestMapping(value = "/packgeapp/createPackVip", method = RequestMethod.POST)
    public void createPackVip(@RequestParam("json") String json,
                              HttpServletRequest request, HttpServletResponse response) {
        String code = "400";
        String message = "fail";
        try {
            //获取入参信息
            JSONObject obj = JSONObject.fromObject(json);
            String packgeId = obj.getString("packgeId");
            String userId = obj.getString("userId");
            String userToken = obj.getString("userToken");
            String payType = "1";
            String commodity = "1";
            String shareType = "1";
            //通过packgeId与userId查找packgevip月中有没有包月记录
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("packgeId", packgeId);
            map.put("userId", userId);
            UserPackgeVip userPackgeVip = null;

            userPackgeVip = userPVipServ.queryApp(map);
            //如果没有
            if (null == userPackgeVip) {
                //则进行包月操作,并在userpay表中记录日志
                boolean isVip = userPVipServ.createVipAndPaylog(userId, packgeId, userToken, payType, commodity, shareType);
            }
            //如果有
            else {
                //对比过期时间没有有过期
                String packgeExpire = userPackgeVip.getPackgeExpire();
                String vipStatus = userPackgeVip.getVipStatus();
                boolean isExpire = false;
                try {
                    Date date = DateUtils.parseDate(packgeExpire, new String[]{"yyyy-MM-dd HH:mm:ss"});
                    isExpire = Calendar.getInstance().getTime().before(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                //没有过期,且status为1,则无需包月
                if (!isExpire && "1".equals(vipStatus)) {
                    code = "400";
                    message = "你已经包月,无需重复包月";
                }
                //已经过期
                else {
                    //则进行包月操作,并在userpay表中记录日志
                    boolean isVip = userPVipServ.createVipAndPaylog(userId, packgeId, userToken, payType, commodity, shareType);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "fail";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        ajaxJson(result.toString(), response);
    }

    /**
     * 单个专区包月退订
     */
    @RequestMapping(value = "/packgeapp/uncreatePackVip", method = RequestMethod.POST)
    public void uncreatePackVip(@RequestParam("json") String jsonStr,
                                HttpServletRequest request, HttpServletResponse response) {
        logger.info("单个app专区包月退订入参-->" + jsonStr);
        JSONObject json = JSONObject.fromObject(jsonStr);
        String code = "400";
        String message = "fail";
        String packgeId = json.getString("packgeId");
        String userId = json.getString("userId");
        String userToken = json.getString("userToken");
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userId", userId);
        map.put("userToken", userToken);
        map.put("packgeId", packgeId);
        try {
            PackgeInfo packgeInfo = packgeServ.queryById(packgeId);
            String productId = packgeInfo.getProductId();
           // UnorderSingleServiceRsp rsp = wSOrderInfoServ.unorderSingleService(Config.SP_ID, userId, Integer.parseInt(Config.USERID_TYPE), userToken, productId);
           // if ("0".equals(rsp.getResult())) {
            if (true) {
                code = "200";
                logger.info("联创业务退订成功");
                UserPackgeVip userPackgeVip = userPVipServ.queryAppById(map);
                if (userPackgeVip == null) {
                    message = "你没有包此专区，无需退订";
                } else {
                    String vipStauts = userPackgeVip.getVipStatus();
                    String packgeExpire = userPackgeVip.getPackgeExpire();
                   Date expire_date =DateUtil.parseDate(packgeExpire);
                    Date current_date = Calendar.getInstance().getTime();
                    //包月日期没有过期
                    if (expire_date.after(current_date)){
                        //已经包月
                        if ("1".equals(vipStauts)) {
                            userPackgeVip.setVipStatus("0");//包月状态置为0 取消包月
                            userPackgeVip.setPackgeId(packgeId);

                            userPVipServ.modify(userPackgeVip);
                            code = "200";
                            message = "success";
                        } else {
                            message = "你没有对此专区包月，无需退订";
                        }
                    }
                    //包月过期
                    else {
                        message = "此专区已过期，无需退订";
                    }
                }
            } else {
                message = "退订失败";
                code = "400";
                logger.info("联创业务退订失败");
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "fail";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        ajaxJson(result.toString(), response);
        logger.info("单个app专区包月退订返回参-->" + result.toString());
    }
}
