package com.wt.nettyrpcclient.proxy;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.wt.nettyrpcapi.common.RPCRequest;
import com.wt.nettyrpcapi.common.RpcResponse;
import com.wt.nettyrpcclient.client.NettyRpcClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author wutong
 * @date 2022/4/24 16:52
 * 说明:
 */
@Component
public class RpcClientProxy {

    @Autowired
    NettyRpcClient rpcClient;

    @Autowired
    ObjectMapper objectMapper;

    Map<Class, Object> serviceProxy = new HashMap<>();

    /**
     * 获取代理对象
     * @param serviceClass
     * @return
     */
    public Object getProxy(Class serviceClass) {
        //1.从缓存中查找
        Object proxy = serviceProxy.get(serviceClass);
        if (proxy == null) {
            //创建代理对象
            Object proxyInstance = Proxy.newProxyInstance(serviceClass.getClassLoader(), new Class[]{serviceClass}, new InvocationHandler() {
                @Override
                public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                    //解决idea调试问题
                    if (method.getDeclaringClass() == Object.class) {
                        String name = method.getName();
                        if ("toString".equals(name)) {
                            return Object.class.toString();
                        }
                    }
                    //1.封装请求对象
                    RPCRequest rpcRequest = new RPCRequest();
                    rpcRequest.setRequestId(UUID.randomUUID().toString());
                    rpcRequest.setClassName(method.getDeclaringClass().getName());
                    rpcRequest.setMethodName(method.getName());
                    rpcRequest.setParameterTypes(method.getParameterTypes());
                    rpcRequest.setParameters(args);

                    //发送消息
                    try {
                        Object msg = rpcClient.sendMessage(objectMapper.writeValueAsString(rpcRequest));
                        //3.将消息转换
                        RpcResponse rpcResponse = objectMapper.readValue(msg.toString(), RpcResponse.class);
                        if (rpcResponse.getError() != null) {
                            throw new RuntimeException(rpcResponse.getError());
                        }
                        if (rpcResponse.getResult() != null) {
                            return objectMapper.convertValue(rpcResponse.getResult(), method.getReturnType());
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        throw e;
                    }
                    return null;
                }
            });
            //放入缓存
            serviceProxy.put(serviceClass, proxyInstance);
            proxy = proxyInstance;
        }
        return proxy;
    }
}
