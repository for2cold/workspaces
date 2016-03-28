package com.for2cold.rpc.serialize;

/**
 * Created by jasme on 16/3/18.
 */
public interface Parser {

    /**
     * 请求解析
     * @param param
     * @return
     */
    Request reqParser(String param);

    /**
     * 响应解析
     * @param result
     * @param <T>
     * @return
     */
    <T> T respParser(String result);
}
