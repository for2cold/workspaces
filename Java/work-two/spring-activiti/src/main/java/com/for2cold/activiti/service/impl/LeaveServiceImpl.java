package com.for2cold.activiti.service.impl;

import com.for2cold.activiti.repository.LeaveRepository;
import com.for2cold.activiti.service.LeaveService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by jasme on 2016/4/5 17:34.
 */
@Service
public class LeaveServiceImpl implements LeaveService {

    @Resource
    private LeaveRepository leaveRepository;
}
