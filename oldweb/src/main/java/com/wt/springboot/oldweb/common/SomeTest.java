package com.wt.springboot.oldweb.common;


import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Administrator
 * @date 2019-07-23 下午 3:38
 * PROJECT_NAME webProject
 */
public class SomeTest {
    @NotNull String foo(int x, @Nullable String data){
       return x > 0 ? data : "";
   }
}
