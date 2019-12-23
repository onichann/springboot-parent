package com.wt.springboot.guava.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Objects;

/**
 * @author wutong
 * @date 2019/9/25 16:11
 * PROJECT_NAME sand-demo
 */
@Service
public class SealTokenService {

    @Autowired(required = false)
    public SealToken sealToken;

    @Autowired
    private RestTemplate restTemplate;

    /**
     * 获取token
     * see http://172.16.21.169:8090/pages/viewpage.action?pageId=9273346
     * @return
     */
    public String getToken(){
        HttpHeaders reqHeaders = new HttpHeaders();
        reqHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", sealToken.getGrantType());
        params.add("scope", sealToken.getScope());
        String authorization = sealToken.getClientId() + ":" + sealToken.getClientSecret();
        reqHeaders.add("Authorization",
                "Basic " + Base64Utils.encodeToString(authorization.getBytes(StandardCharsets.UTF_8)));
        HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, reqHeaders);
        HashMap responseEntity = restTemplate.postForObject(sealToken.getUrl(), entity, HashMap.class);
        return String.valueOf(Objects.requireNonNull(responseEntity).get("access_token"));
    }

}
