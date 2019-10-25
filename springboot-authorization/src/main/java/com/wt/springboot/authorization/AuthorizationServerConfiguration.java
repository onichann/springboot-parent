package com.wt.springboot.authorization;

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

    /**
     * 使用授权服务的 /oauth/check_token 端点你需要将这个端点暴露出去，以便资源服务可以进行访问
     */
    @Override
    public void configure(AuthorizationServerSecurityConfigurer authorizationServer) throws Exception {
//            允许表单验证
            authorizationServer
                    .tokenKeyAccess("permitAll()")//公钥   url: /oauth/token_key
                    .checkTokenAccess("isAuthenticated()")//allow check token url: /oauth/check_token
                    .allowFormAuthenticationForClients();
    }

//    用来配置客户端详情服务（ClientDetailsService），客户端详情信息在这里进行初始化，你能够把客户端详情信息写死在这里或者是通过数据库来存储调取详情信息。

    /**
     *将ClientDetailsServiceConfigurer（从您的回调AuthorizationServerConfigurer）可以用来在内存或JDBC实现客户的细节服务来定义的。客户的重要属性是
     clientId:(必需）客户端ID。
     secret:(可信客户端需要）客户端密钥（如果有）。
     scope：客户受限的范围。如果范围未定义或为空（默认值），则客户端不受范围限制。
     authorizedGrantTypes：授权客户端使用的授权类型。默认值为空。
     authorities：授予客户端的权限（常规Spring Security权限）。
     客户端详细信息可以通过直接访问底层存储（例如，在情况下为数据库表JdbcClientDetailsService）或通过ClientDetailsManager接口（两个实现ClientDetailsService也都实现）在正在运行的应用程序中更新。
     */
    @Override
    public void configure(ClientDetailsServiceConfigurer clients) throws Exception {

        clients.inMemory()
                .withClient("client_1")
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

    /**
     *
     authenticationManager：密码授予通过注入来打开AuthenticationManager。
     userDetailsService：如果您注入一个UserDetailsService或者无论如何都是全局配置的（例如，在a中GlobalAuthenticationManagerConfigurer），那么刷新令牌授权将包含对用户详细信息的检查，以确保该帐户仍处于活动状态
     authorizationCodeServices：定义授权代码授权的授权代码服务（实例AuthorizationCodeServices）。
     implicitGrantService：在imlpicit授权期间管理状态。
     tokenGranter：（TokenGranter完全控制授予和忽略上面的其他属性）
     */
    /**
     *AuthorizationServerEndpointsConfigurer 这个配置对象(AuthorizationServerConfigurer 的一个回调配置项，见上的概述) 有一个叫做 pathMapping() 的方法用来配置端点URL链接，它有两个参数：
     第一个参数：String 类型的，这个端点URL的默认链接。
     第二个参数：String 类型的，你要进行替代的URL链接。
     以上的参数都将以 "/" 字符为开始的字符串，框架的默认URL链接如下列表，可以作为这个 pathMapping() 方法的第一个参数：
     /oauth/authorize：授权端点。
     /oauth/token：令牌端点。
     /oauth/confirm_access：用户确认授权提交端点。
     /oauth/error：授权服务错误信息端点。
     /oauth/check_token：用于资源服务访问的令牌解析端点。
     /oauth/token_key：提供公有密匙的端点，如果你使用JWT令牌的话。

     需要注意的是授权端点这个URL应该被Spring Security保护起来只供授权用户访问
     */
    @Override
    public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
        endpoints.tokenStore(new RedisTokenStore(redisConnectionFactory))
                .authenticationManager(authenticationManager)
                .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);

    }


}
