package com.for2cold.rpc.serialize.json;

import com.alibaba.fastjson.JSON;
import com.for2cold.rpc.serialize.Parser;
import com.for2cold.rpc.serialize.Request;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jasme on 16/3/19.
 */
public class JsonParser implements Parser {

    private static final Logger logger = LoggerFactory.getLogger(JsonParser.class);

    public static final Parser parser = new JsonParser();

    public Request reqParser(String param) {
        return JSON.parseObject(param, Request.class);
    }

    public <T> T respParser(String result) {
        return (T) JSON.parse(result);
    }
}
