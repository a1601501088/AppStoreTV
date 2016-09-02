package com.app.service;

import com.app.model.IndexItemInfo;
import com.basicframe.common.service.IBaseService;

import java.util.List;

/**
 * Created by Administrator on 2016/6/21.
 */
public interface IIndexItemInfo extends IBaseService<IndexItemInfo> {
    List<IndexItemInfo> queryAllItem() throws Exception;
}
