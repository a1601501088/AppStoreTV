package com.basicframe.utils.page;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;
/**
 * <p>Description: Z分页标签</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class ZPageTag extends SimpleTagSupport {
	  
    private Pagination pagination;   
  
    private String baseurl;   
  
    public void setPagination(Pagination pagination) {
        this.pagination = pagination;   
    }   
  
    public void setBaseurl(String baseurl) {   
        this.baseurl = baseurl;   
    }   
  
  
    @Override  
    public void doTag() throws JspException, IOException {
    	
        StringBuilder sb = new StringBuilder();   
  
        if (pagination == null || pagination.getList() == null || pagination.getList().size() == 0) {   
            getJspContext().getOut().print("");   
            return;   
        }   
        
        sb.append("<div style='float:right;font-family:Tahoma'>");
        if (pagination.getPage() == 1) {
            sb.append(createPrePage(0, true));   
        } else {   
            sb.append(createPrePage(pagination.getPage() - 1, false));   
        }   
        if (pagination.getPage() == pagination.getTotalPage()) {
            sb.append(createNextPage(0, true));   
        } else {   
            sb.append(createNextPage(pagination.getPage() + 1, false));   
        }   
        sb.append(createPoint());
        sb.append("</div>");
        sb.append(createPageInfo(pagination));   
        getJspContext().getOut().print(sb.toString());   
  
    }   
  
  
    private String createPrePage(int pageIndex, boolean distable) {   
        StringBuilder sb = new StringBuilder();   
        if (distable) {   
            sb.append("第一页&nbsp;|&nbsp;上一页&nbsp;"); 
        } else {   
            sb.append("<a href='" + baseurl + getUrlStartChar()+"page=1'>第一页</a>&nbsp;|&nbsp;<a href='" + baseurl + getUrlStartChar()+"page=" + pageIndex + "'>上一页</a>&nbsp;");   
        }   
        return sb.toString();
    }   
  
    private String createNextPage(int pageIndex, boolean distable) {   
        StringBuilder sb = new StringBuilder();   
        if (distable) {   
            sb.append("|&nbsp;下一页&nbsp;|&nbsp;最末页&nbsp;&nbsp;&nbsp;&nbsp;"); 
        } else {   
            sb.append("|&nbsp;<a href='" + baseurl + getUrlStartChar()+"page=" + pageIndex + "'>下一页</a>&nbsp;|&nbsp;<a href='" + baseurl + getUrlStartChar()+"page=" + pagination.getTotalPage() + "'>最末页</a>&nbsp;&nbsp;&nbsp;&nbsp;");   
        }   
        return sb.toString();   
    }   
  
  
    private String createPoint() {   
        return "到第&nbsp;<input name='page' type='text' class='inputText' style='width:40px' onKeyUp=\"value=value.replace(/\\D/g,'')\">&nbsp;页&nbsp;&nbsp;<input type='button' onClick='form.submit();' class='inputButton' value='跳转'>";   
    }   
  
    private String createPageInfo(Pagination pagination) {
    	return "<div style='float:left;font-family:Tahoma'>共 "+ pagination.getTotalCount() +" 条记录，每页 "+ pagination.getPageSize() +" 条，当前第 "+ pagination.getPage() +" / "+ pagination.getTotalPage() +" 页</div>";
    }   
       
    /**  
     * 返回URL参数首字符  
     * @return  
     */  
    private String getUrlStartChar(){   
        if(baseurl.indexOf("?")>=0){   
            return "&";   
        }else{   
            return "?";   
        }   
    }   
  
}  
