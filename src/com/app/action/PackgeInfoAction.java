package com.app.action;

import com.app.model.PackgeInfo;
import com.app.service.IPackgeInfo;
import com.basicframe.common.action.BaseAction;
import com.basicframe.common.exception.BusException;
import com.basicframe.common.log.LogGPUtil;
import com.basicframe.sys.model.OperatorLog;
import com.basicframe.utils.PromptUtil;
import com.basicframe.utils.ToolBox;
import com.basicframe.utils.page.Pagination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Controller
public class PackgeInfoAction extends BaseAction {

    private Log log = LogFactory.getLog(PackgeInfoAction.class);
    private Logger logger = Logger.getLogger("log");
    @Resource
    private IPackgeInfo packgeServ;

    /**
     * @param request
     * @return
     */
    @RequestMapping("/system/app/packgeInfo_list")
    public String findList(HttpServletRequest request) {
        try {
            String packgeName = request.getParameter("packgeName");
            String currentPage = request.getParameter("page");
            //参数
            Map<String, Object> map = new HashMap<String, Object>();
            if (packgeName != null && !"".equals(packgeName)) {
                map.put("packgeName", "%" + packgeName + "%");
            }
            //构建分页对象
            Pagination pagination = findPageList(map, packgeServ, currentPage, 15);
            //绑定到页面
            request.setAttribute("pagination", pagination);
            logger.info(pagination.toString());
            request.setAttribute("packgeName", packgeName);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/main.jsp", request);
        }
        return directReturn("/system/app/packgeInfo_list.jsp");
    }

    //增加
    @RequestMapping("/system/app/packgeinfo_add")
    public String add(PackgeInfo packgeInfo, HttpServletRequest request) {
        try {

            //操作值
            String operatorValue = "类别名："+packgeInfo.getPackgeName()+"；时间："+packgeInfo.getPackgeCritme();
            //操作动作
            String operatorAction = "新增专区类别";
            //构建日志
            OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
            //添加
            packgeServ.create(packgeInfo, log);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/app/packgeInfo_list.do", request);
        }
        return successReturn(PromptUtil.ADD_SUCCESS, "/system/app/packgeInfo_list.do", request);
    }

    //根据ID查询
    @RequestMapping("/system/app/packgeInfo_view")
    public void findById(String packgeId, HttpServletRequest request, HttpServletResponse response) {

        try {
            PackgeInfo sp = packgeServ.queryById(packgeId);
            ajaxJson(sp, response);
        } catch (BusException e) {
            errorReturn(log, e, "/system/app/packgeInfo_list.do", request);
        }
    }


    @RequestMapping("/system/app/packgeInfo_check")
    public void findByName(String packgeName, HttpServletRequest request, HttpServletResponse response) {
        try {
            PackgeInfo sp = packgeServ.queryByName(packgeName);
            ajaxJson(sp, response);
        } catch (Exception e) {
            errorReturn(log, e, "/system/app/packgeInfo_list.do", request);
        }
    }

    //修改
    @RequestMapping("/system/app/packgeInfo_edit")
    public String edit(PackgeInfo packgeInfo, HttpServletRequest request) {
        try {
            ToolBox.isNull(packgeInfo);
            //操作值
            String operatorValue = "id：" + packgeInfo.getPackgeId() + "；类别名：" + packgeInfo.getPackgeName() + "；时间：" + packgeInfo.getPackgeCritme();
            //操作动作
            String operatorAction = "删除应用类别";
            //构建日志
            OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
            //修改
            packgeServ.modify(packgeInfo, log);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/app/packgeInfo_list.do", request);
        }
        return successReturn(PromptUtil.EDIT_SUCCESS, "/system/app/packgeInfo_list.do", request);
    }

    //删除
    @RequestMapping("/system/app/packgeInfo_delete")
    public String delete(String packgeId, String page, HttpServletRequest request) {
        try {
            //查询角色信息
            PackgeInfo packgeInfo = packgeServ.queryById(packgeId);
            ToolBox.isNull(packgeInfo);
            //操作值
            String operatorValue = "packgeId：" + packgeInfo.getPackgeId() + "；类别名：" + packgeInfo.getPackgeName() + "；时间：" + packgeInfo.getPackgeCritme();
            //操作动作
            String operatorAction = "删除专区类别";
            //构建日志
            OperatorLog log = LogGPUtil.buildLog(request, operatorValue, operatorAction);
            //删除
            packgeServ.remove(packgeId, log);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/app/packgeInfo_list.do", request);
        }
        return successReturn(PromptUtil.DELETE_SUCCESS, "/system/app/packgeInfo_list.do", request);
    }


}
