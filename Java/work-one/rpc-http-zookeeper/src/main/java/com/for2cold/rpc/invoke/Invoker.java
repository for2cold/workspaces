package com.for2cold.rpc.invoke;

import com.for2cold.rpc.exception.RpcException;

import java.io.OutputStream;

/**
 * Created by jasme on 16/3/16.
 */
public interface Invoker {

    /**
     * 发送请求
     * @param request
     * @param url
     * @return
     */
    String request(String request, String url) throws RpcException;

    /**
     * 响应请求
     * @param response
     * @param outputStream
     */
    void response(String response, OutputStream outputStream);
}
