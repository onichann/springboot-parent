package com.wt.springboot.data.jpa.Specification;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
/**
 * @Author dlei(徐磊)
 * @Email dlei0009@163.com
 * 定义一个对象用于封装一页数据 
 */
public class PageData {
	// 定义一个变量用于存放当前页码 
	private int pageIndex;
	// 定义一个变量用于保存满足查询条件下用于分页的数据总量
	private long totalCount  ;
	// 定义一个变量用于保存当前条件下总共可以分的总页数
	private int pageSize ;
	// 定义一个变量用于保存当前页码查询出的数据总量
	private int pageNum;
	// 定义一个变量用于保存当前查询出来的学生信息 
	private List<Map<String,Object>> stuDatas = new ArrayList<>();
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public List<Map<String, Object>> getStuDatas() {
		return stuDatas;
	}
	public void setStuDatas(List<Map<String, Object>> stuDatas) {
		this.stuDatas = stuDatas;
	}
}
