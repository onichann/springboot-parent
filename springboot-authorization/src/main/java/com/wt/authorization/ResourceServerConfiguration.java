package com.wt.authorization;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;

/**
 * @author Administrator
 * @date 2019-04-19 下午 6:54
 * PROJECT_NAME springboot-parent
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration implements ResourceServerConfigurer {



    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
        resource.resourceId("order").stateless(true);
    }

    @Override
    public void configure(HttpSecurity http) throws Exception {

        http.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED)
                .and()
                .requestMatchers().anyRequest()
                .and()
                .anonymous()
                .and()
                .authorizeRequests()
                .antMatchers("/order/**").authenticated();
        http.authorizeRequests()
                .antMatchers("/oauth/**").permitAll();
    }
}
