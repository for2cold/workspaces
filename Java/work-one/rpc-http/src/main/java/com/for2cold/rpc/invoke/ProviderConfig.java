package com.for2cold.rpc.invoke;

/**
 * Created by jasme on 16/3/16.
 */
public class ProviderConfig {

    private String target;

    private Integer port;

    public ProviderConfig() {
    }

    public ProviderConfig(Integer port, String target) {
        this.port = port;
        this.target = target;
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
