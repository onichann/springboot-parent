package com.wt.oldweb.common;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wut
 */

/**
 * 使用方法
 public PageDataGridDataModel page(List<Map<String,Object>> list,int page,int rows){
	 if(list==null) list=new ArrayList<Map<String,Object>>();
	 PageUtil pa =new PageUtil(list);
	 pa.setTotalCount(list.size());
	 Map<String,Object> data=pa.getPageRecordsMap(page, rows);
	 PageDataGridDataModel model = new PageDataGridDataModel();
	 model.setCurrentRecords((List<Map<String,Object>>)data.get("recordsList"));
	 model.setRecordCount((int)data.get("totalCount"));
	 model.setCurrentPageIndex((int)data.get("currentPage"));
	 model.setRecordCountPerPage(rows);
	 return model;
}*/
public class PageUtil {
	//总数
	private List<Map<String, Object>> recordsList=null;
	//当前页码
	private Integer pageNo=1;
	//总页数
	@SuppressWarnings("unused")
	private Integer totalPage=1;
	//总记录数
	private Integer totalCount=0;
	//每页大小
	private Integer pageSize=15;
	//是否有下一页
	private Boolean hasNext=false;
	//是否有上一页
	private Boolean hasPrev=false;
	
	
	/**
	 * 构造初始化列表和总数
	 * @param list1
	 */
	public PageUtil(List<Map<String, Object>> list1) {
		this.recordsList = list1;
		if(!isListNull(list1)){
			totalCount=list1.size();
		}
	}
	
	public void setTotalCount(Integer totalCount) {
		this.totalCount = totalCount;
	}

	/**
	 *　取得每页的数据
	 * @param pageNo 当前页码
	 * @param pageSize 取多少条记录
	 * @return {"currentPage":当前第几页,"pageSize":每页大小,"totalCount":总记录数,"totalPage":总页数,
	 * 			"recordsList":List<Message> list 每页数据列表,"hasNext":是否有下一页,"hasPrev":是否有上一页}
	 */
	public Map<String, Object> getPageRecordsMap(int pageNo,int pageSize){
		Map<String,Object> pageRecordsMap=new HashMap<String,Object>();
		pageRecordsMap.put("currentPage", pageNo);
		pageRecordsMap.put("pageSize", pageSize);
		pageRecordsMap.put("totalCount", getTotalCount());
		pageRecordsMap.put("totalPage", getTotalPage());
		
		int fromIndex=0;
		int toIndex=0;
		fromIndex=pageSize*(pageNo-1);
		toIndex=fromIndex+pageSize;
		if(toIndex>getTotalCount()){
			toIndex=getTotalCount();
		}
		if(fromIndex<0){
			fromIndex=0;
		}
		setPageNo(pageNo);
		pageRecordsMap.put("recordsList", recordsList.subList(fromIndex, toIndex));
		pageRecordsMap.put("hasNext", getHasNext());
		pageRecordsMap.put("hasPrev", getHasPrev());
		return pageRecordsMap;
		
	}
	
	private boolean isListNull(List<Map<String, Object>> recordsList){
		boolean isNull=false;
		if(null==recordsList||recordsList.size()<=0){
			isNull=true;
		}
		return isNull;
	}
	
	public Integer getPageNo() {
		return pageNo;
	}
	public void setPageNo(Integer pageNo) {
		this.pageNo = pageNo;
	}
	public Integer getTotalPage() {
		return (getTotalCount()+pageSize-1)/pageSize;
	}
	public Integer getTotalCount() {
		return totalCount;
	}
	public Integer getPageSize() {
		return pageSize<=0?10:pageSize;
	}
	public Boolean getHasNext() {
		hasNext=pageNo<getTotalPage()?true:false;
		return hasNext;
	}
	public Boolean getHasPrev() {
		hasPrev=pageNo>1?true:false;
		return hasPrev;
	}
}
