package com.app.service;

import com.app.model.IndexListInfo;
import com.basicframe.common.service.IBaseService;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface IIndexListInfoService extends IBaseService<IndexListInfo>{
    List<IndexListInfo> quaryAllList()throws Exception;
}
