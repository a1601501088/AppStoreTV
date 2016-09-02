package com.app.dao;

import com.app.model.UserPackgeVip;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;
import java.util.Map;

public interface UserPackgeVipMapper extends SqlMapper<UserPackgeVip>{

	UserPackgeVip selectById(UserPackgeVip upv);

	UserPackgeVip queryApp(Map<String, Object> map);
	List<UserPackgeVip> queryAppList(Map<String, Object> map);

	UserPackgeVip queryAppById(Map<String, Object> map);

}