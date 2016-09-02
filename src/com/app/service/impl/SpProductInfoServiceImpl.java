package com.app.service.impl;

import com.app.dao.SpProductInfoMapper;
import com.app.model.SpProductInfo;
import com.app.service.ISpProductInfoService;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

import java.util.List;



/**
 * Created by Administrator on 2016/5/19.
 */
@Service("SpProductInfoServ")
public class SpProductInfoServiceImpl extends BaseServiceImpl<SpProductInfo> implements ISpProductInfoService {

    @Resource
    private SpProductInfoMapper spProductInfoMapper;

    @Resource
    public void setMapper(SpProductInfoMapper spProductInfoMapper){
        super.setSqlMapper(spProductInfoMapper);
    }


    @Override
    public SpProductInfo querySpProductInfo(String spId, String appId, String packgeId, String payType) throws Exception {
        SpProductInfo spProductInfo = new SpProductInfo();
        spProductInfo.setSpId(spId);
        spProductInfo.setAppId(appId);
        spProductInfo.setPackgeId(packgeId);
        spProductInfo.setPayType(payType);
        List<SpProductInfo> spProductInfos = spProductInfoMapper.querySpProductInfo(spProductInfo);
        if (spProductInfos.isEmpty()){
            throw new IllegalStateException("数据库中没有找到此信息");
        }
        if (spProductInfos.size()>1){
            throw new IllegalStateException("数据库中找到多条此信息");
        }
        return spProductInfos.get(0);
    }
}
