package com.wt.springboot.common;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * 
 * @author sally
 * @date 2017年7月30日
 */
public class YYHGenerater {
	private static YYHGenerater yyhGenerater=null;
	private static SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMdd");
	private YYHGenerater(){
		
	}
	
	public static YYHGenerater getInstance() {
        if (yyhGenerater == null) {
            synchronized (YYHGenerater.class) {
                if (yyhGenerater == null) {
                	yyhGenerater = new YYHGenerater();
                }
            }
        }
        return yyhGenerater;
    }
	
	public synchronized String createYYH(String ywlx,Boolean isys,Date yyrq){
		String orginalYYH=formatter.format(yyrq)+ywlx+serial_number()+(isys?"1":"0");
		return orginalYYH.substring(0,8)+formatSeq(getValidateSequence(orginalYYH),3)+orginalYYH.substring(8);
	}
	
	/**
	 * 7位随机数
	 * @return
	 */
	private static int  serial_number(){
		Random random = new Random();
		return random.nextInt(90000000) + 10000000;
	}
	
	/**
	 * 格式化成n位
	 * @param value
	 * @param zeroCount
	 * @return
	 */
	public static String formatSeq(Object value, int zeroCount) {
		String val = "00000" + String.valueOf(value);
		return val.substring(val.length() - zeroCount);
	}
	
	 /** 
     * 增加校验位 
     *  
     * @param number 
     *            号 
     * @return 带校验位的号 
     */  
    private static String getValidateSequence(String number) {  
  
        int validate = 0;  
        for (int i = 1; i <= number.length(); i++) {  
            char x = getCharFromString(number, i - 1);  
            int m = getIntFromChar(x);  
            if (m != 0) {  
                if (i < 9) {  
                    validate = validate + m * (i + 1);  
                } else {  
                    validate = validate + m * (i - 8);  
                }  
            }  
        }
        return String.valueOf(validate); 
        /*validate = validate % 11;  
        if (validate == 10) {  
            return "Y";  
        } else {  
            return String.valueOf(validate);  
        }  */
    }
    
    /**
     * 获取指定位置的字符
     * @param number
     * @param position
     * @return
     */
    private static char getCharFromString(String number, int position) {  
        return number.charAt(position);  
    }  
    
    /**
     * char to int
     * @param x
     * @return
     */
    private static int getIntFromChar(char x) {  
        return Integer.parseInt(String.valueOf(x));  
    }
    
	/*public static void main(String[] args) {
		for(int i=0;i<100;i++){
			String orginalYYH=formatter.format(Calendar.getInstance().getTime())+"111"+serial_number()+(true?"1":"0");
			String ss=getValidateSequence(orginalYYH);
			System.out.println("hao:"+ss);
		}
	}*/
}
