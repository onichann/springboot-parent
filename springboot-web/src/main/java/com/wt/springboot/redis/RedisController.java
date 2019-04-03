package com.wt.springboot.redis;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisZSetCommands;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/redis")
public class RedisController
{
    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    @Qualifier("redisTemplate")
    private RedisTemplate redisTemplate;

    @Autowired
    @Qualifier("strKeyRedisTemplate")
    private RedisTemplate strKeyRedisTemplate;

    @RequestMapping(value = "/get")
    public @ResponseBody Object env(@RequestParam("para") String para){
        strKeyRedisTemplate.opsForValue().set("String:testenv2",para);
        return strKeyRedisTemplate.opsForValue().get("String:testenv2");
    }

    @RequestMapping("/lrange")
    public @ResponseBody String env2(){
        stringRedisTemplate.opsForList().leftPushAll("codeList:li","我","bbb");
        List<String> range = stringRedisTemplate.opsForList().range("codeList:li", 0, -1);
        return JSON.toJSONString(range,SerializerFeature.WriteMapNullValue);
    }

    /**
     * 绑定key值
     * @return
     */
    @RequestMapping("/lrange2")
    public @ResponseBody String env3(){
        BoundListOperations<String, String> stringStringBoundListOperations = stringRedisTemplate.boundListOps("codeList:li");
        List<String> range = stringStringBoundListOperations.range(0,-1);
        return JSON.toJSONString(range,SerializerFeature.WriteMapNullValue);
    }

    //低级别jdk
    @RequestMapping("/get2")
    @ResponseBody
    public String connectionGet(@RequestParam("key") String key){
        return stringRedisTemplate.execute((RedisCallback<String>) redisConnection ->{
            byte[] bytes =stringRedisTemplate.getStringSerializer().serialize(key);
            if(redisConnection.exists(bytes)){
                byte[] value=redisConnection.get(bytes);
                return stringRedisTemplate.getStringSerializer().deserialize(value);
            }
            return null;
        });
    }

    @RequestMapping("/send")
    @ResponseBody
    public String send(){
        stringRedisTemplate.convertAndSend("news.test","hello,world我");
        return "success";
    }

    @RequestMapping("/set")
    public @ResponseBody Map testSet(){
        //set不允许重复
        strKeyRedisTemplate.opsForSet().add("set:set1", "v1", "v1", "v2", "v3", "v4", "v5");
        strKeyRedisTemplate.opsForSet().add("set:set2", "v2", "v4", "v6", "v8");
        //绑定集合操作
        BoundSetOperations setOps = strKeyRedisTemplate.boundSetOps("set:set1");
        setOps.add("v6", "v7");
        setOps.remove("v1", "v7");
        //返回所有元素
        Set members = setOps.members();
        //成员个数
        Long size = setOps.size();
        //求交集
        Set intersect = setOps.intersect("set:set2");
        //求交集并且用新的集合inter保存
        setOps.intersectAndStore("set:set2","set:inter");
        //差集
        Set diff = setOps.diff("set:set2");
        setOps.diffAndStore("set:set2","set:diff");
        //并集
        Set union = setOps.union("set:set2");
        setOps.unionAndStore("set:set2","set:union");

        HashMap<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    @RequestMapping("/zset")
    public @ResponseBody Map testZSet(){
        //创建一个typedTuple对象存入值和风俗
        Set<ZSetOperations.TypedTuple<String>> typedTupleSet = IntStream.range(1, 10).mapToObj(i -> new DefaultTypedTuple<>("value" + i, i * 0.1)).collect(Collectors.toSet());
        strKeyRedisTemplate.opsForZSet().add("zset:zset1", typedTupleSet);
        BoundZSetOperations boundZSetOperations = strKeyRedisTemplate.boundZSetOps("zset:zvset1");
        boundZSetOperations.add("value10",0.26);
        //第一个下标到第六个下标，左右都包含 下标默认为0
        Set range = boundZSetOperations.range(1, 6);
        //按照分数排序获取有序集合
        Set set = boundZSetOperations.rangeByScore(0.2, 0.6);
        //****网上说分数要相同，否则不准？？？  是不准
        RedisZSetCommands.Range range1=new RedisZSetCommands.Range();
        range1.gt("value3");
        Set set1 = boundZSetOperations.rangeByLex(range1);
        //*****
        //删除
        boundZSetOperations.remove("value9","value2");
        //求分数
        Double value8 = boundZSetOperations.score("value8");
        //下标区间下，按照分数排序，返回值和score
        Set set2 = boundZSetOperations.rangeWithScores(1, 6);
        //分数区间下.....
        Set set4 = boundZSetOperations.rangeByScoreWithScores(1, 6);
        //从大到小排序  先排序后截取
        Set set3 = boundZSetOperations.reverseRange(2, 8);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    /**
     * redis事务
     * @return
     */
    @RequestMapping("/mulit")
    @ResponseBody
    public Map testmulit(){
        strKeyRedisTemplate.opsForValue().set("key1","value1");
        Object execute = strKeyRedisTemplate.execute(new SessionCallback() {
            @Override
            public List<Object> execute(RedisOperations redisOperations) throws DataAccessException {
                //设置要监控key1
                redisOperations.watch("key1");
                //开启事务，在exec命令执行前，全部都只是进入队列
                redisOperations.multi();
                redisOperations.opsForValue().set("key2", "value2");
//                redisOperations.opsForValue().increment("key1",1);
                //取值将为null，因为redis只是吧命令放入队列
                Object key2 = redisOperations.opsForValue().get("key2");
                System.out.println("key2值：" + key2);
                redisOperations.opsForValue().set("key3", "value3");
                Object key3 = redisOperations.opsForValue().get("key3");
                System.out.println("key3值：" + key3);
                //执行exec命令，将先判别key1是否在监控后被修改过，如果是则执行事务，否则就执行事务
                return redisOperations.exec();
            }
        });
        System.out.println(execute);
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

    /**
     * 流水线/管道技术  批量
     * @return
     */
    @RequestMapping("/pipeline")
    @ResponseBody
    public Map testPileline(){
        long l = System.currentTimeMillis();
        List list = strKeyRedisTemplate.executePipelined(new SessionCallback() {
            @Override
            public List execute(RedisOperations redisOperations) throws DataAccessException {
                IntStream.rangeClosed(1,10000).forEach(i -> {
                    redisOperations.opsForValue().set("pipeline:pipeline_"+i,"value_"+i);
                    if(i==10000){
                        String s = stringRedisTemplate.opsForValue().get("pipeline:pipeline_" + i);
                        System.out.println("命令进入队列，所以值为："+s);
                    }
                });
                return null;
            }
        });
        System.out.println(list);
        long l1 = System.currentTimeMillis();
        System.out.println("耗时："+(l1-l)+"毫秒");
        HashMap<String, Object> map = new HashMap<>();
        map.put("success",true);
        return map;
    }

}
