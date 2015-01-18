package org.wisdom.activiti.process.impl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.springframework.beans.BeanUtils;
import org.wisdom.activiti.process.ProcessBusiness;
import org.wisdom.activiti.process.ProcessDefinitionDTO;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by cheleb on 24/12/14.
 */
@Component
@Provides
@Instantiate
public class ProcessBusinessImpl implements ProcessBusiness {


    @Requires
    private RepositoryService repositoryService;

    @Requires
    private RuntimeService runtimeService;

    @Requires
    private TaskService taskService;



    @Override
    public List<ProcessDefinition> processes() {
        List<ProcessDefinition> processes = new ArrayList<>();
        for (ProcessDefinition processDefinition : repositoryService.createProcessDefinitionQuery().latestVersion().orderByProcessDefinitionVersion().desc().list()) {
            processes.add(processDefinition);
        }
        return processes;
    }

    @Override
    public boolean deleteProcess(String processDefinitionId) {
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().processDefinitionId(processDefinitionId).singleResult();
        repositoryService.deleteDeployment(processDefinition.getDeploymentId());
        return true;
    }

    @Override
    public InputStream getDiagram(String processDefinitionId){
        return  repositoryService.getProcessDiagram(processDefinitionId);
    }

    @Override
    public List<ProcessDefinition> processesByKey(String key) {
        List<ProcessDefinition> list = new ArrayList<>();
        for (ProcessDefinition definition : repositoryService.createProcessDefinitionQuery().processDefinitionKey(key).orderByDeploymentId().asc().list()) {
            ProcessDefinition processDefinition = new ProcessDefinitionDTO();
            BeanUtils.copyProperties(definition, processDefinition);
            list.add(processDefinition);
        }
        return list;
    }

    @Override
    public ProcessDefinition processById(String processDefinitionId) {
        ProcessDefinition processDefinition = new ProcessDefinitionDTO();
        BeanUtils.copyProperties(repositoryService.getProcessDefinition(processDefinitionId), processDefinition);
        return processDefinition;
    }



    @Override
    public List<ProcessInstance> instances() {
        List<ProcessInstance> instances = new ArrayList<>();

        for (ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().list()) {
            instances.add(processInstance);
        }

        return instances;


    }

    @Override
    public List<ProcessInstance> instances(String key, String deployement, String id) {
        List<ProcessInstance> instances = new ArrayList<>();

        String processDefinitionId = key+ ':' + deployement + ":" + id;

        for (ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().processDefinitionId(processDefinitionId).list()) {
            instances.add(processInstance);
        }

        return instances;


    }

    @Override
    public ProcessInstance instanceById(String id){
        final ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(id).singleResult();
        return processInstance;
    }

    @Override
    public boolean deleteInstance(String id) {
        runtimeService.deleteProcessInstance(id, "admin caneelation");
        return true;
    }

    @Override
    public Map<String, Object> getInstanceVariables(String processInstanceId){
        final Task task = taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
        return  runtimeService.getVariables(task.getExecutionId());
    }

    @Override
    public Task getcurrentTask(String processInstanceId){
        return taskService.createTaskQuery().processInstanceId(processInstanceId).singleResult();
    }
}
