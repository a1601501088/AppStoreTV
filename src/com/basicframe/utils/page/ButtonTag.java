package com.basicframe.utils.page;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.basicframe.sys.model.Permissions;
/**
 * HTML按扭标签
 * @author: tyj
 * @version: V1.0
 * @date: Sep 2, 2010
 */
public class ButtonTag extends BodyTagSupport {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -8795443810320383337L;
	/**
     * ID
     */
    private String id;
	/**
	 * 名称
	 */
    private String name;
    /**
     * 类型
     */
    private String type;
    /**
     * 按扭文本名称
     */
    private String value;
    /**
     * 权限动作
     */
    private String action;
    /**
     * 文本
     */
    private String style;
    /**
     * 事件
     */
    private String onclick;
    
    
    public void setId(String id){
    	this.id = id;
    }
    
    public void setName(String name) {
        this.name = name;
    }
  
    public void setType(String type){
    	this.type = type;
    }
    
    public void setValue(String value){
    	this.value = value;
    }
    
    public void setOnclick(String onclick){
    	this.onclick = onclick;
    }
    
    public void setStyle(String style){
    	this.style = style;
    }

    public void setAction(String action){
    	this.action = action;
    }
    
    @SuppressWarnings("unchecked")
	public int doEndTag() throws JspException {
        try {
        	String id = this.id == null ? "" : " id=\""+ this.id + "\"";
        	String name = this.name == null ? "" : " name=\""+ this.name + "\"";
        	String onclick = this.onclick == null ? "" : " onclick= \""+ this.onclick + "\"";
        	String style = this.style == null ? "" : " style=\""+ this.style + "\"";
        	String disabled = " disabled=\"disabled\"";
        	Map<String, List<?>> map = (Map<String, List<?>>)pageContext.getSession().getAttribute("super_user_Map");
        	List<Permissions> list = (List<Permissions>)map.get("super_user_permissions");
        	if(list != null && list.size() > 0){
        		for(int i = 0; i < list.size(); i++){
        			if(action != "" && action != null && list.get(i).getPerAction().indexOf(action) > -1){
        				disabled = "";
        			}
            	}
        	}
        	String str = "<input"+ id +""+ name +" type=\""+ type +"\""+onclick+""+style+""+disabled+" class=\"button\" value=\""+ value +"\" />";
			pageContext.getOut().print(str.toString());
		} catch (IOException e) {
			e.printStackTrace();
		}
        return EVAL_PAGE;
    }   

}