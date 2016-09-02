package com.ws.action;

import com.basicframe.common.action.BaseAction;
import com.ws.model.BatchUnorderServiceRsp;
import com.ws.model.GetOrderInfoRsp;
import com.ws.model.UnorderSingleServiceRsp;
import com.ws.service.WSOrderInfoService;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/5/24.
 */
@Controller
public class WSOrderInfoAction extends BaseAction{
    @Resource
    private WSOrderInfoService wSOrderInfoServ;

    //2.1	订购关系查询接口
    @RequestMapping(value = "/service/getOrderInfo",method = RequestMethod.POST)
    public void getOrderInfo( HttpServletRequest request, HttpServletResponse response){
        String code = "400";
        String message = "fail";
        GetOrderInfoRsp rep = null;
       try {
           String jsonStr = request.getParameter("json");
           JSONObject json = JSONObject.fromObject(jsonStr);
           String spId =  json.getString("spId");
           String userId =  json.getString("userId");
           int userIdType = json.getInt("userIdType");
           String userToken = json.getString("userToken");
             rep = wSOrderInfoServ.queryOrderInfo(spId, userId, userIdType, userToken);
           if ("0".equals(rep.getResult())){
               code = "200";
               message = "success";
           }else {
               code = "400";
               message = "webservice业务退定失败";
           }
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("message",message);
        result.put("data",rep);
        ajaxJson(result.toString(),response);
    }

    // 业务退订（单个）
    @RequestMapping(value = "/service/unorderSingleService",method = RequestMethod.POST)
    public void unorderSingleService(@RequestParam("json") String jsonStr, HttpServletRequest request, HttpServletResponse response){
        String code = "400";
        String message = "fail";
        UnorderSingleServiceRsp rep = null;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            String spId =  json.getString("spId");
            String userId =  json.getString("userId");
            int userIdType = json.getInt("userIdType");
            String userToken = json.getString("userToken");
            String productId = json.getString("productId");
            rep= wSOrderInfoServ.unorderSingleService(spId, userId, userIdType, userToken, productId);
            if ("0".equals(rep.getResult())){
                code = "200";
                message = "success";
            }else {
                code = "400";
                message = "webservice业务退定失败";
            }
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("message",message);
        result.put("data",rep);
        ajaxJson(result.toString(),response);
    }

    // 2.3 业务退定（批量）接口
    @RequestMapping(value = "/service/batchUnorderService",method = RequestMethod.POST)
    public void batchUnorderService(@RequestParam("json") String jsonStr, HttpServletRequest request, HttpServletResponse response){
        String code = "400";
        String message = "fail";
        BatchUnorderServiceRsp rsp = null;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            String spId =  json.getString("spId");
            String userId =  json.getString("userId");
            int userIdType = json.getInt("userIdType");
            String userToken = json.getString("userToken");
            String productIdList = json.getString("productIdList");
             rsp = wSOrderInfoServ.batchUnorderService(spId, userId, userIdType, userToken, productIdList);
            if ("0".equals(rsp.getResult())){
                code = "200";
                message = "success";
            }else {
                code = "400";
                message = "webservice业务退定失败";
            }

        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
        }

        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("message",message);
        result.put("data",rsp);
        ajaxJson(result.toString(),response);
    }
}
