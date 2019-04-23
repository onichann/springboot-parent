package com.wt.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;

/**
 * @author Administrator
 * @date 2019-04-17 下午 6:30
 * PROJECT_NAME springboot-parent
 */
@Configuration
@EnableAuthorizationServer
public class AuthorizationServerConfiguration  implements AuthorizationServerConfigurer {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RedisConnectionFactory redisConnectionFactory;

//    用来配置令牌端点(Token Endpoint)的安全约束.
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServer) throws Exception {
//            允许表单验证
            authorizationServer
                    .tokenKeyAccess("permitAll()")
                    .checkTokenAccess("isAuthenticated()")//allow check token
                    .allowFormAuthenticationForClients();
    }

//    用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
        clients.inMemory().withClient("client_1")
                .resourceIds("order")
                .authorizedGrantTypes("client_credentials", "refresh_token")
                .scopes("select")
                .authorities("client")
                .secret("60baedd8f2a4e69b5b78ffbd94948385bddc96a0ac0dc500908911cae709d8e7460a99c78ba5997e")  //123456
                .and()
                .withClient("client_2")
                .resourceIds("order")
                .authorizedGrantTypes("password", "refresh_token")
                .scopes("select")
                .authorities("user")
                .secret("60baedd8f2a4e69b5b78ffbd94948385bddc96a0ac0dc500908911cae709d8e7460a99c78ba5997e");

    }

//    用来配置授权（authorization）以及令牌（token）的访问端点和令牌服务(token services)。
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
    }
}
