package com.wt.springboot.utils;

import com.google.gson.Gson;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class HttpFluentUtilsTest {

    private static HttpFluentUtils httpFluentUtils=new HttpFluentUtils();

    private String uri="http://127.0.0.1:8081/testHttpClient";

    @Test
    public void getForObject() {
        String url="/get";
        Map<String,String> params=new HashMap<>();
        params.put("id","idNo_test");
        try {
            String returnJson=httpFluentUtils.getForObject(uri+url,null,params);
//             ReturnJson returnJson=httpFluentUtils.getForObject(uri+url,ReturnJson.class,params);
            Gson gson=new Gson();
            System.out.println(gson.toJson(returnJson));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void postForObject() {

    }

    @Test
    public void get() throws Exception {
        Map<String,String> params=new HashMap<>();
        params.put("transaction_id","10495641025802112");
        params.put("openid","oco2qwz2moN8LrrlY_bHVoZKRpVQ");
        String url="http://www.lslr.gov.cn:8080/api/bind_openid.php";
        String returnJson=httpFluentUtils.getForObject(url,null,params);
        System.out.println(returnJson);
    }
}