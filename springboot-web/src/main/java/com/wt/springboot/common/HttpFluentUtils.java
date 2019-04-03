package com.wt.springboot.common;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.MapUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.springframework.http.MediaType;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpFluentUtils {

    public <T> T getForObject(String uri,Class<T> returnType,Map<String,String> uriParams) throws Exception {
        return get(uri,returnType,uriParams,null,null,null);
    }

    public <T> T getForObject(String uri,Class<T> returnType,Map<String,String> uriParams,String hostName, Integer port, String schemeName) throws Exception {
        return get(uri,returnType,uriParams,hostName,port,schemeName);
    }

    private <T> T get(String uri,Class<T> returnType,Map<String,String> uriParams,String hostName, Integer port, String schemeName) throws IOException {
        uri=buildGetParam(uri,uriParams);
        Request request = Request.Get(uri);
        request=buildProxy(request,hostName,port,schemeName);
        String content=request.execute().returnContent().asString(Consts.UTF_8);
        Gson gson=new Gson();
        if(returnType!=null){
//            return gson.fromJson(content, returnType);
            return JSON.parseObject(content,returnType);
        }
//        return gson.fromJson(content, (Type) String.class);
        return JSON.parseObject(content, (Type) String.class);
    }

    private String buildGetParam(String uri,Map<String,String> uriParams){
        Assert.notNull(uri,"URL不能为空");
        if(MapUtils.isNotEmpty(uriParams)){
            List<NameValuePair> nameValuePairs= new ArrayList<>(uriParams.size());
            for (String key : uriParams.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, uriParams.get(key)));
            }
            uri += "?" + URLEncodedUtils.format(nameValuePairs, Consts.UTF_8);
        }
        return uri;
    }

    private Request buildProxy(Request request, String hostName, Integer port, String schemeName) {
        if(!StringUtils.isEmpty(hostName) && port != null) {
            //设置代理
            if (StringUtils.isEmpty(schemeName)) {
                schemeName = HttpHost.DEFAULT_SCHEME_NAME;
            }
            request.viaProxy(new HttpHost(hostName, port, schemeName));
        }
        return request;
    }


    public <T> T postForObject(String uri,Class<T> returnType,Map<String,String> uriParams,String hostName, Integer port, String schemeName,List<File> files) throws Exception {
        List<NameValuePair> nameValuePairs=null;
        if(MapUtils.isNotEmpty(uriParams)){
            nameValuePairs= new ArrayList<>(uriParams.size());
            for (String key : uriParams.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, uriParams.get(key)));
            }
        }
        return post(uri,hostName,port,schemeName,nameValuePairs,returnType,files);
    }

    public <T> T postForObject(String uri,Class<T> returnType,Map<String,String> uriParams,List<File> files) throws Exception {
        List<NameValuePair> nameValuePairs=null;
        if(MapUtils.isNotEmpty(uriParams)){
            nameValuePairs= new ArrayList<>(uriParams.size());
            for (String key : uriParams.keySet()) {
                nameValuePairs.add(new BasicNameValuePair(key, uriParams.get(key)));
            }
        }
        return post(uri,null,null,null,nameValuePairs,returnType,files);
    }


    private <T> T post(String url, String hostName, Integer port, String schemeName, List<NameValuePair> nameValuePairs,Class<T> returnType, List<File> files) throws IOException {
        Assert.notNull(url,"URL不能为空");
        HttpEntity entity = buildPostParam(nameValuePairs, files);
        Request request = Request.Post(url).body(entity);
        request = buildProxy(request, hostName, port, schemeName);
        String content= request.execute().returnContent().asString(Consts.UTF_8);
        Gson gson=new Gson();
        if(returnType!=null){
            return gson.fromJson(content, returnType);
        }
        return gson.fromJson(content, (Type) String.class);
    }

    private HttpEntity buildPostParam(List<NameValuePair> nameValuePairs, List<File> files) {
        if(CollectionUtils.isEmpty(nameValuePairs) && CollectionUtils.isEmpty(files)) {
            return null;
        }
        if(CollectionUtils.isNotEmpty(files)) {
            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            for (File file : files) {
                builder.addBinaryBody(file.getName(), file, ContentType.APPLICATION_OCTET_STREAM, file.getName());
            }
            for (NameValuePair nameValuePair : nameValuePairs) {
                //设置ContentType为UTF-8,默认为text/plain; charset=ISO-8859-1,传递中文参数会乱码
                builder.addTextBody(nameValuePair.getName(), nameValuePair.getValue(), ContentType.create(MediaType.TEXT_PLAIN_VALUE, Consts.UTF_8));
            }
            return builder.build();
        } else {
            try {
                return new UrlEncodedFormEntity(nameValuePairs);
            } catch (UnsupportedEncodingException e) {

            }
        }
        return null;
    }
}
