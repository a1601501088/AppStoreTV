package com.app.action.inter;

import com.alibaba.fastjson.JSONObject;
import com.app.action.UserPayAction;
import com.app.model.AppInfo;
import com.app.model.IndexItemInfo;
import com.app.service.IAppInfoService;
import com.app.service.IIndexItemInfo;
import com.app.util.FileUpload;
import com.app.util.TextUtils;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.page.Pagination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

/**
 * Created by Administrator on 2016/6/21.
 */
@Controller
public class IndexItemInfoInter extends BaseAction {
    private Logger logger = Logger.getLogger("log");
    private Log log = LogFactory.getLog(UserPayAction.class);
    @Resource
    private FileUpload fileUpload;
    @Resource
    private IIndexItemInfo indexItemInfoServ;
    @Resource
    private IAppInfoService appInfoServ;

    @RequestMapping(value = "/quaryAllItem", method = RequestMethod.POST)
    public void queryAllItems(HttpServletResponse response) {
        String code = "400";
        String message = "fial";
        List<IndexItemInfo> indexItemInfos = null;
        try {
            indexItemInfos = indexItemInfoServ.queryAllItem();
            for (int i = 0; i < indexItemInfos.size(); i++) {
                IndexItemInfo info = indexItemInfos.get(i);
                String exectePara = info.getExectePara();
                if (exectePara != null) {
                    if (exectePara.contains("appStoreDown")) {
                        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(exectePara);
                        String appId = jsonObj.getString("appStoreDown");
                        AppInfo appInfo = appInfoServ.queryById(appId);
                        info.setExectePara(JSONObject.toJSON(appInfo).toString());
                    }
                }
            }

            code = "200";
            message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            code = "400";
            message = "fial";
        }
        JSONObject js = new JSONObject();
        js.put("code", code);
        js.put("message", message);
        js.put("result", indexItemInfos);
        ajaxJson(js.toString(), response);
    }


    /**
     * 列表
     * @author tyj
     * @param page 当前页
     * @param request
     * @return 返回到indexItem_list.jsp
     * @date Feb 24, 2015
     * @modify
     */
    @RequestMapping("/system/app/indexItem_list")
    public String findlList(String page, HttpServletRequest request){
        try {
            String indexItemName = request.getParameter("indexItemName");
            String currentPage = request.getParameter("page");
            //参数
            Map<String, Object> map = new HashMap<String, Object>();
            if(indexItemName != null && !"".equals(indexItemName)){
                map.put("indexItemName", "%"+indexItemName+"%");
            }
            //构建分页对象
            Pagination pagination = findPageList(map, indexItemInfoServ, currentPage, 15);
            //绑定到页面
            request.setAttribute("pagination", pagination);
            request.setAttribute("indexItemName", indexItemName);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/main.jsp", request);
        }
        return directReturn("/system/app/indexItem_list.jsp");
    }




    /**
     * 添加
     * @author tyj
     * @param request
     * @date Mar 5, 2015
     * @modify
     */
    @RequestMapping("/system/app/indexItem_add")
    public String add(IndexItemInfo indexItemInfo,HttpServletRequest request){
        try {

            //图片上传
            Map<String, String> map = fileUpload.upload(request,"upload/homeLeftImage");
            indexItemInfo.setImageUrl(map.get("icon1"));
            //操作值
            String operatorValue = "名称："+indexItemInfo.getIndexItemName()+"；序号："+indexItemInfo.getIndexItemId();
            //操作动作
            String operatorAction = "新增首页左侧Item";
            //构建日志
            OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
            //添加
            indexItemInfoServ.create(indexItemInfo, log);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/app/indexItem_list.do", request);
        }
        return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/indexItem_list.do", request);
    }
    /**
     * 后台页面使用 ,修改框的数据回显
     * @param
     * @return
     */
    @RequestMapping("/system/app/indexItem_view")
    public void view( String indexItemId, HttpServletResponse response,HttpServletRequest request) {
        try {
            //查询信息
            IndexItemInfo indexItemInfo = indexItemInfoServ.queryById(indexItemId);
            //绑定到页面
            ajaxJson(indexItemInfo, response);
        } catch (Exception e) {
            errorReturn(log, e, "/system/app/indexItem_list.do", request);
        }

    }
    /**
     * 后台页面使用 ,删除
     * @param
     * @return
     */
    @RequestMapping("/system/app/indexItem_delete")
    public String delete(int indexItemId,HttpServletRequest request){

        try {
            indexItemInfoServ.remove(indexItemId);
        } catch (Exception e) {
            e.printStackTrace();
            return errorReturn(log,e,"/system/app/indexItem_list.do",request);
        }
        return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/indexItem_list.do", request);
    }

    /**
     * 后台页面使用 ,修改
     * @param
     * @return
     */
    @RequestMapping("/system/app/indexItem_edit")
    public String edit(String oldIndexId, IndexItemInfo indexItemInfo, HttpServletResponse response, HttpServletRequest request){
        try {
            //执行上传图片
            Map<String, String> upload = fileUpload.upload(request,"upload/homeRightImage");
            //更新图片地址
            indexItemInfo.setImageUrl(upload.get("icon1"));


            //修改数据库
            if (!TextUtils.isEmpty(oldIndexId)){
                indexItemInfo.setOldIndexId(oldIndexId);
                indexItemInfoServ.modify(indexItemInfo);
            }
            return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/indexItem_list.do", request);
        } catch (Exception e) {
            e.printStackTrace();
            return errorReturn(log,e,"/system/app/indexItem_list.do",request);
        }
    }
}
