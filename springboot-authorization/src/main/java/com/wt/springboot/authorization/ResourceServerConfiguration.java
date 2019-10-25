package com.wt.springboot.authorization;

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

/**
 * @EnableResourceServer 注解自动增加了一个类型为 OAuth2AuthenticationProcessingFilter 的过滤器链
 */
@Configuration
@EnableResourceServer
public class ResourceServerConfiguration implements ResourceServerConfigurer {


    /**
     * tokenServices：ResourceServerTokenServices 类的实例，用来实现令牌服务。
     resourceId：这个资源服务的ID，这个属性是可选的，但是推荐设置并在授权服务中进行验证。
     其他的拓展属性例如 tokenExtractor 令牌提取器用来提取请求中的令牌。
     请求匹配器，用来设置需要进行保护的资源路径，默认的情况下是受保护资源服务的全部路径。
     受保护资源的访问规则，默认的规则是简单的身份验证（plain authenticated）。
     其他的自定义权限保护规则通过 HttpSecurity 来进行配置。
     */
    @Override
    public void configure(ResourceServerSecurityConfigurer resource) throws Exception {
        resource.resourceId("order").stateless(true);


    }

    @Override
    public void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

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
