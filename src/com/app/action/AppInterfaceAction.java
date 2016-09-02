package com.app.action;

import com.app.model.AppInterface;
import com.app.service.IAppInterfaceService;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.basicframe.utils.DateTool;
import com.basicframe.utils.page.Pagination;
import net.sf.json.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
public class AppInterfaceAction extends BaseAction {
	
	private Log logger = LogFactory.getLog(AppInterfaceAction.class);
	
	@Resource
	private IAppInterfaceService appInterfaceServ;
	
	
	@RequestMapping(value="/back", method = RequestMethod.GET)
	public String edit(HttpServletRequest request, HttpServletResponse response) {
		String info = request.getParameter("INFO");
		if (info != null && !"".equals(info)) {
			try {
				info = new String(info.getBytes("iso-8859-1"), "gbk");
			} catch (UnsupportedEncodingException e2) {
				// TODO Auto-generated catch block
				e2.printStackTrace();
			}

			String info2 = "<results>" + info + "</results>";
			StringReader sr = new StringReader(info2);
			InputSource is = new InputSource(sr);
			Document doc = null;
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();
			String result = "";
			String resultDesc = "";
			String transactionId = "";
			try {
				DocumentBuilder builder = factory.newDocumentBuilder();

				doc = builder.parse(is);
				NodeList list = doc.getElementsByTagName("transactionID");
				if (list.getLength() > 0) {
					transactionId = list.item(0).getFirstChild().getNodeValue();
				}
				NodeList list1 = doc.getElementsByTagName("result");
				if (list1.getLength() > 0) {
					result = list1.item(0).getFirstChild().getNodeValue();
				}
				NodeList list2 = doc.getElementsByTagName("description");
				if (list2.getLength() > 0) {
					resultDesc = list2.item(0).getFirstChild().getNodeValue();
				}
			} catch (ParserConfigurationException e1) {
				e1.printStackTrace();
			} catch (SAXException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}

			AppInterface vo = new AppInterface();
			vo.setTransactionId(transactionId);
			vo.setResult(result);
			vo.setUpdateTime(DateTool.instance.getCurrentDateString());
			vo.setResultDesc(info);
			try {
				appInterfaceServ.modify(vo);
			} catch (BusException e) {
				// TODO Auto-generated catch block
			}
			request.setAttribute("transactionId", transactionId);
			request.setAttribute("result", result);
			request.setAttribute("desc", resultDesc);
			return directReturn("system/app/appPayDetail.jsp");
		} else {
			return directReturn("system/app/appPayDetail.jsp");
		}
		
	}
	
	@ResponseBody 
	@RequestMapping(value="/create", method = RequestMethod.POST)
	public void create(@RequestParam("json") String json, HttpServletResponse response,HttpServletRequest request){
		logger.info("支付入参：" + json);
		Map<String, Object> dataMap = new LinkedHashMap<String, Object>();
		String message = "400";
		String code ="fail";
		AppInterface vo=new AppInterface();
		try {
			//JSONObject object = JSON.parseObject(json);
			
			JSONObject object = JSONObject.fromObject(json);			
			String userToken = object.getString("userToken");
			String backUrl=object.getString("backUrl");
			String categoryId=object.getString("categoryId");
			String contentId=object.getString("contentId");
			String contentType=object.getString("contentType");
			Date createTime=new Date();
			String key=object.getString("key");
			String notifyUrl=object.getString("notifyUrl");
			String optFlag=object.getString("optFlag");
			String price=object.getString("price");
			String productId=object.getString("productId");
			String productName=object.getString("productName");
			String purchaseType=object.getString("purchaseType");
//			String result=object.getString("result");
			String spId=object.getString("spId");
			String transactionId=object.getString("transactionId");
			Date updateTime=new Date();
			String userId=object.getString("userId");
//			String resultDesc = object.getString("resultDesc");
			vo.setBackUrl(backUrl);
			vo.setCategoryId(categoryId);
			vo.setContentId(contentId);
			vo.setContentType(contentType);
			vo.setCreateTime(DateTool.instance.getCurrentDateString());
			vo.setKey(key);
			vo.setNotifyUrl(notifyUrl);
			vo.setOptFlag(optFlag);
			vo.setPrice(price);
			vo.setProductId(productId);
			vo.setProductName(productName);
			vo.setPurchaseType(purchaseType);
//			vo.setResult(result);
			vo.setSpId(spId);
			vo.setTransactionId(transactionId);
			vo.setUpdateTime(DateTool.instance.getCurrentDateString());
			vo.setUserId(userId);
			vo.setUserToken(userToken);
			//检查登陆状态
			appInterfaceServ.create(vo);
			code = "200";
			message = "success";
		} catch (BusException e) {
			message = e.getMessage();
		} catch (Exception e) {
			e.printStackTrace();
			code = "500";
			message = "error";
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("code", code);
		jsonObject.put("message", message);
		jsonObject.put("data", dataMap);
		logger.info("获取群商品详情结果：" + jsonObject.toString());
		ajaxJson(jsonObject.toString(), response);
	}
	
	/**
	 * 列表
	 * @author tyj
	 * @param page 当前页
	 * @param request
	 * @return 返回到appInfo_list.jsp
	 * @date Feb 24, 2011
	 * @modify
	 */
	@RequestMapping("/system/app/appInterface_list")
	public String findList(String page, HttpServletRequest request){
//		String url=request.getLocalAddr();
//		String url1=request.getPathInfo();
//		String url2=request.getRealPath("/");
//		System.out.println("url:"+url);	
//		System.out.println("url1:"+url1);
//		System.out.println("url2:"+url2);
		
		
		try {
			String msgName = request.getParameter("msgName");
			String currentPage = request.getParameter("page");
			//参数
			Map<String, Object> map = new HashMap<String, Object>();
			if(msgName != null && msgName != ""){
				map.put("msgName", "%"+msgName+"%");
			}
			//构建分页对象
			Pagination pagination = findPageList(map, appInterfaceServ, currentPage, 15);
			//绑定到页面
			request.setAttribute("pagination", pagination);
			request.setAttribute("msgName", msgName);
		} catch (Exception e) {
			//业务异常、系统异常包装错误返回
			return errorReturn(logger, e, "/system/main.jsp", request);
		}
		return directReturn("/system/app/appInterface_list.jsp");
	}
	
	
	@RequestMapping("/system/app/appInterface_view")
	public void findById(String id, HttpServletRequest request , HttpServletResponse response){
		try {
			//查询
			AppInterface msg = appInterfaceServ.queryById(id);
			//绑定到页面
			ajaxJson(msg, response);
		} catch (Exception e) {
			errorReturn(logger, e, "/system/app/appInterface_list.do", request);
		}
	}
	public static void main(String[] args) {
		String a ="·ÅÆú¶©¹º";
		String b=null;
		try {
			b= new String(a.getBytes("iso-8859-1"),"gbk");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("@@@@@@@:"+b);
	}
	
	
	
}
