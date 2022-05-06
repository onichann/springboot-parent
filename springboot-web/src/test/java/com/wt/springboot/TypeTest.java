package com.wt.springboot;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wutong
 * @date 2022/5/6 23:30
 * 说明:
 */
public class TypeTest {
    @SneakyThrows
    public static void main(String[] args) {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<Map<String, Object>> typeReference = new TypeReference<Map<String, Object>>() {
        };
        System.out.println(typeReference.getType());
//        System.out.println(((ParameterizedTypeImpl)typeReference.getType()).getActualTypeArguments());
//        Map<String, Object> map=new HashMap<>();
//        map.put("a", 1);
//        Map<String, Object> stringObjectMap = JSON.parseObject(JSON.toJSONString(map), typeReference);
//        System.out.println(stringObjectMap);
//        Object o = objectMapper.readValue(JSON.toJSONString(map), (Class) typeReference.getType());
//        System.out.println(o);
//        String name = ((Class) typeReference.getType()).getName();
//        Object value = getValue(Class.forName(name));
//        System.out.println(value);
    }
    public static <T> T  getValue(Class<T> clazz) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map=new HashMap<>();
        map.put("a", 1);
        return objectMapper.readValue(JSON.toJSONString(map), clazz);
    }
}
