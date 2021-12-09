**先确定递增情况（右边大于左边）**
**再确定递减情况 (左边大于右边)**
### 代码

```java
class Solution {
    public int candy(int[] ratings) {
        int[] res = new int[ratings.length];
        Arrays.fill(res,1);

        // 从左往右，如果右边大于左边
        // 1,2,2,5,4,3,2
        // 1 2 1 2 1 1 1
        for(int i = 0; i < ratings.length - 1; i++){
            if(ratings[i] < ratings[i + 1]){
                res[i + 1] = res[i] + 1;
            }
        }

        // 修正左边大于右边的糖果数
        // 从右往左遍历

        // 1,2,2,5,4,3,2
        // 1 2 1 2 1 1 1
        // 修正
        // 1 2 1 4 3 2 1
        for(int i = ratings.length - 2; i >= 0; i--){
            if(ratings[i] > ratings[i + 1]){
                // 要取右边大于左边时得到的糖果数res[i]
                // 和res[i+1] + 1 之间取最大值
                // 1 3 4 5 2
                // 1 2 3 4 1    //第一遍遍历
                // 1 2 3 4 1    // 第二遍遍历 （4的位置就不能修改为res[i+1] + 1 = 2
                res[i] = Math.max(res[i],res[i+1] + 1);
            }
        }

        int result = 0;
        for(int i = 0; i < res.length; i++){
            result += res[i];
        }

        return result;
    }
}
```