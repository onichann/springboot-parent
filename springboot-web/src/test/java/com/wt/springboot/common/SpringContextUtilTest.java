package com.wt.springboot.common;

import com.wt.springboot.SpringbootWebApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(classes= SpringbootWebApplication.class)
public class SpringContextUtilTest {

    @Test
    public void test(){
        for (int i = 0; i < 10; i++) {
            new Thread(()->System.out.println(SpringContextUtil.getCtx()==SpringContextUtil.getCtx())).start();
        }
    }
}