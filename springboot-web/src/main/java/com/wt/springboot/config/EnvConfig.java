package com.wt.springboot.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

@Configuration
public class EnvConfig {
    @Autowired private Environment env;

    public String getJAVA_HOME(){
        System.out.println("server.port:"+env.getProperty("server.port",String.class));
        return env.getProperty("JAVA_HOME",String.class);
    }
}
