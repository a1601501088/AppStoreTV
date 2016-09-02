package com.app.action;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import com.app.model.CommendInfo;
import com.app.service.ICommendInfoService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.ToolBox;
import com.basicframe.utils.page.Pagination;

/**
 * <p>Description: 推荐action</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class CommendInfoAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(CommendInfoAction.class);
	
	@Resource
	private ICommendInfoService commendInfoServ;
	
	
	/**
	 * 列表
	 * @author tyj
	 * @param page 当前页
	 * @param request 
	 * @return 返回到role_list.jsp
	 * @date Feb 24, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/commend_list")
	public String findList(String page, HttpServletRequest request){
		try {
			String commendName = request.getParameter("commendName");
			String commendType = request.getParameter("commendType");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(commendName != null && commendName != ""){
				map.put("commendName", "%"+commendName+"%");
			}
			if(commendType != null && commendType != ""){
				map.put("commendType", commendType);
			}
			//构建分页对象
			Pagination pagination = findPageList(map, commendInfoServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("commendType", commendType);
			request.setAttribute("commendName", commendName);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/app/commend_list.jsp");
	}
	
	/**
	 * 添加
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/commend_add")
	public String add(HttpServletRequest request){
		try {
			String filename = "";
			//转型为MultipartHttpRequest
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;   
	        //根据页面的name名称得到上传的文件  
	        MultipartFile mFile  =  multipartRequest.getFile("afile");
	        if(mFile != null){
	        	//获取文件名
			    filename = mFile.getOriginalFilename();
			    if(!"".equals(filename)){
			    	//获取文件扩展名
				    String extName = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
				    //文件类型验证
				    if("jpg,png,jpeg,gif,bmp".indexOf(extName) == -1){
				    	throw new BusException("不支持"+ extName +"类型的相片上传！");
				    }
				    //文件高宽验证
					long size = mFile.getSize();
					if(size < 10 || size > 10 * 1024 * 1024){
						throw new BusException("相片大小不能小于10K，大于10M！");
					}
			        BufferedImage sourceImg = ImageIO.read(mFile.getInputStream());
			        int height = sourceImg.getHeight();
			        int width = sourceImg.getWidth();
			        if(height < 0 || width < 0){
			        	throw new BusException("相片尺寸不能小于250x250！");
			        }
			        //文件路径
			        String path = request.getSession().getServletContext().getRealPath("/") + "upload/commend/";
			        File Subfile = new File(path);
			        if (!Subfile.exists()) {
						Subfile.mkdir();
					}
			        filename = ToolBox.getFileRandName();
			        File addFile = new File(path + "/" + filename + "_commend.jpg");
		        	//文件保存
					mFile.transferTo(addFile);
			    }
	        }
			//
			//页面获取参数
			String commendType = multipartRequest.getParameter("acommendType"); 
			String commendName = multipartRequest.getParameter("acommendName");
			String commendUrl = multipartRequest.getParameter("acommendUrl");
			ToolBox.busExcep(commendType);
			ToolBox.busExcep(commendName);
			ToolBox.busExcep(commendUrl);
			CommendInfo commend = new CommendInfo();
			commend.setCommendName(commendName);
			commend.setCommendType(Integer.parseInt(commendType));
			commend.setCommendUrl(commendUrl);
			if(!"".equals(filename)){
				commend.setCommendImg("/upload/commend/"+ filename + "_commend.jpg");
			}
			commend.setCreateTime(DateTool.instance.getCurrentDateString());
			//操作值
			String operatorValue = "名称："+commend.getCommendName()+"；类型："+commend.getCommendType()+"；地址：" + 
						commend.getCommendUrl()+"；应用id："+commend.getCommendUrl()+"；时间："+commend.getCreateTime();
			//操作动作
			String operatorAction = "新增应用推荐";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//添加
			commendInfoServ.create(commend, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/commend_list.do", request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/commend_list.do", request);
	}
	
	/**
	 * 显示信息
	 * @author tyj
	 * @param commendId
	 * @param request
	 * @param response
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/commend_view")
	public void findById(int commendId, HttpServletRequest request , HttpServletResponse response){
		CommendInfo commend = null;
		try {
			//查询信息
			commend = commendInfoServ.queryById(commendId);
			//绑定到页面
			ajaxJson(commend, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/app/commend_list.do", request);
		}
	}
	
	/**
	 * 修改
	 * @author tyj
	 * @param commendId
	 * @param request
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/commend_edit")
	public String edit(int commendId, HttpServletRequest request){
		try {
			CommendInfo commend = new CommendInfo();
			//查询信息
			CommendInfo qco = commendInfoServ.queryById(commendId);
			ToolBox.isNull(qco);
			//转型为MultipartHttpRequest
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;   
	        //根据页面的name名称得到上传的文件  
	        MultipartFile mFile  =  multipartRequest.getFile("file");
	        if(mFile != null){
	        	//获取文件名
			    String filename = mFile.getOriginalFilename();
			    if(!"".equals(filename)){
			    	 //获取文件扩展名
				    String extName = filename.substring(filename.lastIndexOf(".") + 1, filename.length()).toLowerCase();
				    //文件类型验证
				    if("jpg,png,jpeg,gif,bmp".indexOf(extName) == -1){
				    	throw new BusException("不支持"+ extName +"类型的相片上传！");
				    }
				    //文件高宽验证
					long size = mFile.getSize();
					if(size < 10 || size > 10 * 1024 * 1024){
						throw new BusException("相片大小不能小于10K，大于10M！");
					}
			        BufferedImage sourceImg = ImageIO.read(mFile.getInputStream());
			        int height = sourceImg.getHeight();
			        int width = sourceImg.getWidth();
			        if(height < 0 || width < 0){
			        	throw new BusException("相片尺寸不能小于250x250！");
			        }
			        //文件路径
			        String path = request.getSession().getServletContext().getRealPath("/") + "upload/commend/";
			        File Subfile = new File(path);
			        if (!Subfile.exists()) {
						Subfile.mkdir();
					}
			        filename = ToolBox.getFileRandName();
			        File addFile = new File(path + "/" + filename + "_commend.jpg");
		        	//文件保存
					mFile.transferTo(addFile);
					String dst = request.getSession().getServletContext().getRealPath("/") + qco.getCommendImg();
					File delFile = new File(dst);
					if (delFile.exists()) {
						delFile.delete();
					}
					commend.setCommendImg("/upload/commend/"+ filename + "_commend.jpg");
			    }
	        }
			//页面获取参数
			String commendType = multipartRequest.getParameter("commendType"); 
			String commendName = multipartRequest.getParameter("commendName");
			String commendUrl = multipartRequest.getParameter("commendUrl");
			ToolBox.isNull(commendId);
			ToolBox.busExcep(commendType);
			ToolBox.busExcep(commendName);
			ToolBox.busExcep(commendUrl);
			commend.setCommendId(commendId);
			commend.setCommendName(commendName);
			commend.setCommendType(Integer.parseInt(commendType));
			commend.setCommendUrl(commendUrl);
			//操作值
			String operatorValue = "id："+commend.getCommendId()+"；名称："+commend.getCommendName()+"；类型："+commend.getCommendType()+"；地址：" + 
						commend.getCommendUrl()+"；应用id："+commend.getCommendUrl()+"；时间：" + DateTool.instance.getCurrentDateString();
			//操作动作
			String operatorAction = "更新应用推荐";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改
			commendInfoServ.modify(commend, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/commend_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/commend_list.do", request);
	}
	
	/**
	 * 删除角色
	 * @author tyj
	 * @param request
	 * @date Mar 8, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/commend_delete")
	public String delete(int commendId, String page, HttpServletRequest request){
		try {
			//查询
			CommendInfo commend = commendInfoServ.queryById(commendId);
			ToolBox.isNull(commend);
			//操作值
			String operatorValue = "id："+commend.getCommendId()+"；名称："+commend.getCommendName()+"；类型："+commend.getCommendType()+"；地址：" + 
						commend.getCommendUrl()+"；应用id："+commend.getCommendUrl()+"；时间：" + DateTool.instance.getCurrentDateString();
			//操作动作
			String operatorAction = "删除应用推荐";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除
			commendInfoServ.remove(commendId, log);
			//删除文件
			String dst = request.getSession().getServletContext().getRealPath("/") + commend.getCommendImg();
			File delFile = new File(dst);
			if (delFile.exists()) {
				delFile.delete();
			}
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/commend_list.do", request);
		}
		return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/commend_list.do", request);
	}
	
	 /** 提供给前台用的接口
		 * @author tyj
		 * @param request
		 * @param response
		 * @date Mar 5, 2015
		 * @modify
		 */
		@SuppressWarnings("unchecked")
		@RequestMapping("/app/commend/queryAll")
		public void interfaceFindAll(HttpServletRequest request, HttpServletResponse response){
			JSONObject json = new JSONObject();
			JSONArray jsonObject = null;
			Map<String, String> map = new HashMap<String, String>(); 
			try {
				//查询信息
				String commendName = request.getParameter("commendName");
				String commendType = request.getParameter("commendType");
				//参数
				Map<String, Object> param = new HashMap<String, Object>();
				if(commendName != null && commendName != ""){
					param.put("commendName", "%"+commendName+"%");
				}
				if(commendType != null && commendType != ""){
					param.put("commendType", commendType);
				}
				List<CommendInfo> commend = commendInfoServ.queryAll(param);
				if(commend != null && commend.size() > 0){
					map.put("code", "200");
					map.put("message", "查询成功");
					jsonObject = JSONArray.fromObject(commend);
				}else{
					map.put("code", "400");
					map.put("message", "查询失败");
				}
			} catch (Exception e) {
				map.put("code", "500");
				map.put("message", "查询错误");
			}
			json.put("meta", map);
			json.put("data", jsonObject);
			//绑定到页面
			ajaxJson(json, response);
		}
	
	
}
