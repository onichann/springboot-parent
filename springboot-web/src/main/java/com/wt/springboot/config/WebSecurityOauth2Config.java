package com.wt.springboot.config;//package com.wt.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.config.http.SessionCreationPolicy;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.oauth2.config.annotation.configurers.ClientDetailsServiceConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.AuthorizationServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.EnableResourceServer;
//import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfigurerAdapter;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerEndpointsConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.AuthorizationServerSecurityConfigurer;
//import org.springframework.security.oauth2.config.annotation.web.configurers.ResourceServerSecurityConfigurer;
//import org.springframework.security.oauth2.provider.token.store.InMemoryTokenStore;
//import org.springframework.security.oauth2.provider.token.store.redis.RedisTokenStore;
//
//@Configuration
//public class WebSecurityOauth2Config {
//
//    private static final String DEMO_RESOURCE_ID = "order";
//
////    @Bean
////    protected UserDetailsService userDetailsService(){
////        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
////        manager.createUser(User.withUsername("user_1").password("123456").authorities("USER").build());
////        manager.createUser(User.withUsername("user_2").password("123456").authorities("USER").build());
////        return manager;
////    }
//
//    @Configuration
//    @EnableResourceServer
//    protected static class ResourceServerConfiguration extends ResourceServerConfigurerAdapter {
//
//        @Override
//        public void configure(ResourceServerSecurityConfigurer resources) {
//            resources.resourceId(DEMO_RESOURCE_ID).stateless(true);
//        }
//
//        @Override
//        public void configure(HttpSecurity http) throws Exception {
//
//            http.authorizeRequests()
//                    .antMatchers("/css/**","/js/**","/images/**").permitAll()
//                    .antMatchers("/","/home","/redis/**").permitAll()
//                    .antMatchers("/actuator/**").access("hasIpAddress('127.0.0.1')")
//                    .antMatchers("/product/**").permitAll()
//                    .antMatchers("/order/**").authenticated()//配置order访问控制，必须认证过后才可以访问
//                    .anyRequest().authenticated()
//                    .and()
//                    .anonymous()
//                    .and()
//                    .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.IF_REQUIRED);
//        }
//    }
//
//
//    @Configuration
//    @EnableAuthorizationServer
//    protected static class AuthorizationServerConfiguration extends AuthorizationServerConfigurerAdapter {
//
//        @Autowired
//        AuthenticationManager authenticationManager;
//        @Autowired
//        RedisConnectionFactory redisConnectionFactory;
//
//        @Qualifier("userServiceImpl")
//        @Autowired
//        UserDetailsService userDetailsService;
//
//        @Override
//        public void configure(ClientDetailsServiceConfigurer clients) throws Exception {
//            //配置两个客户端,一个用于password认证一个用于client认证
//            clients.inMemory().withClient("client_1")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .authorizedGrantTypes("client_credentials")
//                    .scopes("select")
//                    .authorities("oauth2")
//                    .secret("123456")
//                    .and().withClient("client_2")
//                    .resourceIds(DEMO_RESOURCE_ID)
//                    .authorizedGrantTypes("password", "refresh_token")
//                    .scopes("select")
//                    .authorities("oauth2")
//                    .secret("123456");
//        }
//
//        @Override
//        public void configure(AuthorizationServerEndpointsConfigurer endpoints) throws Exception {
//            endpoints
//                    .tokenStore(new RedisTokenStore(redisConnectionFactory))
//                    .tokenStore(new InMemoryTokenStore())
//                    .authenticationManager(authenticationManager)
//                    .userDetailsService(userDetailsService)
//                    // 2018-4-3 增加配置，允许 GET、POST 请求获取 token，即访问端点：oauth/token
//                    .allowedTokenEndpointRequestMethods(HttpMethod.GET, HttpMethod.POST);
//
//            endpoints.reuseRefreshTokens(true);
//        }
//
//        @Override
//        public void configure(AuthorizationServerSecurityConfigurer oauthServer) throws Exception {
//            //允许表单认证
//            oauthServer.allowFormAuthenticationForClients();
//        }
//
//    }
//
//}
