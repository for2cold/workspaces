package com.for2cold.rpc.proxy;

import com.for2cold.rpc.invoke.ConsumerConfig;
import com.for2cold.rpc.invoke.HttpInvoker;
import com.for2cold.rpc.invoke.Invoker;
import com.for2cold.rpc.serialize.Formater;
import com.for2cold.rpc.serialize.Parser;
import com.for2cold.rpc.serialize.json.JsonFormater;
import com.for2cold.rpc.serialize.json.JsonParser;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * Created by jasme on 16/3/19.
 */
public class ConsumerProxyFactory implements InvocationHandler {

    private ConsumerConfig config;

    private Parser parser = JsonParser.parser;

    private Formater formater = JsonFormater.formater;

    private Invoker invoker = HttpInvoker.invoker;

    private String clazz;

    public Object create() throws ClassNotFoundException {
        Class<?> interfaceClass = Class.forName(clazz);
        return Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, this);
    }

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Class<?> interfalceClass = proxy.getClass().getInterfaces()[0];
        String req = formater.reqFormat(interfalceClass, method.getName(), args[0]);
        String resp = invoker.request(req, config);
        return parser.respParser(resp);
    }

    public ConsumerConfig getConfig() {
        return config;
    }

    public void setConfig(ConsumerConfig config) {
        this.config = config;
    }

    public Parser getParser() {
        return parser;
    }

    public void setParser(Parser parser) {
        this.parser = parser;
    }

    public Formater getFormater() {
        return formater;
    }

    public void setFormater(Formater formater) {
        this.formater = formater;
    }

    public Invoker getInvoker() {
        return invoker;
    }

    public void setInvoker(Invoker invoker) {
        this.invoker = invoker;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }
}
