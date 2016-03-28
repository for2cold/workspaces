package com.for2cold.rpc.proxy;

import com.for2cold.rpc.container.Container;
import com.for2cold.rpc.container.HttpContainer;
import com.for2cold.rpc.exception.RpcException;
import com.for2cold.rpc.exception.RpcExceptionCodeEnum;
import com.for2cold.rpc.invoke.HttpInvoker;
import com.for2cold.rpc.invoke.Invoker;
import com.for2cold.rpc.invoke.ProviderConfig;
import com.for2cold.rpc.serialize.Formater;
import com.for2cold.rpc.serialize.Parser;
import com.for2cold.rpc.serialize.Request;
import com.for2cold.rpc.serialize.json.JsonFormater;
import com.for2cold.rpc.serialize.json.JsonParser;
import org.mortbay.jetty.handler.AbstractHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jasme on 16/3/19.
 */
public class ProviderProxyFactory extends AbstractHandler {

    private static final Logger logger = LoggerFactory.getLogger(ProviderProxyFactory.class);

    private Map<Class<?>, Object> providers = new ConcurrentHashMap<Class<?>, Object>();

    private static ProviderProxyFactory factory;

    private Parser parser = JsonParser.parser;

    private Formater formater = JsonFormater.formater;

    private Invoker invoker = HttpInvoker.invoker;

    public ProviderProxyFactory(Map<Class<?>, Object> providers) {
        if (Container.container == null) {
            new HttpContainer(this).start();
        }
        for (Map.Entry<Class<?>, Object> entry : providers.entrySet()) {
            register(entry.getKey(), entry.getValue());
        }
        factory = this;
    }

    public ProviderProxyFactory(Map<Class<?>, Object> providers, ProviderConfig config) {
        if (Container.container == null) {
            new HttpContainer(this, config).start();
        }
        for (Map.Entry<Class<?>, Object> entry : providers.entrySet()) {
            register(entry.getKey(), entry.getValue());
        }
        factory = this;
    }

    private void register(Class<?> clazz, Object object) {
        providers.put(clazz, object);
        logger.info("{} 对象注册成功", clazz.getSimpleName());
    }

    public void handle(String s, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, int i) throws IOException, ServletException {
        String reqStr = httpServletRequest.getParameter("data");

        try {
            Request rpcRequest = parser.reqParser(reqStr);

            Object result = rpcRequest.invoke(ProviderProxyFactory.getInstance().getBeanByClass(rpcRequest.getClazz()));

            invoker.response(formater.respFormat(result), httpServletResponse.getOutputStream());
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (RpcException e) {
            e.printStackTrace();
        }
    }

    public Object getBeanByClass(Class<?> clazz) throws RpcException {
        Object bean = providers.get(clazz);
        if (bean != null) {
            return bean;
        }
        throw new RpcException(RpcExceptionCodeEnum.NO_BEAN_FOUND.getCode(), clazz);
    }

    public static ProviderProxyFactory getInstance() {
        return factory;
    }
}
