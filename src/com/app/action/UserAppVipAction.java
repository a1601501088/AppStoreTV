package com.app.action;

import com.app.service.IUserAppVip;
import com.basicframe.common.action.BaseAction;
import com.basicframe.utils.page.Pagination;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;


@Controller
public class UserAppVipAction extends BaseAction {
    private Log log = LogFactory.getLog(UserAppVipAction.class);
    private Logger logger = Logger.getLogger("log");

    @Resource
    private IUserAppVip userAVipServ;
    /**
     * 列表
     * @author tyj
     * @param page 当前页
     * @param request
     * @return 返回到
     * @date Feb 24, 2015
     * @modify
     */
    @RequestMapping("/system/app/query_appvip")
    public String findlList(String page, HttpServletRequest request){
        try {
            String userId = request.getParameter("userId");
            String currentPage = request.getParameter("page");
            //参数
            Map<String, Object> map = new HashMap<String, Object>();
            if(userId != null && !"".equals(userId)){
                map.put("userId", "%"+userId+"%");
            }
            //构建分页对象
            Pagination pagination = findPageList(map, userAVipServ, currentPage, 15);
            //绑定到页面
            request.setAttribute("pagination", pagination);
            logger.info(pagination.toString());
            request.setAttribute("userId", userId);
        } catch (Exception e) {
            //业务异常、系统异常包装错误返回
            return errorReturn(log, e, "/system/main.jsp", request);
        }
        return directReturn("/system/app/userAppVip_list.jsp");
    }
}
