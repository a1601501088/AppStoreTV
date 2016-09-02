package com.ws.service.impl;

import com.Config;
import com.app.util.DateUtil;
import com.app.util.UserTools;
import com.ws.model.*;
import com.ws.service.WSOrderInfoService;
import com.ws.util.SHA1;
import net.sf.json.JSONObject;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.params.HttpMethodParams;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by Administrator on 2016/5/24.
 */
@Service("wSOrderInfoServ")
public class WSOrderInfoServiceImpl extends WSBaseServiceImpl implements WSOrderInfoService {


    @Override
    public GetOrderInfoRsp queryOrderInfo(String spId, String userId, int userIdType, String userToken) throws Exception {

        GetOrderInfoReq req = new GetOrderInfoReq();

        req.setSPID(spId);
        req.setTimeStamp(DateUtil.getCurrentTime());
//spid(8)+ YYYYMMDDHHMISS(14)+自增字符串序号(18)
        String transactionID = UserTools.getTransactionID(spId, 40);

        //System.out.println(transactionID+":"+transactionID.length());
        req.setTransactionID(transactionID);
        req.setUserID(userId);
        req.setUserIDType(userIdType);
        req.setUserToken(userToken);
        System.out.println("访问联创地址:"+Config.GETORDERINFO_URL);
        GetOrderInfoRsp rep = (GetOrderInfoRsp) getResult(req, GetOrderInfoRsp.class, Config.GETORDERINFO_URL, Config.GETORDERINFO_SPACE, Config.GETORDERINFO_METHOD);

        return rep;
    }

    @Override
    public UnorderSingleServiceRsp unorderSingleService(String spId, String userId, int userIdType, String userToken, String productId) throws Exception {
        UnorderSingleServiceReq req = new UnorderSingleServiceReq();
        req.setSPID(spId);
        req.setTimeStamp(DateUtil.getCurrentTime());
        String transactionID = UserTools.getTransactionID(spId, 40);
        req.setTransactionID(transactionID);
        req.setUserToken(userToken);
        req.setProductID(productId);
        req.setUserID(userId);
        req.setUserIDType(userIdType);
        System.out.println("访问联创地址:"+Config.UNORDERSINGLE_URL);
        UnorderSingleServiceRsp rsp = (UnorderSingleServiceRsp) getResult(req, UnorderSingleServiceRsp.class, Config.UNORDERSINGLE_URL, Config.UNORDERSINGLE_SPACE, Config.UNORDERSINGLE_METHOD);
        return rsp;
    }

    @Override
    public BatchUnorderServiceRsp batchUnorderService(String spId, String userId, int userIdType, String userToken, String productIdList) throws Exception {
        BatchUnorderServiceReq req = new BatchUnorderServiceReq();
        req.setSPID(spId);
        req.setTimeStamp(DateUtil.getCurrentTime());
        String transactionID = UserTools.getTransactionID(spId, 40);
        req.setTransactionID(transactionID);
        req.setUserToken(userToken);
        req.setUserID(userId);
        req.setUserIDType(userIdType);
        req.setProductIDList(productIdList);
        BatchUnorderServiceRsp rsp = (BatchUnorderServiceRsp) getResult(req, BatchUnorderServiceRsp.class, Config.BATCHUNORDER_URL, Config.BATCHUNORDER_SPACE, Config.BATCHUNORDER_METHOD);

        return rsp;
    }

    @Override
    public Map<String, Object> postMG(String spId, String userId, String userIdType, String productId, String userToken, String expiredTime) throws Exception {
        String code = "400";
        String message = "fail";

        Map<String,Object> map =  new HashMap<String,Object>();

        /*String end_time = "";
        if (!TextUtils.isEmpty(expiredTime)) {
            try {
                Date date = DateUtils.parseDate(expiredTime, new String[]{"yyyyMMddHHmmss"});
                end_time = DateFormatUtils.format(date, "yyyy-MM-dd HH:mm:ss");
            } catch (ParseException e) {
                e.printStackTrace();
                 message = "时间类型转换错误";
                return null;
            }
        }*/

        JSONObject data =   new JSONObject();
        data.put("user_id", userId);
        data.put("product_id_list", productId);
        data.put("user_token", userToken);
      //  data.put("lc_end_time", end_time);
      String sign =  makeSign(data, Config.SGIN_KEY);
        //HttpClient 访问
        HttpClient httpclient = new HttpClient();
        PostMethod post = new PostMethod(Config.MG_URL);
        post.getParams().setParameter(HttpMethodParams.HTTP_CONTENT_CHARSET,"gbk");
        post.addParameter("sign", sign);
        post.addParameter("method_type", "1");
        post.addParameter("data", data.toString());
        int status = httpclient.executeMethod(post);
        System.out.println("status:"+status);
        //(info.getBytes("iso-8859-1"), "gbk");
        String info = new String(post.getResponseBody(),"gbk");
        JSONObject jsonObject = JSONObject.fromObject(info);
       String st = jsonObject.getString("status");
       JSONObject msgObj = jsonObject.getJSONObject("msg");
        message = URLDecoder.decode(msgObj.getString("msg"));
        if ("0000".equals(st)){
            code = "200";
        }else {
            code = "400";
            message += ",(芒果接口)";
        }
        map.put("code",code);
        map.put("message",message);
        System.out.println(message);
        return map;
    }

    private String makeSign(JSONObject data, String sginKey) throws NoSuchAlgorithmException, UnsupportedEncodingException {

        String signSrc = ("data="+data.toString()+"&method_type="+"1"+"&secret_key="+sginKey).toLowerCase();
        System.out.println(signSrc);
        String mySign = (new SHA1().getDigestOfString(signSrc.getBytes())).toLowerCase();
        System.out.println("mySign:"+mySign);
        return mySign;
    }


    public UserDetInfoRsp getUserDets(UserDetInfoReq req ) throws Exception{
        UserDetInfoRsp rep = (UserDetInfoRsp) getResult(req,UserDetInfoRsp.class,Config.USERDET_URL,Config.USERDET_SPACE,Config.USERDET_METHOD);
        return rep;
    }

    public AccountPwdChangRsp changeAccountPwd(AccountPwdChangReq req ) throws Exception{
        AccountPwdChangRsp rep = (AccountPwdChangRsp) getResult(req,AccountPwdChangRsp.class,Config.CHANGEACCOUNTPWD_URL,Config.CHANGEACCOUNTPWD_SPACE,Config.CHANGEACCOUNTPWD_METHOD);
        return rep;
    }

}
