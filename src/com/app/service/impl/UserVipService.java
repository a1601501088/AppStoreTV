package com.app.service.impl;

import com.app.dao.UserPackgeVipMapper;
import com.app.model.PackgeInfo;
import com.app.model.UserPackgeVip;
import com.app.service.IPackgeInfo;
import com.app.service.IUserPackgeVip;
import com.app.service.IUserPay;
import com.app.util.DateUtil;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.apache.juli.logging.Log;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

@Service("userPVipServ")
public class UserVipService extends BaseServiceImpl<UserPackgeVip> implements IUserPackgeVip {
    private Logger logger = Logger.getLogger("log");
    @Resource
    private UserPackgeVipMapper userPackgeVipMapper;
    @Resource
    private IPackgeInfo packgeServ;
    @Resource
    private IUserPay userPayServ;
    @Resource
    public void setMapper(UserPackgeVipMapper userPackgeVipMapper) {
        super.setSqlMapper(userPackgeVipMapper);
    }


    @Override
    public UserPackgeVip queryApp(Map<String, Object> map) throws Exception {
        return userPackgeVipMapper.queryApp(map);
    }

    @Override
    public List<UserPackgeVip> queryAppList(Map<String, Object> map) throws Exception {
        return userPackgeVipMapper.queryAppList(map);
    }

    @Override
    public UserPackgeVip queryAppById(Map<String, Object> map) throws Exception {
        return userPackgeVipMapper.queryAppById(map);
    }

    /**
     * 包月操作与支付日志记录
     * @param userId
     * @param packgeId
     * @param userToken
     * @return true包月成功 false包月失败
     */
    @Override
    public boolean createVipAndPaylog(String userId, String packgeId, String userToken,String payType,
                                String commodity,String shareType) throws Exception {
        PackgeInfo packgeInfo = null;
        try {
             packgeInfo = packgeServ.queryById(packgeId);
            if (null==packgeInfo){
                logger.info("没有这个专区packgeId:"+packgeId);
            }else {
                String packgeName = packgeInfo.getPackgeName();
                String packgePrice = packgeInfo.getPackgePrice();
                String packgeUrl = packgeInfo.getPackgeUrl();
                String packgeOrde = packgeInfo.getPackgeOrde();
                String packgePace = packgeInfo.getPackgePace();
                UserPackgeVip userPackgeVip = new UserPackgeVip();
                userPackgeVip.setUserToken(userToken);
                userPackgeVip.setPackgeExpire(DateUtil.getExpire());
                userPackgeVip.setUserId(userId);
                userPackgeVip.setPackgeId(packgeId);
                userPackgeVip.setPackgeName(packgeName);
                userPackgeVip.setPackgePrice(packgePrice);
                userPackgeVip.setPackgeUrl(packgeUrl);
                userPackgeVip.setPackgeOrde(packgeOrde);
                userPackgeVip.setPackgePace(packgePace);
                userPackgeVip.setVipStatus("1");
                int insert = userPackgeVipMapper.insert(userPackgeVip);
                String payStatus = "1";//支付成功
                if (insert==1){
                    logger.info("插入packgevip表成功");
                    payStatus = "1";//支付成功
                }else {
                    logger.info("插入packgevip表失败");
                    payStatus = "0";//支付失败
                }
                //创建支付日志
                try {
                    userPayServ.payLog(userId,packgeId,false,userToken,payType,commodity,shareType,payStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                    logger.info("创建支付日志失败:"+e.getMessage());
                }
                return  true;
            }
        } catch (BusException e) {
            e.printStackTrace();
        }
        return false;
    }
}
