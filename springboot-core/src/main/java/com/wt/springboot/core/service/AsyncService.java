package com.wt.springboot.core.service;

import cn.hutool.http.HttpUtil;
import org.assertj.core.util.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.task.AsyncTaskExecutor;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.SettableListenableFuture;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Pat.Wu
 * @date 2022/1/27 11:56
 * @description
 */
@Service
public class AsyncService {

    @Autowired
    @Qualifier("getAsyncExecutor")
    private Executor executor;

    public ListenableFuture<String> asyncFromUrl() {
        SettableListenableFuture<String> future = new SettableListenableFuture<>();
        try {
            TimeUnit.SECONDS.sleep(1);
            Callable<String> task=()->{
                System.out.println(Thread.currentThread().getName()+"---");
                String result = HttpUtil.get("https://blog.csdn.net/phoenix/web/v1/reward/article-users?articleId=80372711");
                return result;
            };
            Future<String> outcome = ((AsyncTaskExecutor) executor).submit(task);
            future.set(outcome.get());
        } catch (Exception e) {
            future.setException(e);
        }
        return future;
    }

    public static void main(String[] args) {
         String variable_regex = "\\$\\{[\\[\\]\\?\\#\\.0-9\\_\\-a-zA-Z\\(\\)\\+\\-\\*\\/\\u0391-\\uFFE5]*\\}(\\@\\d+){0,1}";
        String sql = "select sys_created_by,sumRecord,CONNECT,duration,callmin,gtthree,organization,string_agg (team::TEXT, ',' )  AS \"team\"  from (\n" +
                "\n" +
                "select t1.sys_created_by , count(*) as sumRecord,sum(case when t1.out_call_connect=1 then 1 else 0 end ) as connect, sum(t1.duration) as duration, sum(t1.callmin) as callmin,\n" +
                "sum(case when t1.duration>300 then 1 else 0 end ) as gtthree ,t1.organization as organization,tr.team_id  AS \"team\" \n" +
                "from sm_out_call_detail t1  left JOIN team_result tr ON tr.user_id=t1.sys_created_by\n" +
                "where ${dataPermission} and  ${queryDate?dc#t1.start_time}  and t1.sys_status in (1,2) and t1.organization = ${organization} and ${team?dc#t1.sys_created_by}  \n" +
                "GROUP BY t1.sys_created_by,t1.organization,tr.team_id\n" +
                "\n" +
                ") t2 where 1=1 and ${sys_created_by?dc#t2.sys_created_by} and ${sumrecord?dc#t2.sumRecord}  and ${connect?dc#t2.connect}  and ${duration?dc#t2.duration} \n" +
                "and ${gtthree?dc#t2.gtthree} GROUP BY sys_created_by,sumRecord,CONNECT,duration,callmin,gtthree,organization";
        List<String> matcher = matcher(sql, variable_regex);
        System.out.println(matcher);

    }

    public static List<String> matcher(String content, String regex){
        List<String> retList = Lists.newArrayList();
        Pattern pattern = Pattern.compile(regex,Pattern.DOTALL);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()) {
            retList.add(matcher.group());
        }
        return retList;
    }
}
