package com.wt.springboot.config;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.ResponseErrorHandler;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.time.Duration;

/**
 * @author Administrator
 * @date 2019-04-06 下午 11:10
 * PROJECT_NAME springboot-parent
 */
@Configuration
public class RestConfiguration {

    @Autowired
    private RestTemplateBuilder builder;

    //AsyncRestTemplate 过时 用 Spring 5中的WebClient
    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate= builder.setConnectTimeout(Duration.ofMillis(10000)).setReadTimeout(Duration.ofMillis(10000)).build();
        restTemplate.setErrorHandler(new ResponseErrorHandler() {
            @Override
            public boolean hasError(@NotNull ClientHttpResponse clientHttpResponse) throws IOException {
                return true;
            }

            @Override
            public void handleError(@NotNull ClientHttpResponse clientHttpResponse) throws IOException {

            }
        });
        return restTemplate;
    }
}
