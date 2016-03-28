package com.for2cold.rpc.zookeeper;

import com.for2cold.rpc.exception.RpcException;
import com.for2cold.rpc.exception.RpcExceptionCodeEnum;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.exception.ZkInterruptedException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 2016/3/23.
 */
public class ZookeeperClient {

    private ZkClient zkClient;

    private volatile Watcher.Event.KeeperState state = Watcher.Event.KeeperState.SyncConnected;

    public ZookeeperClient(String url) {

        zkClient = new ZkClient(url);
    }


    /**
     * 创建持久化目录
     * @param path
     */
    public void createPersistent(String path) {
        try {
            zkClient.createPersistent(path, true);
        } catch (RuntimeException e) {
        }
    }

    /**
     * 创建临时目录
     * @param path
     * @param data
     */
    public void createEphemeral(String path, String data) {
        try {
            zkClient.createEphemeral(path, data);
        } catch (RuntimeException e) {
        }
    }

    /**
     * 获取子目录
     * @param path
     * @return
     */
    public List<String> getChildren(String path) {
        try {
            return zkClient.getChildren(path);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * 获取节点数据
     * @param path
     * @return
     */
    public String getData(String path) throws RpcException {
        try {
            return zkClient.readData(path);
        } catch (Exception e) {
            e.printStackTrace();
        }
        throw new RpcException(RpcExceptionCodeEnum.NO_PROVIDERS.getCode(), path);
    }

    /**
     * 删除节点
     * @param path
     */
    public void delete(String path) {
        try {
            zkClient.delete(path);
        } catch (Exception e) {
        }
    }

    /**
     * 订阅监听
     * @param path
     * @param watcher
     */
    public void setWatcher(String path, IZkDataListener watcher) {
        zkClient.subscribeDataChanges(path, watcher);
    }

    /**
     * 判断是否已连接
     * @return
     */
    public boolean isConnected() {
        return state == Watcher.Event.KeeperState.SyncConnected;
    }

    /**
     * 关闭连接
     */
    public void doClose() {
        try {
            zkClient.close();
        } catch (ZkInterruptedException e) {
        }
    }

}
