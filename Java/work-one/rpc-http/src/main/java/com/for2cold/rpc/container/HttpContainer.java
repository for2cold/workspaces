package com.for2cold.rpc.container;

import com.for2cold.rpc.container.Container;
import com.for2cold.rpc.invoke.ProviderConfig;
import org.mortbay.jetty.Connector;
import org.mortbay.jetty.Server;
import org.mortbay.jetty.handler.AbstractHandler;
import org.mortbay.jetty.nio.SelectChannelConnector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by jasme on 16/3/16.
 */
public class HttpContainer extends Container {

    private static final Logger logger = LoggerFactory.getLogger(HttpContainer.class);

    private AbstractHandler httpHandler;

    private ProviderConfig config;

    public HttpContainer(AbstractHandler httpHandler) {
        this(httpHandler, new ProviderConfig(8080, "/invoke"));
    }

    public HttpContainer(AbstractHandler httpHandler, ProviderConfig config) {
        this.httpHandler = httpHandler;
        this.config = config;
        Container.container = this;
    }


    @Override
    public void start() {

        Server server = new Server();

        try {
            SelectChannelConnector connector = new SelectChannelConnector();
            connector.setPort(config.getPort());
            server.setConnectors(new Connector[]{
                    connector
            });

            server.setHandler(httpHandler);
            server.start();

        } catch (Throwable e) {
            logger.error("服务启动失败", e);
        }

    }
}
