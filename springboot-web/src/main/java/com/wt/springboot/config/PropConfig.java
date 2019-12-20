package com.wt.springboot.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@PropertySource("classpath:config.properties")
@ConfigurationProperties(prefix = "test", ignoreUnknownFields = false)
@EnableConfigurationProperties(PropConfig.class)
@Profile({"dev","test"})
public class PropConfig {
    private String url;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
