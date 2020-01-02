package com.wt.springboot.zookeeper;

import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author Administrator
 * @date 2020-01-02 下午 3:13
 * description
 */
@Controller
@RequestMapping("/zoo")
public class ZooController {

    @Autowired
    private CuratorFramework client;

    public void test() throws Exception {
        String path = client.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("path", "init".getBytes());
        byte[] paths = client.getData().forPath("path");
        Stat stat = new Stat();
        byte[] paths1 = client.getData().storingStatIn(stat).forPath("path");
        client.setData().withVersion(10086).forPath("path", "data".getBytes());
        Stat path2 = client.checkExists().forPath("path");
        client.getChildren().forPath("path");
        Void path1 = client.delete().guaranteed().deletingChildrenIfNeeded().forPath("path");
        
        //事务  过时
        client.inTransaction().check().forPath("path")
                .and()
                .create().withMode(CreateMode.EPHEMERAL).forPath("path","data".getBytes())
                .and()
                .setData().withVersion(10086).forPath("path","data2".getBytes())
                .and()
                .commit();
        client.create().inBackground((curatorFramework, curatorEvent) -> {
            System.out.println(String.format("eventType:%s,resultCode:%s", curatorEvent.getType(), curatorEvent.getResultCode()));
        }).forPath("path");
    }
}
