package com.wt.springboot.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.retry.RetryNTimes;
import org.apache.curator.utils.CloseableUtils;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Administrator
 * @date 2020-01-02 上午 11:36
 * description
 */
@Configuration
public class ZookeeperConfiguration implements DisposableBean {

    @Autowired
    private ZookeeperProperties zookeeperProperties;

    @Bean(initMethod = "start")
    public CuratorFramework curatorFramework() {
        return CuratorFrameworkFactory.newClient(
                zookeeperProperties.getConnectString(),
                zookeeperProperties.getSessionTimeoutMs(),
                zookeeperProperties.getConnectionTimeoutMs(),
                new RetryNTimes(zookeeperProperties.getRetryCount(), zookeeperProperties.getElapsedTimeMs())
        ).usingNamespace("zoo");
    }

    @Override
    public void destroy() {
        CloseableUtils.closeQuietly(curatorFramework());
    }
}
