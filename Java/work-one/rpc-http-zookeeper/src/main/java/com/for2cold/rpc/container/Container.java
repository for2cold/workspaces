package com.for2cold.rpc.container;

/**
 * Created by jasme on 16/3/16.
 */
public abstract class Container {

    public static volatile boolean isStart = false;

    public abstract void start();

    public static volatile Container container = null;
}
