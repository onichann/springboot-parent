package com.wt.springboot.dingding;

import org.springframework.web.bind.annotation.RestController;

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
public static void main(String[] args) {
    int[] array = new int[]{0,0,0,1,2,3,0,2,1,0,12,21,122,122,44,21,12};
    int oldLength = array.length;
    int[] arrayNew = new int[oldLength];
    int isRepeatCount = 0;
    int arrayNewIndex = -1;
    for (int i = 0;i<array.length;i++) {
        int arrayOld = array[i];
        Boolean isRepeat = false;
        if(arrayNewIndex>-1){
            for (int j = 0;j<=arrayNewIndex;j++) {
                if(arrayNew[j] == arrayOld){
                    isRepeat = true;
                    isRepeatCount++;
                    break;
                }
            }
        }
        if(!isRepeat){
            arrayNewIndex++;
            arrayNew[arrayNewIndex] = arrayOld;
        }
    }
    int[] arrayResult = new int[oldLength-isRepeatCount];

    for (int m = 0;m<arrayResult.length;m++) {
        arrayResult[m] = arrayNew[m];
    }

    for (int i : arrayResult) {
        System.out.println(i);
    }
}

}
