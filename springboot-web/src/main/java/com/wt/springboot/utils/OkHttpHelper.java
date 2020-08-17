package com.wt.springboot.utils;

import okhttp3.*;
import okhttp3.Request.Builder;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author wutong
 * @date 2020/8/17 11:36
 * introduction
 */
public class OkHttpHelper {
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");
    public static final MediaType FORM = MediaType.parse("multipart/form-data; charset=utf-8");
    private static OkHttpClient client = new OkHttpClient();

    public OkHttpHelper() {
    }

    public static String get(String url, Map<String, String> params, Map<String, String> headers) {
        List<String> keyValue = new ArrayList();
        if (params != null) {
            params.entrySet().stream().forEach((entry) -> {
                keyValue.add((String)entry.getKey() + "=" + (String)entry.getValue());
            });
        }

        if (url.contains("?") && !keyValue.isEmpty()) {
            url = url + "&" + StringUtils.join(keyValue, "&");
        } else if (!keyValue.isEmpty()) {
            url = url + "?" + StringUtils.join(keyValue, "&");
        }

        Builder builder = new Builder();
        if (headers != null) {
            headers.entrySet().stream().forEach((entry) -> {
                builder.addHeader((String)entry.getKey(), (String)entry.getValue());
            });
        }

        Request request = builder.url(url).build();
        return execute(request);
    }

    public static String post(String url, String json, Map<String, String> headers) {
        RequestBody body = RequestBody.create(json, JSON);
        return executePost(url, body, headers);
    }

    public static String post(String url, Map<String, String> params, Map<String, String> headers) {
        okhttp3.FormBody.Builder builder = new okhttp3.FormBody.Builder();
        if (params != null) {
            params.entrySet().stream().forEach((entry) -> {
                builder.add((String)entry.getKey(), (String)entry.getValue());
            });
        }

        RequestBody form = builder.build();
        return executePost(url, form, headers);
    }

    public void upload(String url, File[] files, Map<String, String> params, Map<String, String> headers) {
        okhttp3.MultipartBody.Builder builder = (new okhttp3.MultipartBody.Builder()).setType(MultipartBody.FORM);
        params.entrySet().stream().forEach((entry) -> {
            builder.addFormDataPart((String)entry.getKey(), (String)entry.getValue());
        });

        for(int i = 0; i < files.length; ++i) {
            File file = files[i];
            builder.addFormDataPart("file", file.getName(), RequestBody.create(file, FORM));
        }

        RequestBody requestBody = builder.build();
        executePost(url, requestBody, headers);
    }

    private static String executePost(String url, RequestBody body, Map<String, String> headers) {
        Builder builder = new Builder();
        if (headers != null) {
            headers.entrySet().stream().forEach((entry) -> {
                builder.addHeader((String)entry.getKey(), (String)entry.getValue());
            });
        }

        Request request = builder.url(url).post(body).build();
        return execute(request);
    }

    private static String execute(Request request) {
        try {
            Response response = client.newCall(request).execute();
            if (response.isSuccessful()) {
                return response.body().string();
            } else {
                throw new RuntimeException(response.message());
            }
        } catch (Exception var2) {
            throw new RuntimeException(var2.getMessage());
        }
    }
}
