package com.app.service.impl;

import com.app.dao.IndexListInfoMapper;
import com.app.model.IndexListInfo;
import com.app.service.IIndexListInfoService;
import com.basicframe.common.dao.SqlMapper;
import com.basicframe.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Service("IndexListInfoServ")
public class IndexListInfoServiceImpl extends BaseServiceImpl<IndexListInfo> implements IIndexListInfoService {
    @Resource
    private IndexListInfoMapper indexListInfoMapper;

    @Resource
    public void setMapper(IndexListInfoMapper indexListInfoMapper) {
        super.setSqlMapper(indexListInfoMapper);
    }

    @Override
    public List<IndexListInfo> quaryAllList() throws Exception{
        return indexListInfoMapper.queryAllList();
    }
}
