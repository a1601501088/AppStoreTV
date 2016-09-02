package com.basicframe.sys.service.impl;

import java.io.File;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.impl.BaseServiceImpl;
import com.basicframe.sys.dao.AttachmentMapper;
import com.basicframe.sys.model.Attachment;
import com.basicframe.sys.service.IAttachmentService;

@Service("attaServ")
public class AttachmentServiceImpl extends BaseServiceImpl<Attachment> implements IAttachmentService {
	
	@Resource
	private AttachmentMapper attaMapper;
	
	@Resource
	public void setMapper(AttachmentMapper attaMapper){
		super.setSqlMapper(attaMapper);
	}

	public void remove(Attachment vo) throws BusException {
		int i = attaMapper.delete(vo.getId());
		if(i < 1){
			throw new BusException("删除文件失败！");
		}
		//删除图片
		File file = new File(vo.getPath());
		if(file.exists()){
			file.delete();
		}
		
	}
	


}
