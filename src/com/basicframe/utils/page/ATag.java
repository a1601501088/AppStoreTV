package com.basicframe.utils.page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.basicframe.sys.model.Permissions;
/**
 * A标签
 * @author: tyj
 * @version: V1.0
 * @date: Sep 2, 2010
 */
public class ATag extends BodyTagSupport {
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -4618502168533375552L;
	/**
     * 权限动作
     */
    private String action;
    /**
     * 文本
     */
    private String title;
    /**
     * 事件
     */
    private String onclick;
    
    
    public void setTitle(String title){
    	this.title = title;
    }
    
    public void setOnclick(String onclick){
    	this.onclick = onclick;
    }
    
    public void setAction(String action){
    	this.action = action;
    }
    
    @SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
        try {
        	String title = this.title == null ? "" : "title=\"你没有此权限\"";
        	String onclick = this.onclick == null ? "" : "onclick= \"\"";
        	String style = "style=\"color: #ccc;text-decoration: none;\"";
        	Map<String, List<?>> map = (Map<String, List<?>>)pageContext.getSession().getAttribute("super_user_Map");
        	List<Permissions> list = (List<Permissions>)map.get("super_user_permissions");
        	if(list != null && list.size() > 0){
        		for(int i = 0; i < list.size(); i++){
        			if(action != "" && action != null && list.get(i).getPerAction().indexOf(action) > -1){
        				style = "";
        				title = "title=\""+ this.title + "\"";
        				onclick = "onclick=\""+ this.onclick + "\"";
        			}
            	}
        	}
        	String str = ""+title+" "+style+" "+onclick+"";
			pageContext.getOut().print(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return EVAL_PAGE;
    }   

}