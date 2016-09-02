package com.basicframe.utils.page;

import java.io.Serializable;
import java.util.List;

/**
 * <p>Description: 分页对象</p>
 *
 * <p>Copyright: Copyright (c) 2011</p>
 *
 * <p>Company: xmp</p>
 *
 * @author 唐颖杰
 * @version 1.0
 */
public class Pagination implements Serializable {

	private static final long serialVersionUID = -4263336128482001626L;

	/**
	 * 每页的记录数
	 */
	public int pageSize;
	/**
	 * 总记录数
	 */
	private int totalCount;
	/**
	 * 当前页, 从1开始计数
	 */
	private int page;
	/**
	 * 页数据
	 */
	private List<?> list;
	/**
	 * 起始行, 从1开始计数 
	 */
	private int startRow;
	/**
	 * 结束行, 从1开始计数 
	 */
	private int endRow;
	/**
	 * 总页数
	 */
	private int totalPage;

	/**
	 * 无参构造方法
	 */
	public Pagination() {

	}
	
	/**
	 * 构造方法
	 * @param page 当前页
	 * @param totalCount 总条数
	 * @param pageSize 每页显示多少条
	 * @param list 查询结果集
	 */
	public Pagination(int page, int totalCount, int pageSize, List<?> list) {
		this.page = page <= 0 ? 1 : page;
		this.totalCount = totalCount;
		this.pageSize = pageSize;
		this.list = list;
		if (totalCount < pageSize) {
			this.totalPage = 1;
		} else {
			if (totalCount % pageSize == 0) {
				this.totalPage = totalCount / pageSize;
			} else {
				this.totalPage = totalCount / pageSize + 1;
			}
		}
		if (page > totalPage) {
			this.page = this.totalPage;
			this.startRow = pageSize * (this.page - 1) + 1;
			this.endRow = this.startRow + this.pageSize - 1;
			if(endRow > totalCount){
				this.endRow = this.totalCount;
			}
		}
	}

	/**
	 * @return the totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * @param totalCount the totalCount to set
	 */
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 * @return the startRow
	 */
	public int getStartRow() {
		return startRow;
	}

	/**
	 * @param startRow the startRow to set
	 */
	public void setStartRow(int startRow) {
		this.startRow = startRow;
	}

	/**
	 * @return the endRow
	 */
	public int getEndRow() {
		return endRow;
	}

	/**
	 * @param endRow the endRow to set
	 */
	public void setEndRow(int endRow) {
		this.endRow = endRow;
	}

	/**
	 * @return the totalPage
	 */
	public int getTotalPage() {
		return totalPage;
	}

	/**
	 * @param totalPage the totalPage to set
	 */
	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	/**
	 * @return the page
	 */
	public int getPage() {
		return page;
	}

	/**
	 * @param page the page to set
	 */
	public void setPage(int page) {
		this.page = page;
	}

	/**
	 * @return the pageSize
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param pageSize the pageSize to set
	 */
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	/**
	 * @return the list
	 */
	public List<?> getList() {
		return list;
	}

	/**
	 * @param list the list to set
	 */
	public void setList(List<?> list) {
		this.list = list;
	}

}
