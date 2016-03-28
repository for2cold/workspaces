package com.for2cold.rpc.invoke;

import com.for2cold.rpc.exception.RpcException;
import com.for2cold.rpc.zookeeper.ZookeeperClient;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by jasme on 16/3/16.
 */
public class ConsumerConfig {

    private String url;

    private ZookeeperClient client;

    private final ConcurrentHashMap<Class<?>, AtomicInteger> invokeCount = new ConcurrentHashMap<>();

    public String getUrl(Class<?> clazz) throws RpcException {
        if (null != client) {
            List<String> urlList = new ArrayList<>();
            List<String> pathList = client.getChildren("/rpc/"+clazz.getName().replaceAll("\\.","/"));

            for (String path : pathList) {
                String httpUrl = client.getData("/rpc/" + clazz.getName().replaceAll("\\.","/")+"/"+path);
                if (null != httpUrl) {
                    urlList.add(httpUrl);
                }
            }
            return getCurrentUrl(clazz, urlList);
        }
        return url;
    }

    public String getCurrentUrl(Class<?> clazz, List<String> urlList) {
        if (invokeCount.get(clazz) == null) {
            invokeCount.putIfAbsent(clazz, new AtomicInteger(1));
            return urlList.get(0);
        }
        int i = invokeCount.get(clazz).incrementAndGet();
        return urlList.get(i % urlList.size());
    }

    public void setUrl(String url) {
        this.url = url;
        if (url.toLowerCase().startsWith("zookeeper://")) {
            client = new ZookeeperClient(url.toLowerCase().replaceFirst("zookeeper://", ""));
        }
    }
}
