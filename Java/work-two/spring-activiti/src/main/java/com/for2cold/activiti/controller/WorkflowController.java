package com.for2cold.activiti.controller;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Repository;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipInputStream;

/**
 * Created by jasme on 2016/4/5 18:12.
 */
@Controller
@RequestMapping("/front/workflow")
public class WorkflowController {

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

    @RequestMapping(value = "deploy", method = RequestMethod.GET)
    public String deploy() {
        return "workflow/deploy";
    }

    @RequestMapping(value = "deploy", method = RequestMethod.POST)
    public String deploy(MultipartFile file, HttpServletRequest request, RedirectAttributes redirectAttributes) throws IOException {
        if (!file.isEmpty()) {

            byte[] bytes = file.getBytes();

            String realPath = request.getSession().getServletContext().getRealPath("/upload");
            File out = new File(realPath, file.getOriginalFilename());

            FileUtils.writeByteArrayToFile(out, bytes);

            if (FilenameUtils.getExtension(file.getOriginalFilename()).equalsIgnoreCase("zip")) {
                ZipInputStream inputStream = new ZipInputStream(file.getInputStream());

                repositoryService.createDeployment()
                        .addZipInputStream(inputStream)
                        .deploy();
            } else {
                redirectAttributes.addFlashAttribute("msg", "请上次zip格式的文件");
            }
        }
        return "redirect:/front/workflow/list";
    }

    @RequestMapping("list")
    public String list(Model model) {

        List<Object> objects = new ArrayList<>();

        List<ProcessDefinition> list = repositoryService.createProcessDefinitionQuery().list();
        for (ProcessDefinition definition : list) {
            String deploymentId = definition.getDeploymentId();
            Deployment deployment = repositoryService.createDeploymentQuery().deploymentId(deploymentId).singleResult();
            objects.add(new Object[]{definition, deployment});
        }
        model.addAttribute("objects", objects);
        return "workflow/list";
    }

    @RequestMapping("info/{deploymentId}/{resourceName}")
    public void info(@PathVariable String deploymentId, @PathVariable String resourceName, HttpServletResponse response) {

        InputStream inputStream = repositoryService.getResourceAsStream(deploymentId, resourceName);

        try {
            byte[] bytes = IOUtils.toByteArray(inputStream);
            ServletOutputStream out = response.getOutputStream();
            out.write(bytes, 0, bytes.length);
            out.flush();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
