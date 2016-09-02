package com.app.service.impl;

import com.app.dao.IndexItemInfoMapper;
import com.app.model.IndexItemInfo;
import com.app.service.IIndexItemInfo;
import com.basicframe.common.dao.SqlMapper;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
@Service("indexItemInfoServ")
public class IndexItemInfoService extends BaseServiceImpl<IndexItemInfo> implements IIndexItemInfo{
    @Resource
    private IndexItemInfoMapper indexItemInfoMapper;

    @Resource
    public void setMapper(IndexItemInfoMapper indexItemInfoMapper) {
        super.setSqlMapper(indexItemInfoMapper);
    }

    @Override
    public List<IndexItemInfo> queryAllItem() throws Exception{
        return indexItemInfoMapper.queryAllItem();
    }
}
