package com.app.service.impl;

import java.math.BigDecimal;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.app.dao.AppInfoMapper;
import com.app.dao.DiscussInfoMapper;
import com.app.model.AppInfo;
import com.app.model.DiscussInfo;
import com.app.service.IDiscussInfoService;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.utils.DateTool;


@Service("discussInfoServ")
public class DiscussInfoServiceImpl extends BaseServiceImpl<DiscussInfo> implements IDiscussInfoService {

	@Resource
	private DiscussInfoMapper discussInfoMapper;
	
	@Resource
	private AppInfoMapper appInfoMapper;
	
	@Resource
	public void setMapper(DiscussInfoMapper discussInfoMapper){
		super.setSqlMapper(discussInfoMapper);
	}

	public void create(String appId, String appLevel) throws BusException {
		int l = Integer.parseInt(appLevel);
		if(l > 5){
			throw new BusException("评论的等级不能大于5");
		}
		DiscussInfo info = new DiscussInfo();
		info.setAppLevel(Integer.parseInt(appLevel));
		info.setAppId(Integer.parseInt(appId));
		info.setCreateTime(DateTool.instance.getCurrentDateString());
		discussInfoMapper.insert(info);
		float count = discussInfoMapper.selectDisscussCount(Integer.parseInt(appId));
		BigDecimal level = new BigDecimal(count);
		level.setScale(0, BigDecimal.ROUND_HALF_UP);
		AppInfo app = appInfoMapper.selectById(appId);
		if(app == null){
			throw new BusException("评论的应用不存");
		}
		AppInfo vo = new AppInfo();
		vo.setAppId(Integer.parseInt(appId));
		vo.setAppLevel(level.intValue());
		appInfoMapper.update(vo);
	}
	
	
	
}
