package com.app.action.inter;

import com.app.model.SpProductInfo;
import com.app.service.ISpProductInfoService;
import com.basicframe.common.action.BaseAction;
import net.sf.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by Administrator on 2016/5/19.
 */
@Controller
public class SpProductInfoInter extends BaseAction{
    @Resource
    private ISpProductInfoService SpProductInfoServ;

    @RequestMapping(value = "/querySpProductInfo" ,method = RequestMethod.POST)
    public void querySpProductInfo(@RequestParam("json") String jsonStr, HttpServletRequest request, HttpServletResponse response){
        String code = "400";
        String message = "fail";
        SpProductInfo spProductInfo = null;
        try {
            JSONObject json = JSONObject.fromObject(jsonStr);
            String spId = json.getString("spId");
            String appId = "";
            String packgeId = "";
            String  payType = json.getString("payType");
            if (json.has("packgeId")){
                packgeId = json.getString("packgeId");
            }
            if (json.has("appId")){
                appId =  json.getString("appId");
            }
             spProductInfo = SpProductInfoServ.querySpProductInfo(spId, appId, packgeId, payType);
            code = "200";
            message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            if (e instanceof IllegalStateException){
                message = e.getMessage();
            }
        }
        JSONObject result = new JSONObject();
        result.put("code",code);
        result.put("message",message);
        result.put("spProductInfo",spProductInfo);
        ajaxJson(result.toString(),response);

    }
}
