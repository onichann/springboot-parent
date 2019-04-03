package com.wt.springboot.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class WebAuthenticationSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {

    @Setter
    @Getter
    private boolean useReferer = false;

    @Override
    protected void handle(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        String targetUrl = this.determineTargetUrl(request, response ,authentication);
        if (response.isCommitted()) {
            this.logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
        } else {
            this.getRedirectStrategy().sendRedirect(request, response, targetUrl);
        }
    }

    protected String determineTargetUrl(HttpServletRequest request, HttpServletResponse response , Authentication authentication) {
        //获取当前登陆用户的角色权限集合 根据角色跳转不同的主页
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
//       List<GrantedAuthority> list = (List<GrantedAuthority>) CollectionUtils.collect(authorities.iterator(), o -> (GrantedAuthority)o);
        String roles=authorities.stream().flatMap(o -> Stream.of(o.getAuthority())).collect(Collectors.joining(","));
        if(roles.contains("ROLE_ADMIN")){
            return this.getDefaultTargetUrl();
        }else if(roles.contains("ROLE_USER")){
            return "/home";
        }

        if (this.isAlwaysUseDefaultTargetUrl()) {
            return this.getDefaultTargetUrl();
        } else {
            String targetUrl = null;
            if (this.getTargetUrlParameter() != null) {
                targetUrl = request.getParameter(this.getTargetUrlParameter());
                if (StringUtils.hasText(targetUrl)) {
                    this.logger.debug("Found targetUrlParameter in request: " + targetUrl);
                    return targetUrl;
                }
            }

            if (this.useReferer && !StringUtils.hasLength(targetUrl)) {
                targetUrl = request.getHeader("Referer");
                this.logger.debug("Using Referer header: " + targetUrl);
            }

            if (!StringUtils.hasText(targetUrl)) {
//                targetUrl = "/login?logout";
                targetUrl = this.getDefaultTargetUrl();
                this.logger.debug("Using default Url: " + targetUrl);
            }

            return targetUrl;
        }
    }



}
