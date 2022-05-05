package com.wt.nettyrpcserver.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wt.nettyrpcapi.common.RPCRequest;
import com.wt.nettyrpcapi.common.RpcResponse;
import com.wt.nettyrpcserver.anno.RpcService;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author wutong
 * @date 2022/4/22 16:15
 * 说明:
 * 1.将标有@RpcService的注解的bean进行缓存
 * 2.接收客户端请求
 * 3.根据传递过来的beanName从缓存中查找
 * 4.通过反射调用bean的方法
 * 5.给客户端响应
 */
@Component
@ChannelHandler.Sharable//设置通道共享
public class NettyServerHandler extends SimpleChannelInboundHandler<String> implements ApplicationContextAware {

    @Autowired
    private ObjectMapper objectMapper;

    static Map<String, Object> SERVICE_INSTANCE_MAP = new ConcurrentHashMap<>();

    /**
     * 通道读取客户端消息
     * @param ctx
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        //2.接收客户端请求
        RPCRequest request = objectMapper.readValue(msg, RPCRequest.class);
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());

        //业务处理
        try {
            response.setResult(handler(request));
        } catch (Exception e) {
            e.printStackTrace();
            response.setError(e.getMessage());
        }

        //5.给客户端响应
        ctx.writeAndFlush(objectMapper.writeValueAsString(response));
    }

    private Object handler(RPCRequest request) throws InvocationTargetException {
        //3.根据传递过来的beanName从缓存中查找
        //4.通过反射调用bean的方法
        Object serviceBean = SERVICE_INSTANCE_MAP.get(request.getClassName());
        if (serviceBean == null) {
            throw new RuntimeException("服务端没有找到服务");
        }
        FastClass proxyClass = FastClass.create(serviceBean.getClass());
        FastMethod method = proxyClass.getMethod(request.getMethodName(), request.getParameterTypes());
        return method.invoke(serviceBean, request.getParameters());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        //1.1通过注解获取bean集合
        Map<String, Object> serviceMap = applicationContext.getBeansWithAnnotation(RpcService.class);
        for (Map.Entry<String, Object> entry : serviceMap.entrySet()) {
            Object serviceBean = entry.getValue();
            if (serviceBean.getClass().getInterfaces().length == 0) {
                throw new RuntimeException("对外暴露的服务必须实现接口");
            }
            //默认处理第一个作为缓存bean的名字
            String serviceName = serviceBean.getClass().getInterfaces()[0].getName();
            SERVICE_INSTANCE_MAP.put(serviceName, serviceBean);
        }
    }
}
