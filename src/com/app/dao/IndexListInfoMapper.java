package com.app.dao;

import com.app.model.IndexListInfo;
import com.basicframe.common.dao.SqlMapper;

import java.util.List;

public interface IndexListInfoMapper extends SqlMapper<IndexListInfo>{
    int insert(IndexListInfo record);

    int insertSelective(IndexListInfo record);

    List<IndexListInfo> queryAllList();
}