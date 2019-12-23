package com.wt.springboot.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * @author Administrator
 * @date 2019-03-28 下午 2:54
 * PROJECT_NAME springBoot
 */
@Service
public class JwtSevice {

    @Value("wut")
    private String secret;

    @RequestMapping("/token")
    public String generateToken(String sub){
        return Jwts.builder().setSubject(sub).setIssuedAt(new Date()).signWith(SignatureAlgorithm.HS512, secret).compact();
    }

    public static void main(String[] args) {
        LocalDateTime localDateTime1 = LocalDateTime.now().plusSeconds(600L);
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime1.atZone(zone).toInstant();
        String compact = Jwts.builder().setSubject("wut1").setIssuedAt(new Date()).setExpiration(Date.from(instant)).signWith(SignatureAlgorithm.HS512, "wut1").compact();
        System.out.println(compact);
        Claims wut1 = Jwts.parser().setSigningKey("wut1").parseClaimsJws(compact).getBody();
        System.out.println(wut1);
        System.out.println(wut1.getExpiration());
        LocalDateTime now = LocalDateTime.now();
        System.out.println(now.atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        LocalDateTime localDateTime = now.plusSeconds(600L);
        System.out.println(localDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}
