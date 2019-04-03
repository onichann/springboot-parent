package com.wt.springboot.common.rtx;


public class SendNotify
 {
	//2007测试成功
    public static void main(String[] args) {

    	String receivers= "全体";
    	String title = receivers+"您好";
    	String msg = "消息提醒测试,[腾讯|http://www.qq.com]";
    	String type = "0";
    	String delayTime = "10000";
    	
    	int iRet= -1;
    	RTXSvrApi  RtxsvrapiObj = new RTXSvrApi();
    	RtxsvrapiObj.setServerIP("192.168.13.210");
    	if( RtxsvrapiObj.Init())
    	{   
    		iRet = RtxsvrapiObj.sendNotify(receivers,title,msg, type,delayTime);
    		if (iRet == 0)
    		{
    			System.out.println("发送成功");
    			
    		}
    		else 
    		{
    			System.out.println("发送失败");
    		}

    	}
    	RtxsvrapiObj.UnInit();
    	
    }
}
