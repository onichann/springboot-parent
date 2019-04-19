package com.wt.authorization;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

/**
 * @author Administrator
 * @date 2019-04-19 下午 7:30
 * PROJECT_NAME springboot-parent
 */
@Configuration
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter {

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new Pbkdf2PasswordEncoder("wut");
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {

        //内存中设置用户
        auth.inMemoryAuthentication()
                .passwordEncoder(passwordEncoder())
                .withUser("admin").password(passwordEncoder().encode("admin")).roles("ADMIN");

    }


}
