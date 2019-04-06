package com.wt.springboot.redis;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

@Configuration
public class RedisConfig {

    @Bean
    public MessageListenerAdapter listenerAdapter(){
        return  new MessageListenerAdapter(new MyRedisChannelListener());
    }

    private @Autowired ThreadPoolTaskScheduler taskScheduler=null;
    //创建任务池，运行线程等待处理Redis消息
    @Bean
    public ThreadPoolTaskScheduler initTaskScheduler(){
        if(taskScheduler!=null){
            return  taskScheduler;
        }
        taskScheduler=new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(20);
        taskScheduler.setWaitForTasksToCompleteOnShutdown(true);
        taskScheduler.setAwaitTerminationSeconds(60);
        return taskScheduler;
    }

    //channel订阅
    /**
     * 监听容器
     * @param redisConnectionFactory
     * @param messageListenerAdapter
     * @return
     */
    @Bean
    public RedisMessageListenerContainer container(RedisConnectionFactory redisConnectionFactory,
                                                     MessageListenerAdapter messageListenerAdapter,
                                                   ThreadPoolTaskScheduler taskScheduler){
        RedisMessageListenerContainer container=new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.setTaskExecutor(taskScheduler);
        //使用监听器监听渠道 news.*
        container.addMessageListener(messageListenerAdapter,new PatternTopic("news.*"));
        return container;
    }

    @Bean
    public RedisTemplate<String,Object> strKeyRedisTemplate(RedisConnectionFactory redisConnectionFactory){
        RedisTemplate<String, Object> stringObjectRedisTemplate = new RedisTemplate<>();
        stringObjectRedisTemplate.setConnectionFactory(redisConnectionFactory);
//        RedisSerializer<String> stringSerializer = new StringRedisSerializer();
        RedisSerializer<String> stringSerializer = stringObjectRedisTemplate.getStringSerializer();
        stringObjectRedisTemplate.setKeySerializer(stringSerializer);
        stringObjectRedisTemplate.setHashKeySerializer(stringSerializer);
        return stringObjectRedisTemplate;
    }
}
