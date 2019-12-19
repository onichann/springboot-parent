package com.wt.springboot;


import org.jetbrains.annotations.NotNull;

/**
 * @author Administrator
 * @date 2019-07-23 下午 3:38
 * PROJECT_NAME webProject
 */
public class SomeTest {
    @NotNull String foo(int x, @NotNull String data){
       return x > 0 ? data : "";
   }
}
