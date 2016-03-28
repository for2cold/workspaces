package com.for2cold.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

public class FirstDemo {

	public static void main(String[] args) {
		/**
		 * 1. 创建流程图
		 * 2. 上次流程图
		 * 3. 部署流程图
		 * 4. 启动流程
		 * 5. 处理流程任务
		 */
		
		// 1. 获取流程引擎配置
		ProcessEngineConfiguration config = ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("config/activiti.cfg.xml");
		// 2. 获取流程引擎
		ProcessEngine engine = config.buildProcessEngine();
		// 3. 获取RepositoryService
		RepositoryService repositoryService = engine.getRepositoryService();
		// 4. 部署流程图
		repositoryService.createDeployment()
				.addClasspathResource("process/firstdemo.bpmn20.xml")
				.deploy();
		// 5. 获取运行时服务 RuntimeService
		RuntimeService runtimeService = engine.getRuntimeService();
		// 6. 获取流程实例
		String processDefinitionKey = "myProcess";
		ProcessInstance instance = runtimeService.startProcessInstanceByKey(processDefinitionKey);
		// 7. 处理流程任务
		TaskService taskService = engine.getTaskService();
		// 8. 查询Task
		Task task = taskService.createTaskQuery().singleResult();
		// 9. 处理任务
		System.out.println(task.getName());
	}
}
