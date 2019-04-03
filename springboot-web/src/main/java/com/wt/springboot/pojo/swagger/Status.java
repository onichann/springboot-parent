package com.wt.springboot.pojo.swagger;


import java.util.Objects;

public enum Status {
    available("available"),pending("pending"),sold("sold");
    private String name;
    Status(String name) {
        this.name=name;
    }

    public static Status  valueOfName(String name){
        for(Status obj:Status.values()){
            if(Objects.equals(obj.name,name)){
                return obj;
            }
        }
        return null;
    }
}
