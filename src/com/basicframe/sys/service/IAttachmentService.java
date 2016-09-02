package com.basicframe.sys.service;

import com.basicframe.common.exception.BusException;
import com.basicframe.common.service.IBaseService;
import com.basicframe.sys.model.Attachment;

/**
 * <p>Description: 附件接口</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public interface IAttachmentService extends IBaseService<Attachment> {
	
	/**
	 * 删除防伪码文件
	 * @param vo
	 * @throws BusException
	 */
	public void remove(Attachment vo) throws BusException ;
	
}
