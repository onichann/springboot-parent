package com.wt.springboot.dingding;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author Pat.Wu
 * @date 2021/11/26 16:37
 * @description
 */
@RestController
public class DemoController {

//    public void demoTest() {
//        Consumer<String> consumer = s -> and;
//    }
//public static void main(String[] args) {
//    int[] array = new int[]{0,0,0,1,2,3,0,2,1,0,12,21,122,122,44,21,12};
//    int oldLength = array.length;
//    int[] arrayNew = new int[oldLength];
//    int isRepeatCount = 0;
//    int arrayNewIndex = -1;
//    for (int i = 0;i<array.length;i++) {
//        int arrayOld = array[i];
//        Boolean isRepeat = false;
//        if(arrayNewIndex>-1){
//            for (int j = 0;j<=arrayNewIndex;j++) {
//                if(arrayNew[j] == arrayOld){
//                    isRepeat = true;
//                    isRepeatCount++;
//                    break;
//                }
//            }
//        }
//        if(!isRepeat){
//            arrayNewIndex++;
//            arrayNew[arrayNewIndex] = arrayOld;
//        }
//    }
//    int[] arrayResult = new int[oldLength-isRepeatCount];
//
//    for (int m = 0;m<arrayResult.length;m++) {
//        arrayResult[m] = arrayNew[m];
//    }
//
//    for (int i : arrayResult) {
//        System.out.println(i);
//    }
//}

    public static void main(String[] args) throws Exception{
        //language=JSON
        String str = "{\"id\": \"id1\",\"a\": null,\"b\": \"\",\"c\": \"aaa\",\"d\":null}";
        String str2 = "{\"id\": \"id2\",\"a\": null,\"b\": \"\",\"c\": \"bbb\"}";

        ObjectMapper objectMapper = new ObjectMapper();
        Map<String, Object> map = (Map<String, Object>)objectMapper.readValue(str, Map.class);
        Map<String, Object> map2 = (Map<String, Object>)objectMapper.readValue(str2, Map.class);
        List<Map<String, Object>> list = new ArrayList<>();
        list.add(map);
        list.add(map2);
        System.out.println(list);
        Map<Object, Long> id = list.stream().collect(Collectors.groupingBy(m -> m.get("id"), Collectors.summingLong(m2 -> m2.entrySet().stream().filter(e -> "".equals(e.getValue()) || null == e.getValue()).count())));
        System.gc();
        System.out.println(id);
    }

}
