package com.for2cold.activiti.controller;

import com.for2cold.activiti.service.LeaveService;
import org.activiti.engine.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;

/**
 * Created by jasme on 2016/4/5 17:35.
 */
@Controller
@RequestMapping("/front/leave")
public class LeaveController {

    @Resource
    private LeaveService leaveService;

    @Resource
    private IdentityService identityService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private HistoryService historyService;

    @Resource
    private TaskService taskService;

    @Resource
    private ManagementService managementService;

    @Resource
    private FormService formService;

    @Resource
    private RepositoryService repositoryService;


}
