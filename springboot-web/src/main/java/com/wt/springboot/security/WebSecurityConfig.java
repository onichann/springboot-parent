package com.wt.springboot.security;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.crypto.password.Pbkdf2PasswordEncoder;

@Configuration
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private WebAuthenticationSuccessHandler webAuthenticationSuccessHandler;

    @Autowired
    @Qualifier("userServiceImpl")
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;


//    @Value("#{T (java.io.File).separator}")
//    @Value("#{config['passwordEncoderSecret']}")
//    @Value("${passwordEncoderSecret}")
//    private String PESecret2;

    @Bean
    public PasswordEncoder passwordEncoder(@Value("${passwordEncoderSecret}") String PESecret){
        return new Pbkdf2PasswordEncoder(PESecret);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setHideUserNotFoundExceptions(false);
        provider.setUserDetailsService(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder);
        return provider;
    }

    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 默认开启csrf功能 会生成csrf参数 post form、上传文件会遇到验证 不通过会拦截掉  关闭：http.csrf().disable().authorizeRequests()...
     * <input type="hidden" id="${_csrf.parameterName}" name="${_csrf.parameterName}" value="${_csrf.token}"/>
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
//        super.configure(http)

        //控制页面是否可以在iframe里显示
        http.headers().frameOptions().disable();
        //某些请求不需要开启csrf,附件接口
        http.csrf().ignoringAntMatchers("/file/**","/api/v2.0/pet/**");

        http.authorizeRequests()
                .antMatchers("/js/**","/swagger3/**").permitAll()
                .antMatchers("/","/home","/redis/**","/oauth/**","/file/**","/api/v2.0/**","/index2/**","/jsplogin").permitAll()
                .antMatchers("/actuator/**").access("hasIpAddress('127.0.0.1')")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/login").usernameParameter("username").passwordParameter("password").successHandler(webAuthenticationSuccessHandler).permitAll()
                .and()
                .logout().permitAll()
                .and()
                //用户尝试访问仅限于他们没有的角色的页面时，应用程序将返回状态代码403，这意味着拒绝访问
                .exceptionHandling().accessDeniedPage("/403Page")
                .and()
                .httpBasic()
                .and()
                .sessionManagement().maximumSessions(1).expiredUrl("/login");


    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
//        super.configure(auth);
        //密码编码器
//        PasswordEncoder passwordEncoder=new Pbkdf2PasswordEncoder("qwe");
        //内存中设置用户
//        auth.inMemoryAuthentication()
//                .passwordEncoder(passwordEncoder)
//                .withUser("admin").password(passwordEncoder.encode("admin")).roles("ADMIN");
        //设置认证方式
        auth.authenticationProvider(authenticationProvider());

    }

    public static void main(String[] args) {
        String a=new Pbkdf2PasswordEncoder("wut").encode("123456");
        String b=new Pbkdf2PasswordEncoder("abc").encode("123");
        System.out.println(a+","+b);
    }

}
