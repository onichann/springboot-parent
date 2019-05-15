package com.wt.authorization;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Administrator
 * @date 2019-04-19 下午 6:38
 * PROJECT_NAME springboot-parent
 */
@RestController
public class TestEndpoints {

    @GetMapping("/product/{id}")
    public String getProduct(@PathVariable String id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return "product id : "+id;
    }

    @GetMapping("/order/{id}")
    public String getOrder(@PathVariable String id) {
        Authentication authentication= SecurityContextHolder.getContext().getAuthentication();
        return "order id : "+id;
    }
}