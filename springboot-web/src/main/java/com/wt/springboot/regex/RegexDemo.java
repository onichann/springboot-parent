package com.wt.springboot.regex;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Administrator
 * @date 2019-03-01 上午 11:48
 * PROJECT_NAME springBoot
 */
public class RegexDemo {

    public static void main(String[] args) {
        String sql = "select * from dual where id = #{id} and age =  #{age}  ";
        Pattern compile = Pattern.compile("(?<key>\\b\\w+\\s*(?=\\=))\\s*\\=(?<value>(?<=\\=)\\s*\\S+)");
        Pattern compile2 = Pattern.compile("(?<replace>(?<=\\=)\\s*\\S+)");
        Matcher matcher = compile.matcher(sql);
        while (matcher.find()) {
            System.out.println(matcher.group());
            System.out.println(matcher.group("key"));
            System.out.println(matcher.group("value").replaceAll("\\s*",""));
        }
        Matcher matcher1 = compile2.matcher(sql);
        String change = matcher1.replaceAll(" ?");
        System.out.println(change);

        System.out.println("-----------------------");
        String str = ".304..313.";
        String pattern = "(?<=\\.)\\d+(?=\\.)";

        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        while(m.find()){
            System.out.println(m.group());
        }
    }
}
