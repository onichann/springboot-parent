package com.wt.nettyrpcserver;

import com.wt.nettyrpcserver.server.NettyRpcServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NettyRpcServerApplication implements CommandLineRunner {

    @Autowired
    private NettyRpcServer rpcServer;

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(NettyRpcServerApplication.class);
        application.run( args);

    }


    @Override
    public void run(String... args) throws Exception {
        new Thread(()->{
            rpcServer.start("127.0.0.1", 8899);
        }).start();
    }
}
