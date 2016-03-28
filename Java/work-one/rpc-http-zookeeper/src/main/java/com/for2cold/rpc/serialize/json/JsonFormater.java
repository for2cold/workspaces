package com.for2cold.rpc.serialize.json;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.for2cold.rpc.serialize.Formater;
import com.for2cold.rpc.serialize.Request;

/**
 * Created by jasme on 16/3/19.
 */
public class JsonFormater implements Formater {

    public static final Formater formater = new JsonFormater();

    public String reqFormat(Class<?> clazz, String method, Object param) {

        Request request = new Request();
        request.setParam(param);
        request.setClazz(clazz);
        request.setMethod(method);

        return JSON.toJSONString(request, SerializerFeature.WriteClassName);
    }

    public String respFormat(Object param) {
        return JSON.toJSONString(param, SerializerFeature.WriteClassName);
    }
}
