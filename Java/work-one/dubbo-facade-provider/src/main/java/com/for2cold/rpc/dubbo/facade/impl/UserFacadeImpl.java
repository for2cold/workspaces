package com.for2cold.rpc.dubbo.facade.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.for2cold.rpc.dubbo.facade.UserFacade;
import org.springframework.stereotype.Component;

/**
 * Created by root on 2016/3/25.
 */
@Component("userFacade")
public class UserFacadeImpl implements UserFacade {

    @Override
    public String findOne(String name) {
        return "Hello " + name;
    }
}
