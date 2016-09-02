package com.app.model;

import org.apache.ibatis.type.Alias;

@Alias("appClass")
public class AppClass {
	
	private int classId;
	
	private String className;
	
	private int classOrder;
	
	private String createTime;

	
	public int getClassId() {
		return classId;
	}

	public void setClassId(int classId) {
		this.classId = classId;
	}

	public String getClassName() {
		return className;
	}

	public void setClassName(String className) {
		this.className = className;
	}

	public int getClassOrder() {
		return classOrder;
	}

	public void setClassOrder(int classOrder) {
		this.classOrder = classOrder;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	
	
}
