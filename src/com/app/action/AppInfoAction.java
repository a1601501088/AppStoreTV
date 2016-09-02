package com.app.action;

import com.Config;
import com.app.model.AppInfo;
import com.app.service.IAppInfoService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.CommonUtil;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.ToolBox;
import com.basicframe.utils.page.Pagination;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Description: 应用action</p>
 *
 * <p>Copyright: Copyright (c) 2015</p>
 *
 * <p>Company: </p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
@Controller
public class AppInfoAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(AppInfoAction.class);
	
	@Resource
	private IAppInfoService appInfoServ;
	
	
	/**
	 * 列表
	 * @author tyj
	 * @param page 当前页
	 * @param request
	 * @return 返回到appInfo_list.jsp
	 * @date Feb 24, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appInfo_list")
	public String findlList(String page, HttpServletRequest request){
		try {
			String appName = request.getParameter("appName");
			String appShortName = request.getParameter("appShortName");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(appName != null && appName != ""){
				map.put("appName", "%"+appName+"%");
			}
			if(appShortName != null && appShortName != ""){
				map.put("appShortName", "%"+appShortName+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, appInfoServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("appName", appName);
			request.setAttribute("appShortName", appShortName);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/app/appInfo_list.jsp");
	}
	
	/**
	 * 添加
	 * @author tyj
	 * @param request
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appInfo_add")
	public String add(HttpServletRequest request){
		try {
			//文件路径
			String path =  Config.UPLOAD_BASE_PATH+"/upload/app";
			//转型为MultipartHttpRequest
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        
	        String filename = "";
	        //根据页面的name名称得到上传的文件  
	        MultipartFile mFile  =  multipartRequest.getFile("aiconfile");
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
					if(size < 10 || size > 3 * 1024 * 1024){
						throw new BusException("相片大小不能小于10K，大于3M！");
					}
			        BufferedImage sourceImg = ImageIO.read(mFile.getInputStream());
			        int height = sourceImg.getHeight();
			        int width = sourceImg.getWidth();
			        if(height < 0 || width < 0){
			        	throw new BusException("相片尺寸不能小于250x250！");
			        }


			        File Subfile = new File(path);
			        if (!Subfile.exists()) {
						Subfile.mkdirs();
					}
			        filename = ToolBox.getFileRandName();
			        File addFile = new File(path + "/" + filename + "_app_icon.jpg");
		        	//文件保存
					mFile.transferTo(addFile);
			    }
	        }
	        
	        String filename2 = "";
	        //根据页面的name名称得到上传的文件  
	        MultipartFile fFile  =  multipartRequest.getFile("aimgfile");
	        if(fFile != null){
	        	//获取文件名
			    filename2 = fFile.getOriginalFilename();
			    if(!"".equals(filename2)){
			    	 //获取文件扩展名
				    String extName = filename2.substring(filename2.lastIndexOf(".") + 1, filename2.length()).toLowerCase();
				    //文件类型验证
				    if("jpg,png,jpeg,gif,bmp".indexOf(extName) == -1){
				    	throw new BusException("不支持"+ extName +"类型的相片上传！");
				    }
				    //文件高宽验证
					long size = fFile.getSize();
					if(size < 10 || size > 10 * 1024 * 1024){
						throw new BusException("相片大小不能小于10K，大于10M！");
					}
			        BufferedImage sourceImg = ImageIO.read(fFile.getInputStream());
			        int height = sourceImg.getHeight();
			        int width = sourceImg.getWidth();
			        if(height < 0 || width < 0){
			        	throw new BusException("相片尺寸不能小于250x250！");
			        }

			        File Subfile = new File(path);
			        if (!Subfile.exists()) {
						Subfile.mkdirs();
					}
			        filename2 = ToolBox.getFileRandName();
			        File addFile = new File(path + "/" + filename2 + "_app_img.jpg");
		        	//文件保存
					fFile.transferTo(addFile);
			    }
	        }
			
			//页面获取参数
			String classId = request.getParameter("aclassId");
			String appName = request.getParameter("aappName");
			String appShortName = request.getParameter("aappShortName");
			String appVersion = request.getParameter("aappVersion");
			String appUrl = request.getParameter("aappUrl");
			String appDesc = request.getParameter("aappDesc");
			String appSize = request.getParameter("aappSize");
			String appCount = request.getParameter("aappCount");
			String appLevel = request.getParameter("aappLevel");
			String jsonData = request.getParameter("jjsonData");
			String provider = request.getParameter("aprovider");
			String price = request.getParameter("aprice");
			String freeFrequency = request.getParameter("afreeFrequency");
			String chargeType = request.getParameter("achargeType");
			String packge = request.getParameter("apackge");
			String showName = request.getParameter("ashowName");
			String appCode = request.getParameter("aappCode");
			String spId = request.getParameter("aspId");
			String productId = request.getParameter("aproductId");
			System.out.println(jsonData);

			boolean level = CommonUtil.isNumeric(appLevel);
			if(!level){
				throw new BusException("等级必须为数字");
			}


			//对象附值
			AppInfo app = new AppInfo();
			app.setClassId(Integer.parseInt(classId));
			app.setAppName(appName);
			app.setAppShortName(appShortName);
			if(!"".equals(filename)){
				app.setAppIcon("/upload/app/" + filename + "_app_icon.jpg");
			}
			if(!"".equals(filename2)){
				app.setAppImg("/upload/app/" + filename2 + "_app_img.jpg");
			}
			app.setAppVersion(appVersion);
			app.setAppUrl(appUrl);
			app.setAppDesc(appDesc);
			app.setAppSize(appSize);
			app.setAppCount(Integer.parseInt(appCount));
			app.setAppLevel(Integer.parseInt(appLevel));
			app.setJsonData(jsonData);
			app.setCreateTime(DateTool.instance.getCurrentDateString());
			app.setUpdateTime(DateTool.instance.getCurrentDateString());
			app.setProvider(provider);
			app.setAppCode(appCode);
			app.setFreeFrequency(freeFrequency);
			app.setChargeType(chargeType);
			app.setPrice(price);
			app.setPackge(packge);
			app.setShowName(showName);
			app.setSpId(spId);
			app.setProductId(productId);
			//操作值
			String operatorValue = "app名称："+app.getAppName()+"；url："+app.getAppUrl()+"；时间："+app.getCreateTime();
			//操作动作
			String operatorAction = "新增应用";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//添加
			appInfoServ.create(app, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appInfo_list.do", request);
		}
		return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/appInfo_list.do", request);
	}
	
	/**
	 * 显示角色信息
	 * @author tyj
	 * @param appId
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@RequestMapping("/system/app/appInfo_view")
	public void findById(int appId, HttpServletRequest request , HttpServletResponse response){
		try {
			//查询信息
			AppInfo app = appInfoServ.queryById(appId);
			//绑定到页面
			ajaxJson(app, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/app/appInfo_list.do", request);
		}
	}
	
	/**
	 * 修改角色
	 * @author tyj
	 * @param appId
	 * @param request
	 * @date Mar 5, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appInfo_edit")
	public String edit(int appId, HttpServletRequest request){
		//文件路径
		String path = Config.UPLOAD_BASE_PATH+"/upload/app";
		try {
			//查询信息
			AppInfo app = appInfoServ.queryById(appId);
			
			//转型为MultipartHttpRequest
	        MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
	        
	        String filename = "";
	        //根据页面的name名称得到上传的文件  
	        MultipartFile mFile  =  multipartRequest.getFile("iconfile");
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
					if(size < 10 || size > 3 * 1024 * 1024){
						throw new BusException("相片大小不能小于10K，大于3M！");
					}
			        BufferedImage sourceImg = ImageIO.read(mFile.getInputStream());
			        int height = sourceImg.getHeight();
			        int width = sourceImg.getWidth();
			        if(height < 0 || width < 0){
			        	throw new BusException("相片尺寸不能小于250x250！");
			        }


			        File Subfile = new File(path);
			        if (!Subfile.exists()) {
						Subfile.mkdirs();
					}
			        filename = ToolBox.getFileRandName();
			        File addFile = new File(path + "/" + filename + "_app_icon.jpg");
		        	//文件保存
					mFile.transferTo(addFile);
					//删除文件
					String dst = path + app.getAppIcon();
					File delFile = new File(dst);
					if (delFile.exists()) {
						delFile.delete();
					}
					app.setAppIcon("/upload/app/" + filename + "_app_icon.jpg");
			    }
	        }
	        
	        String filename2 = "";
	        //根据页面的name名称得到上传的文件  
	        MultipartFile fFile  =  multipartRequest.getFile("imgfile");
	        if(fFile != null){
	        	//获取文件名
			    filename2 = fFile.getOriginalFilename();
			    if(!"".equals(filename2)){
			    	 //获取文件扩展名
				    String extName = filename2.substring(filename2.lastIndexOf(".") + 1, filename2.length()).toLowerCase();
				    //文件类型验证
				    if("jpg,png,jpeg,gif,bmp".indexOf(extName) == -1){
				    	throw new BusException("不支持"+ extName +"类型的相片上传！");
				    }
				    //文件高宽验证
					long size = fFile.getSize();
					if(size < 10 || size > 10 * 1024 * 1024){
						throw new BusException("相片大小不能小于10K，大于10M！");
					}
			        BufferedImage sourceImg = ImageIO.read(fFile.getInputStream());
			        int height = sourceImg.getHeight();
			        int width = sourceImg.getWidth();
			        if(height < 0 || width < 0){
			        	throw new BusException("相片尺寸不能小于250x250！");
			        }

			        File Subfile = new File(path);
			        if (!Subfile.exists()) {
						Subfile.mkdirs();
					}
			        filename2 = ToolBox.getFileRandName();
			        File addFile = new File(path + "/" + filename2 + "_app_img.jpg");

		        	//文件保存
					fFile.transferTo(addFile);
					//删除文件
					String dst = path + app.getAppImg();
					File delFile = new File(dst);
					if (delFile.exists()) {
						delFile.delete();
					}
					app.setAppImg("/upload/app/" + filename2 + "_app_img.jpg");
			    }
	        }
			
			//页面获取参数
			String classId = request.getParameter("classId");
			String appName = request.getParameter("appName");
			String appShortName = request.getParameter("appShortName");
			//String appIcon = request.getParameter("appIcon");
			String appVersion = request.getParameter("appVersion");
			String appUrl = request.getParameter("appUrl");
			//String appImg = request.getParameter("appImg");
			String appDesc = request.getParameter("appDesc");
			String appSize = request.getParameter("appSize");
			String appCount = request.getParameter("appCount");
			String appLevel = request.getParameter("appLevel");
			String jsonData = request.getParameter("jsonData");
			String provider = request.getParameter("provider");
			String price = request.getParameter("price");
			String freeFrequency = request.getParameter("freeFrequency");
			String chargeType = request.getParameter("chargeType");
			String packge = request.getParameter("packge");
			String showName = request.getParameter("showName");
			String appCode = request.getParameter("appCode");
			String spId = request.getParameter("spId");
			String productId = request.getParameter("productId");

			boolean level = CommonUtil.isNumeric(appLevel);
			if(!level){
				throw new BusException("等级必须为数字");
			}

			//对象附值
			app.setAppId(appId);
			app.setClassId(Integer.parseInt(classId));
			app.setAppName(appName);
			app.setAppShortName(appShortName);
			//app.setAppIcon(appIcon);
			app.setAppVersion(appVersion);
			app.setAppUrl(appUrl);
			//app.setAppImg(appImg);
			app.setAppDesc(appDesc);
			app.setAppSize(appSize);
			app.setAppCount(Integer.parseInt(appCount));
			app.setJsonData(jsonData);
			app.setAppLevel(Integer.parseInt(appLevel));
			app.setUpdateTime(DateTool.instance.getCurrentDateString());
			app.setProvider(provider);
			app.setAppCode(appCode);
			app.setFreeFrequency(freeFrequency);
			app.setChargeType(chargeType);
			app.setPrice(price);
			app.setPackge(packge);
			app.setShowName(showName);
			app.setSpId(spId);
			app.setProductId(productId);
			//操作值
			String operatorValue = "id："+app.getAppId() + "；app名称："+app.getAppName()+"；url："+app.getAppUrl()+"；时间："+app.getCreateTime();
			//操作动作
			String operatorAction = "更新应用";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//修改
			appInfoServ.modify(app, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appInfo_list.do", request);
		}
		return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/appInfo_list.do", request);
	}
	
	/**
	 * 删除
	 * @author tyj
	 * @param request
	 * @date Mar 8, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appInfo_delete")
	public String deleteRole(int appId, String page, HttpServletRequest request){
		try {
			String tomcat_path = System.getProperty("user.dir").replace("bin", "webapps").replace("tomcat6035STB","tomcat6035");
			//文件路径
			String path = tomcat_path+ Config.UPLOAD_BASE_PATH+"/upload/app";
			//查询信息
			AppInfo app = appInfoServ.queryById(appId);
			ToolBox.isNull(app);
			//删除文件
			String dst = path + app.getAppIcon();
			File delFile = new File(dst);
			if (delFile.exists()) {
				delFile.delete();
			}
			//删除文件
			String dst1 = path + app.getAppImg();
			File delFile1 = new File(dst1);
			if (delFile1.exists()) {
				delFile1.delete();
			}
			//操作值
			String operatorValue = "id："+app.getAppId() + "；app名称："+app.getAppName()+"；url："+app.getAppUrl()+"；时间："+app.getCreateTime();
			//操作动作
			String operatorAction = "删除应用";
			//构建日志
			OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
			//删除
			appInfoServ.remove(appId, log);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/app/appInfo_list.do", request);
		}
		return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/appInfo_list.do", request);
	}
	
	/** 提供给前台用的接口
	 * @author tyj
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/appinfo/queryAll")
	public void interfaceFindAll(HttpServletRequest request, HttpServletResponse response){
		System.out.println("hnfjgh");
		JSONObject json = new JSONObject();
		JSONArray jsonObject = null;
		Map<String, String> map = new HashMap<String, String>(); 
		try {
			//查询信息
			String appId = request.getParameter("appId");
			String appName = request.getParameter("appName");
			String classId = request.getParameter("classId");
			String appLevel = request.getParameter("appLevel");
			String appShortName = request.getParameter("appShortName");
			//参数
			Map<String, Object> param = new HashMap<String, Object>();
			if(appId != null && appId != ""){
				param.put("appId", Integer.parseInt(appId));
			}
			if(appName != null && appName != ""){
				param.put("appName", "%"+appName+"%");
			}
			if(classId != null && classId != ""){
				param.put("classId", classId);
			}
			if(appLevel != null && appLevel != ""){
				param.put("appLevel", appLevel);
			}
			if(classId != null && classId != ""){
				param.put("appShortName", "%"+appShortName+"%");
			}
			List<AppInfo> appInfo = appInfoServ.queryAll(param);
			if(appInfo != null && appInfo.size() > 0){
				map.put("code", "200");
				map.put("message", "查询成功");
				jsonObject = JSONArray.fromObject(appInfo);
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
	
	/** 提供给前台用的接口
	 * @author tyj
	 * @param request
	 * @param response
	 * @date Mar 5, 2015
	 * @modify
	 */
	@SuppressWarnings("unchecked")
	@RequestMapping("/app/appinfo/update")
	public void interfaceAddDiscussInfo(HttpServletRequest request, HttpServletResponse response){
		JSONObject json = new JSONObject();
		Map<String, String> map = new HashMap<String, String>(); 
		try {
			String appId = request.getParameter("appId");
			AppInfo app = new AppInfo();
			app.setAppId(Integer.parseInt(appId));
			app.setAppCount(1);
			//appInfoServ.modify(app);
			map.put("code", "200");
			map.put("message", "更新成功");
			
		} catch (Exception e) {
			map.put("code", "500");
			map.put("message", "更新错误");
		}
		json.put("meta", map);
		//绑定到页面
		ajaxJson(json, response);
	}
	
}
