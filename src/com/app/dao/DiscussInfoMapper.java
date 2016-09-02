package com.app.dao;

import com.app.model.DiscussInfo;
import com.basicframe.common.dao.SqlMapper;

/**
 * <p>Description: 评论Mapper接口</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface DiscussInfoMapper extends SqlMapper<DiscussInfo> {
	public float selectDisscussCount(int appId);
}
