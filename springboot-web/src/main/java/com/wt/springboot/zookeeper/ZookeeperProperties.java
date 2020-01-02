package com.wt.springboot.zookeeper;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date 2020-01-02 上午 11:33
 * description
 */
@Configuration
@ConfigurationProperties(prefix = "curator")
@Data
public class ZookeeperProperties {
    private int retryCount;

    private int elapsedTimeMs;

    private String connectString;

    private int sessionTimeoutMs;

    private int connectionTimeoutMs;
}
