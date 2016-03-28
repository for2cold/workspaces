package com.for2cold.rpc.dubbo.facade.controller;

import com.for2cold.rpc.dubbo.facade.UserFacade;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * Created by root on 2016/3/25.
 */
@Component("userController")
public class UserController {

    @Resource
    private UserFacade userFacade;

    public String index() {
        return userFacade.findOne("Java Dubbo");
    }
}
