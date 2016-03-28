package com.for2cold.rpc.serialize;

/**
 * Created by jasme on 16/3/18.
 */
public interface Formater {

    /**
     * 请求格式化
     * @param clazz
     * @param method
     * @param param
     * @return
     */
    String reqFormat(Class<?> clazz, String method, Object param);

    /**
     * 响应格式化
     * @param param
     * @return
     */
    String respFormat(Object param);
}
