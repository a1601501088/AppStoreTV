package com.app.dao;

import com.app.model.PackgeInfo;
import com.basicframe.common.dao.SqlMapper;

public interface PackgeInfoMapper extends SqlMapper<PackgeInfo>{
    int insert(PackgeInfo record);

    int insertSelective(PackgeInfo record);
}