package com.ws.action;

import com.app.util.TextUtils;
import com.app.util.UserTools;
import com.basicframe.common.action.BaseAction;
import com.ws.model.*;
import com.ws.service.WSOrderInfoService;
import net.sf.json.JSONObject;
import org.apache.commons.lang.time.DateFormatUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Calendar;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
public class OrderInfoAction extends BaseAction {
    @Resource
    private WSOrderInfoService wSOrderInfoServ;

    //2.1	订购关系查询接口
    @RequestMapping(value = "/service/queryOrderInfo")
    public String queryOrderInfo(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/service/queryOrderInfo");
        String code = "400";
        String message = "fail";
        GetOrderInfoRsp rep = null;
        String userId = request.getParameter("userId");
        try {
            String spId = request.getParameter("spId");
            String userIdType = request.getParameter("userIdType");
            String userToken = request.getParameter("userToken");
            if (TextUtils.isEmpty(userIdType)) {
                userIdType = "0";
            }
            request.setAttribute("dataList", "[]");
            rep = wSOrderInfoServ.queryOrderInfo(spId, userId, Integer.parseInt(userIdType), userToken);
            System.out.println("订购关系查询接口---->" + rep.toString());
            if ("0".equals(rep.getResult())) {
                code = "200";
                message = "success";
                JSONObject result = new JSONObject();

                result.put("data", rep);
                if (null != rep.getProductList() && rep.getProductList().size() > 0) {

                    String dataList = result.getJSONObject("data").getJSONArray("productList").get(0).toString();

                    request.setAttribute("dataList", dataList);
                    System.out.println(dataList);
                }

                request.setAttribute("orderInfo", result);
                request.getSession().setAttribute("spId", spId);
                request.getSession().setAttribute("userId", userId);
                request.getSession().setAttribute("userIdType", userIdType);
                request.getSession().setAttribute("userToken", userToken);
                return "page/index.jsp";
            } else {
                code = "400";
                message = "webservice业务查询失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", rep);
        request.getSession().setAttribute("userId", userId);
        System.out.println("订购关系查询接口:" + result.toString());
        return "page/index.jsp";
    }

    // 业务退订（单个）
    @RequestMapping(value = "/service/delOrderSingleService", method = RequestMethod.POST)
    public void delOrderSingleService(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/service/delOrderSingleService");
        String code = "400";
        String message = "fail";
        String sign = "";

        UnorderSingleServiceRsp rep = null;
        try {
            /*String spId =   "99999999";
            String userId =   "6830016";
            String userIdType =   "1";
            String userToken =   "";*/
            String spId = (String) request.getSession().getAttribute("spId");
            String userId = (String) request.getSession().getAttribute("userId");
            String userIdType = (String) request.getSession().getAttribute("userIdType");
            String userToken = (String) request.getSession().getAttribute("userToken");
            String productId = request.getParameter("productID");
            if (TextUtils.isEmpty(userIdType)) {
                userIdType = "0";
            }
            rep = wSOrderInfoServ.unorderSingleService(spId, userId, Integer.parseInt(userIdType), userToken, productId);
            String expiredTime = DateFormatUtils.format(Calendar.getInstance(),"yyyyMMddHHmmss");
            rep.setExpiredTime(expiredTime);
            if ("0".equals(rep.getResult())) {
                code = "200";
                message = "success";
                //调用芒果接口
                /*Map<String, Object> map = wSOrderInfoServ.postMG(spId, userId, userIdType, productId, userToken, rep.getExpiredTime());
                code = (String) map.get("code");
                message = (String) map.get("message");*/
            } else {
                code = "400";
                message = "webservice业务退定失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }
        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", rep);
        ajaxJson(result.toString(), response);
    }


    // 2.3 业务退定（批量）接口
    @RequestMapping(value = "/service/batchdelOrderService", method = RequestMethod.POST)
    public void batchdelOrderService(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/service/batchdelOrderService");
        String code = "400";
        String message = "fail";
        BatchUnorderServiceRsp rsp = null;
        try {
            HttpSession session = request.getSession();
            String spId = (String) session.getAttribute("spId");
            String userId = (String) session.getAttribute("userId");
            String userIdType = (String) session.getAttribute("userIdType");
            String userToken = (String) session.getAttribute("userToken");
            String productIdList = request.getParameter("productIdList");

            if (TextUtils.isEmpty(userIdType)) {
                userIdType = "0";
            }
            rsp = wSOrderInfoServ.batchUnorderService(spId, userId, Integer.parseInt(userIdType), userToken, productIdList);
            if ("0".equals(rsp.getResult())) {
                code = "200";
                message = "success";
              /*  Map<String, Object> map = wSOrderInfoServ.postMG(spId, userId, userIdType, productIdList, userToken, "");
                code = (String) map.get("code");
                message = (String) map.get("message");*/
            } else {
                code = "400";
                message = "webservice业务退定失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", rsp);
        System.out.println("业务退定（批量）接口:" + result.toString());
        ajaxJson(result.toString(), response);
    }


    // 2.4 用户清单查询接口
    @RequestMapping(value = "/service/getUserDets", method = RequestMethod.POST)
    public void getUserDets(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/service/getUserDets");
        String code = "400";
        String message = "fail";
        UserDetInfoRsp rsp = null;
        try {
            String spId = request.getParameter("spId");
            String userId = request.getParameter("userId");
            String userIdType = request.getParameter("userIdType");
            String userToken = request.getParameter("userToken");
            String beginTime = request.getParameter("beginTime");
            String endTime = request.getParameter("endTime");
            if (TextUtils.isEmpty(userIdType)) {
                userIdType = "0";
            }
            UserDetInfoReq req = new UserDetInfoReq();
            req.setSPID(spId);
            Calendar calendar = Calendar.getInstance();
            req.setTimeStamp(DateFormatUtils.format(calendar, "yyyyMMddHHmmss"));
            req.setTransactionID(UserTools.getTransactionID(spId, 40));
            req.setUserToken(userToken == null ? "" : userToken);
            req.setUserID(userId);
            req.setUserIDType(Integer.parseInt(userIdType));
            req.setBeginTime(beginTime);
            req.setEndTime(endTime);
            rsp = wSOrderInfoServ.getUserDets(req);

            if (0 == rsp.getResult()) {
                code = "200";
                message = "success";
            } else {
                code = "400";
                message = "webservice业务清单查询失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", rsp);
        System.out.println("用户清单查询接口-->" + result.toString());
        ajaxJson(result.toString(), response);
    }

    // 2.4 用户清单查询接口
    @RequestMapping(value = "/service/changeAccountPwd", method = RequestMethod.POST)
    public void changeAccountPwd(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/service/changeAccountPwd");
        String code = "400";
        String message = "fail";
        AccountPwdChangRsp rsp = null;
        try {
            String spId = request.getParameter("spId");
            String userId = request.getParameter("userId");
            String userIdType = request.getParameter("userIdType");
            String userToken = request.getParameter("userToken");
            String newPassword = request.getParameter("newPassword");
            String password = request.getParameter("password");
            if (TextUtils.isEmpty(userIdType)) {
                userIdType = "0";
            }
            AccountPwdChangReq req = new AccountPwdChangReq();
            Calendar calendar = Calendar.getInstance();
            req.setTimeStamp(DateFormatUtils.format(calendar, "yyyyMMddHHmmss"));
            req.setTransactionID(UserTools.getTransactionID(spId, 40));
            req.setUserToken(userToken);
            req.setUserID(userId);
            req.setNewPassword(newPassword);
            req.setPassword(password);
            rsp = wSOrderInfoServ.changeAccountPwd(req);

            System.out.println(rsp);
            if ("0".equals(rsp.getResult())) {
                code = "200";
                message = "success";
            } else {
                code = "400";
                message = "webservice业务清单查询失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", rsp);
        ajaxJson(result.toString(), response);
    }


    // 2.6 童锁密码修改接口
    @RequestMapping(value = "/service/modLimitation", method = RequestMethod.POST)
    public void modLimitation(HttpServletRequest request, HttpServletResponse response) {
        System.out.println("/service/modLimitation");
        String code = "400";
        String message = "fail";
        AccountPwdChangRsp rsp = null;
        try {
            String spId = request.getParameter("spId");
            String userId = request.getParameter("userId");
            String userIdType = request.getParameter("userIdType");
            String userToken = request.getParameter("userToken");
            String newPassword = request.getParameter("newPassword");
            String password = request.getParameter("password");
            if (TextUtils.isEmpty(userIdType)) {
                userIdType = "0";
            }
            AccountPwdChangReq req = new AccountPwdChangReq();

            Calendar calendar = Calendar.getInstance();
            req.setTimeStamp(DateFormatUtils.format(calendar, "yyyyMMddHHmmss"));
            req.setTransactionID(UserTools.getTransactionID(spId, 40));
            req.setUserToken(userToken);
            req.setUserID(userId);
            req.setNewPassword(newPassword);
            req.setPassword(TextUtils.isEmpty(password)?null:password);
            rsp = wSOrderInfoServ.changeAccountPwd(req);

            System.out.println(rsp);
            if ("0".equals(rsp.getResult())) {
                code = "200";
                message = "success";
            } else {
                code = "400";
                message = "webservice童锁密码修改失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code", code);
        result.put("message", message);
        result.put("data", rsp);
        System.out.println("童锁密码修改:" + result.toString());
        ajaxJson(result.toString(), response);
    }


}
