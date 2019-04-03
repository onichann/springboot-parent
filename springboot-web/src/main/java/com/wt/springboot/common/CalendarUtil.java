package com.wt.springboot.common;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 
 * @author sally
 * @date 2017年7月26日
 */
public class CalendarUtil {
	//节假日列表  
    private  List<Calendar> holidayList= new ArrayList<Calendar>();    
    //周末为工作日  
    private  List<Calendar> weekendList= new ArrayList<Calendar>(); 
    
    private static DateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
    
    public CalendarUtil(){
    	
    }
    public CalendarUtil(List<Calendar> holidayList, List<Calendar> weekendList){
    	this.holidayList=holidayList;
    	this.weekendList=weekendList;
    }
    
    /**
	  * 
	  * 验证日期是否是节假日
	  * @param calendar  传入需要验证的日期
	  * @return 
	  * return boolean    返回类型  返回true是节假日，返回false不是节假日
	  * 
	  */
	 public boolean checkHoliday(Calendar calendar){
		 //判断日期是否是周六周日
		 if(calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY || 
				 calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
			//判断日期是否是节假日
			 for (Calendar ca : weekendList) {
				if(ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
						ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)&&
						ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
					return false;
				}
			}
			 return true;
		 }
		 //判断日期是否是节假日
		 for (Calendar ca : holidayList) {
			if(ca.get(Calendar.MONTH) == calendar.get(Calendar.MONTH) &&
					ca.get(Calendar.DAY_OF_MONTH) == calendar.get(Calendar.DAY_OF_MONTH)&&
					ca.get(Calendar.YEAR) == calendar.get(Calendar.YEAR)){
				return true;
			}
		}
		 return false;
	 }
	 
	 /**
	  * 
	  * 把所有节假日放入list
	  * @param date  从数据库查 查出来的格式2016-05-09
	  * return void    返回类型 
	  * throws 
	  */
	private void initHolidayList(String date){
			String [] da = date.split("-");
			Calendar calendar = Calendar.getInstance();
			calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
			calendar.set(Calendar.MONTH, Integer.valueOf(da[1])-1);//月份比正常小1,0代表一月
			calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
			holidayList.add(calendar);
	}
	
	/**
	 * 初始化周末被调整为工作日的数据
	 */
	private void initWeekendList(String date){
		String [] da = date.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
		calendar.set(Calendar.MONTH, Integer.valueOf(da[1])-1);//月份比正常小1,0代表一月
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
		weekendList.add(calendar);
	}
	
	/**
	 * 
	 * @param date 格式yyyy-MM-dd
	 * @return
	 */
	public static Calendar string2Calendar(String date){
		String [] da = date.split("-");
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.YEAR, Integer.valueOf(da[0]));
		calendar.set(Calendar.MONTH, Integer.valueOf(da[1])-1);//月份比正常小1,0代表一月
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(da[2]));
		return calendar;
	}
	
	/**
	 * 
	 * @param cal
	 * @param dfs
	 * @return
	 */
	public static String Calendar2String(Calendar cal,DateFormat dfs){
		if(dfs==null) dfs=df;
		return dfs.format(cal.getTime());
	}
	
	/**
	 * 获取当前日期的下一天
	 * @param date
	 * @return
	 */
	public static String nextDate(String date){
		Calendar cal=string2Calendar(date);
		cal.add(Calendar.DATE, 1);
		return Calendar2String(cal,null);
	}
	/**
	 * 获取当前日期的上一天
	 * @param date
	 * @return
	 */
	public static String prevDate(String date){
		Calendar cal=string2Calendar(date);
		cal.add(Calendar.DATE, -1);
		return Calendar2String(cal,null);
	}
	
	/**
	 * 服务器当前日期
	 * @return
	 */
	public static String nowDate(){
		return df.format(Calendar.getInstance().getTime());
	}
	
	
	public static void main(String[] args) throws Exception {
//		System.out.println(df.format(Calendar.getInstance().getTime()));
		System.out.println(prevDate("2017-07-01"));
//		System.out.println(new CalendarUtil().checkHoliday(string2Calendar(nextDate("2017-07-01"))));

	}
	public List<Calendar> getHolidayList() {
		return holidayList;
	}
	public void setHolidayList(List<Calendar> holidayList) {
		this.holidayList = holidayList;
	}
	public List<Calendar> getWeekendList() {
		return weekendList;
	}
	public void setWeekendList(List<Calendar> weekendList) {
		this.weekendList = weekendList;
	}
}
