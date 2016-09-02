package com.app.dao;

import com.app.model.UserApp;
import com.basicframe.common.dao.SqlMapper;

public interface UserAppMapper extends SqlMapper<UserApp>{


    UserApp queryAppByBean(UserApp app);
}