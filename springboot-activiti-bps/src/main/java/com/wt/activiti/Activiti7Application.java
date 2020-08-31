package com.wt.activiti;

import com.wt.activiti.configuration.EnableActivitiWorkflow;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableActivitiWorkflow
/**
 * @see org.activiti.engine.ProcessEngine //流程引擎的抽象，可以通过此类获取需要的所有服务。
 */
public class Activiti7Application {

    public static void main(String[] args) {
        SpringApplication.run(Activiti7Application.class, args);
    }

}
