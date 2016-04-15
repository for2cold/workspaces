package com.for2cold.activiti.test;

import org.activiti.engine.*;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.spring.ProcessEngineFactoryBean;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by jasme on 2016/4/5 16:23.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring-config.xml")
public class WorkflowTestCase {

    @Resource
    private ProcessEngineFactoryBean processEngineFactoryBean;

    @Resource
    private RepositoryService repositoryService;

    @Resource
    private RuntimeService runtimeService;

    @Resource
    private TaskService taskService;

    @Resource
    private HistoryService historyService;

    @Resource
    private IdentityService identityService;

    @Test
    public void test() throws InterruptedException {
        repositoryService
                .createDeployment()
                .addClasspathResource("diagram/hello.bpmn")
                .deploy();

        System.out.println("Number of process definitions: " + repositoryService.createDeploymentQuery().count());

        identityService.setAuthenticatedUserId("for2cold");

        ProcessInstance processInstance = runtimeService.startProcessInstanceByKey("helloProcess");
        System.out.println(processInstance.getId());
        System.out.println(processInstance.getProcessDefinitionId());

        List<Task> tasks = taskService.createTaskQuery().taskAssignee("kermit").list();
        for (Task task : tasks) {
            System.out.println(task.getName() + " : " + task.getAssignee());

            taskService.claim(task.getId(), "kermit");
        }

        tasks = taskService.createTaskQuery().taskAssignee("kermit").list();
        for (Task task : tasks) {

            taskService.complete(task.getId());
            System.out.println(task.getName() + " : " + task.getId() + " completed ");
        }

        Thread.sleep(10 * 1000);

        tasks = taskService.createTaskQuery().taskAssignee("stone").list();
        for (Task task : tasks) {
            System.out.println(task.getName() + " : " + task.getAssignee());
            taskService.claim(task.getId(), "stone");
        }

        tasks = taskService.createTaskQuery().taskAssignee("stone").list();
        for (Task task : tasks) {
            taskService.complete(task.getId());
            System.out.println(task.getName() + " : " + task.getId() + " completed ");
        }

        tasks = taskService.createTaskQuery().taskAssignee("kermit").list();
        for (Task task : tasks) {
            System.out.println(task.getName() + " : " + task.getAssignee());
            taskService.claim(task.getId(), "kermit");
        }


        HistoricProcessInstance hpInstance =
                historyService.createHistoricProcessInstanceQuery()
                        .processInstanceId(processInstance.getId()).singleResult();
        System.out.println("end time: " + hpInstance.getEndTime());
    }
}
