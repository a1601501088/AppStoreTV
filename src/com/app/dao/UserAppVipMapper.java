package com.app.dao;

import com.app.model.UserApp;
import com.app.model.UserAppVip;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;
import java.util.Map;

public interface UserAppVipMapper extends SqlMapper<UserAppVip>{
    int insert(UserAppVip record) ;

    int insertSelective(UserAppVip record);

    UserAppVip queryApp(UserApp app) throws Exception;
    List<UserAppVip> queryAppList(Map<String,Object> map) throws Exception;

    List<UserAppVip> queryAppVipInfo(Map<Object, Object> map);

    int createVip(UserAppVip userAppVip);
}