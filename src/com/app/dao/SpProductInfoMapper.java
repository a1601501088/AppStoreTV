package com.app.dao;

import com.app.model.SpProductInfo;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;

public interface SpProductInfoMapper extends SqlMapper<SpProductInfo> {

    int insertSelective(SpProductInfo record);

    List<SpProductInfo> querySpProductInfo(SpProductInfo spProductInfo);
}