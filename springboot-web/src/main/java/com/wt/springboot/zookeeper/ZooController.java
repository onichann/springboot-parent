package com.wt.springboot.zookeeper;

import org.apache.commons.codec.Charsets;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.ChildData;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.recipes.locks.InterProcessMutex;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.data.Stat;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

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

    @RequestMapping("/test")
    @ResponseBody
    public void test() throws Exception {
        String path = client.create().creatingParentContainersIfNeeded()
                .withMode(CreateMode.EPHEMERAL)
                .forPath("/path", "init".getBytes());
        byte[] paths = client.getData().forPath("/path");
        Stat stat = new Stat();
        byte[] paths1 = client.getData().storingStatIn(stat).forPath("/path");
//        client.setData().withVersion(stat.getVersion()).forPath("/path", "data".getBytes());
        Stat path2 = client.checkExists().forPath("/path");
        List<String> strings = client.getChildren().forPath("/path");

        //guaranteed()接口是一个保障措施，只要客户端会话有效，那么Curator会在后台持续进行删除操作，直到删除节点成功。
        client.delete().guaranteed().deletingChildrenIfNeeded().forPath("/path");
        
        //事务  过时
        Collection<CuratorTransactionResult> commit = client.inTransaction()
                .create().withMode(CreateMode.EPHEMERAL).forPath("/path", "data".getBytes())
                .and()
                .setData().forPath("/path", "data2".getBytes())
                .and()
                .commit();
        commit.forEach(v-> System.out.println(v.getForPath()+":-------re------"+v.getType()));

        client.create().inBackground((curatorFramework, curatorEvent) -> {
            System.out.println(String.format("eventType:%s,resultCode:%s", curatorEvent.getType(), curatorEvent.getResultCode()));
        }).forPath("/path");

        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework curatorFramework, ConnectionState state) {
                if(state == ConnectionState.CONNECTED){
                    System.out.println("zk connected..");
                }else if(state == ConnectionState.LOST){
                    System.out.println("zk session lost..");
                }else if(state == ConnectionState.RECONNECTED){
                    System.out.println("zk reconnected..");
                }
            }
        });

        //监听
    }

    @RequestMapping("/lock")
    @ResponseBody
    public void lock() throws InterruptedException {
        InterProcessMutex lock = new InterProcessMutex(client, "/test");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        AtomicInteger j = new AtomicInteger(10);
        AtomicInteger k = new AtomicInteger(1);
        for (int i = 0; i < 10; i++) {
            new Thread(()->{
                try {
                    countDownLatch.await();
                    System.out.println(k.get()+"等待获取锁");
                    lock.acquire();
                    System.out.println(k.get()+"获取到锁");
                    System.out.println(j.decrementAndGet());
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    try {
                        lock.release();
                        System.out.println(k.getAndIncrement()+"释放了锁");
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            },"t"+i).start();
        }
        TimeUnit.SECONDS.sleep(2);
        countDownLatch.countDown();
    }

    @RequestMapping("/pathCache")
    @ResponseBody
    public void pathCache() throws Exception {
        PathChildrenCache cache = new PathChildrenCache(client, "/pathCache", true);
        cache.start(PathChildrenCache.StartMode.POST_INITIALIZED_EVENT);
        cache.getListenable().addListener(new PathChildrenCacheListener() {
            @Override
            public void childEvent(CuratorFramework client, PathChildrenCacheEvent event) throws Exception {
                PathChildrenCacheEvent.Type type = event.getType();
                System.out.println("事件类型:"+type);
                if (null != event.getData()) {
                    System.out.println("节点数据:"+event.getData().getPath()+"="+new String(event.getData().getData(), Charsets.UTF_8));
                }
            }
        });
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/pathCache/t","01".getBytes());
        Thread.sleep(10);
        client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath("/pathCache/t","02".getBytes());
        Thread.sleep(10);
        for (ChildData data : cache.getCurrentData()) {
            System.out.println("getCurrentData:" + data.getPath() + " = " + new String(data.getData()));
        }
        cache.close();
    }
}
