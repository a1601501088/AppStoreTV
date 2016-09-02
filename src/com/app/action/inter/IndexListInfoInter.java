package com.app.action.inter;

import com.alibaba.fastjson.JSONObject;
import com.app.action.UserPayAction;
import com.app.model.AppInfo;
import com.app.model.IndexListInfo;
import com.app.model.PackgeInfo;
import com.app.service.IAppInfoService;
import com.app.service.IIndexListInfoService;
import com.app.service.IPackgeInfo;
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
 * Created by Administrator on 2016/6/22.
 */
@Controller
public class IndexListInfoInter extends BaseAction{
    private Logger logger = Logger.getLogger("log");
    private Log log = LogFactory.getLog(UserPayAction.class);
    @Resource
    private IPackgeInfo packgeServ;
    @Resource
    private IAppInfoService appInfoServ;
    @Resource
    private FileUpload fileUpload;
    @Resource
    private  IIndexListInfoService indexListInfoServ;

    /**
     * 给前台接口调用
     * @param response
     */
    @RequestMapping(value = "/quaryAllList",method = RequestMethod.POST)
    public void quaryAllList(HttpServletResponse response){
        String code = "400";
        String message = "fail";
        List<IndexListInfo> indexListInfos = null;
        try {
            indexListInfos = indexListInfoServ.quaryAllList();
            for (int i=0;i<indexListInfos.size(); i++){
                IndexListInfo info = indexListInfos.get(i);
                String execteParaTop = info.getTopExectePara();
                String execteParaDown = info.getBottomExectePara();
                if (execteParaTop!=null){
                    if (execteParaTop.contains("appStoreDown")){
                        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(execteParaTop);
                        String appId = jsonObj.getString("appStoreDown");
                        AppInfo appInfo = appInfoServ.queryById(appId);
                        info.setTopExectePara(JSONObject.toJSON(appInfo).toString());
                    }
                    if (execteParaTop.contains("appStoreGameUrl")){
                        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(execteParaTop);
                        String urlId = jsonObj.getString("appStoreGameUrl");
                        PackgeInfo packgeInfo = packgeServ.queryById(urlId);
                        info.setTopExectePara(JSONObject.toJSON(packgeInfo).toString());
                    }
                }
                if (execteParaDown!=null){
                    if (execteParaDown.contains("appStoreDown")){
                        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(execteParaDown);
                        String appId = jsonObj.getString("appStoreDown");
                        AppInfo appInfo = appInfoServ.queryById(appId);
                        info.setBottomExectePara(JSONObject.toJSON(appInfo).toString());
                    }
                    if (execteParaDown.contains("appStoreGameUrl")){
                        net.sf.json.JSONObject jsonObj = net.sf.json.JSONObject.fromObject(execteParaDown);
                        String urlId = jsonObj.getString("appStoreGameUrl");
                        PackgeInfo packgeInfo = packgeServ.queryById(urlId);
                        info.setBottomExectePara(JSONObject.toJSON(packgeInfo).toString());
                    }
                }


            }
            code = "200";
            message = "success";
        } catch (Exception e) {
            e.printStackTrace();
            code = "500";
            message = "error";
        }
        JSONObject js = new JSONObject();
        js.put("code",code);
        js.put("message",message);
        js.put("result",indexListInfos);
        ajaxJson(js.toString(),response);

    }

    /**
     * 后台页面使用
     * @param request
     * @return
     */
    @RequestMapping(value = "/system/app/indexInfo_list")
    public  String findList(HttpServletRequest request){
        try {
            String indexName = request.getParameter("indexName");
            String currentPage = request.getParameter("page");
            //参数
            Map<String, Object> map = new HashMap<String, Object>();
            if(indexName != null && !"".equals(indexName)){
                map.put("indexName", "%"+indexName+"%");
            }
            //构建分页对象
            Pagination pagination = findPageList(map, indexListInfoServ, currentPage, 15);
            //绑定到页面
            request.setAttribute("pagination", pagination);
            request.setAttribute("indexName", indexName);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/main.jsp", request);
        }
        return directReturn("/system/app/indexInfo_list.jsp");
    }


    /**
     * 后台页面使用
     * @param request
     * @return
     */
    @RequestMapping("/system/app/indexInfo_add")
    public String add( IndexListInfo indexListInfo, HttpServletRequest request) {
        try {
            Map<String,String> paths = fileUpload.upload(request,"upload/homeRightImage" );
            indexListInfo.setTopImageUrl(paths.get("icon1"));
            indexListInfo.setBottomImageUrl(paths.get("icon2"));
            //操作值
            String operatorValue = "名称："+indexListInfo.getIndexName()+"；时间："+indexListInfo.getCreateTime();
            //操作动作
            String operatorAction = "新增首页右边item";
            //构建日志
            OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
            //添加
            indexListInfoServ.create(indexListInfo, log);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/app/indexInfo_list.do", request);
        }
        return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/indexInfo_list.do", request);
    }

    /**
     * 后台页面使用 ,修改框的数据回显
     * @param
     * @return
     */
    @RequestMapping("/system/app/indexInfo_view")
    public void view( String indexId, HttpServletResponse response,HttpServletRequest request) {
        try {
            //查询信息
            IndexListInfo indexListInfo = indexListInfoServ.queryById(indexId);
            //绑定到页面
            ajaxJson(indexListInfo, response);
        } catch (Exception e) {
            errorReturn(log, e, "/system/app/indexInfo_list.do", request);
        }

    }
    /**
     * 后台页面使用 ,修改
     * @param
     * @return
     */
    @RequestMapping("/system/app/indexInfo_edit")
    public String edit(String indexItemId,IndexListInfo indexListInfo,HttpServletResponse response,HttpServletRequest request){
        try {

           //执行上传图片
          Map<String, String> upload = fileUpload.upload(request,"upload/homeRightImage");
          //更新图片地址
           indexListInfo.setTopImageUrl(upload.get("icon1"));
          indexListInfo.setBottomImageUrl(upload.get("icon2"));
            //修改文本数据
            if (!TextUtils.isEmpty(indexItemId)){
                indexListInfo.setIndexId(Long.parseLong(indexItemId));
            }

            //修改数据库
            indexListInfoServ.modify(indexListInfo);
            return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/indexInfo_list.do", request);
            } catch (Exception e) {
                 e.printStackTrace();
            return errorReturn(log,e,"/system/app/indexInfo_list.do",request);
        }
    }
    /**
     * 后台页面使用 ,删除
     * @param
     * @return
     */
    @RequestMapping("/system/app/indexInfo_delete")
    public String delete(int indexId,HttpServletRequest request){

        try {
            indexListInfoServ.remove(indexId);
        } catch (Exception e) {
            e.printStackTrace();
            return errorReturn(log,e,"/system/app/indexInfo_list.do",request);
        }
        return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/indexInfo_list.do", request);
    }
}
