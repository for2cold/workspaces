package com.for2cold.rpc.invoke;

import com.for2cold.rpc.zookeeper.ZookeeperClient;

import java.net.Inet4Address;
import java.net.UnknownHostException;

/**
 * Created by jasme on 16/3/16.
 */
public class ProviderConfig {

    private String target;

    private Integer port;

    private ZookeeperClient client;


    public ProviderConfig(Integer port, String target) {
        this.port = port;
        this.target = target;
        if (target.toLowerCase().startsWith("zookeeper://")) {
            client = new ZookeeperClient(target.toLowerCase().replaceFirst("zookeeper://", ""));
        }
    }

    public void register(Class<?> clazz) {
        if (client != null) {
            client.createPersistent("/rpc/" + clazz.getName().replaceAll("\\.", "/"));
            client.createEphemeral("/rpc/" + clazz.getName().replaceAll("\\.", "/") + "/node", getNodeInfo());
        }
    }

    public String getNodeInfo() {
        try {
            return "http://" + Inet4Address.getLocalHost().getHostAddress() + ":" + port;
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
