package com.app.dao;

import com.app.model.IndexItemInfo;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;

public interface IndexItemInfoMapper extends SqlMapper<IndexItemInfo>{
    int insert(IndexItemInfo record);

    int insertSelective(IndexItemInfo record);

    List<IndexItemInfo> queryAllItem();
}