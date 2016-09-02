package com.basicframe.sys.model;

import java.io.Serializable;

import org.apache.ibatis.type.Alias;

@Alias("attachment")
public class Attachment implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5734237227307801300L;
	
	/**
	 * ID
	 */
	private int id;
	
	/**
	 * 名称
	 */
	private String name;
	
	/**
	 * 类型
	 */
	private String type;
	
	/**
	 * 大小
	 */
	private String size;
	
	/**
	 * 路径
	 */
	private String path;
	
	/**
	 * 上传时间
	 */
	private String time;
	
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getSize() {
		return size;
	}

	public void setSize(String size) {
		this.size = size;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
	
	
}
