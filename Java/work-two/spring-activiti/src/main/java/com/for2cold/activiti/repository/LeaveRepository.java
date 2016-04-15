package com.for2cold.activiti.repository;

import com.for2cold.activiti.mapper.LeaveMapper;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;

/**
 * Created by jasme on 2016/4/5 17:33.
 */
@Repository
public class LeaveRepository {

    @Resource
    private LeaveMapper leaveMapper;
}
