package com.app.dao;

import com.app.model.UserPay;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;

public interface UserPayMapper extends SqlMapper<UserPay>{
  
	int insert(UserPay record);

	List<UserPay> selectByUser(UserPay record);

	int create2(UserPay userPay) ;

	UserPay queryLatelyByUserId(String userId);
}