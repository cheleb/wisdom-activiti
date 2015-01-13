package org.wisdom.activiti.process.impl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.wisdom.activiti.process.*;
import org.wisdom.activiti.process.Process;

import java.util.*;

/**
 * Created by cheleb on 10/01/15.
 */
public class ProcessesImpl implements Processes {


    private RepositoryService repositoryService;

    private RuntimeService runtimeService;

    private TaskService taskService;

    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    @Override
    public List<Process> findAll() {
        List<Process> processes = new ArrayList<>();
        for (ProcessDefinition processDefinition : repositoryService.createProcessDefinitionQuery().latestVersion().orderByProcessDefinitionVersion().desc().list()) {
            Process process = new Process();
            process.setId(processDefinition.getId());
            process.setName(processDefinition.getName());
            process.setDescription(processDefinition.getDescription());
            processes.add(process);
        }
        return processes;
    }

    @Override
    public List<Instance> findAllInstancesByKey(String key) {
        return findAllInstancesByKey(key, null);
    }

    @Override
    public Instance findInstancesById(String instanceId, String ... vars) {
        Instance instance = findInstancesById(instanceId);

        Map<String, Object> variables = new HashMap<>(vars.length);

        for (int i = 0; i < vars.length; i++) {
            Object val = runtimeService.getVariable(instance.getId(), vars[i]);
            if(val!=null){
                variables.put(vars[i], val);
            }
        }

        instance.setVars(variables);

        return instance;
    }

    @Override
    public Instance findInstancesById(String instanceId) {
        ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(instanceId).singleResult();

        return buildtInstance(processInstance);
    }
    @Override
    public List<org.wisdom.activiti.process.Task> findByTaskKeyAndField(String key, Map<String,String> predicate ,String ... vars){


        TaskQuery taskQuery = taskService.createTaskQuery().taskDefinitionKey(key);
        for (Map.Entry<String,String> entry : predicate.entrySet()){
            taskQuery = taskQuery.processVariableValueEquals(entry.getKey(), entry.getValue());
        }
        return getTasksWithVariables(taskQuery, vars);

    }

    private List<org.wisdom.activiti.process.Task> getTasksWithVariables(TaskQuery taskQuery, String[] vars) {
        List<org.wisdom.activiti.process.Task> list = new ArrayList<>();
        for (Task t : taskQuery.list()) {
            org.wisdom.activiti.process.Task task = new org.wisdom.activiti.process.Task();
            task.setId(t.getId());
            task.setName(t.getName());
            task.setKey(t.getTaskDefinitionKey());
            task.setDescription(t.getDescription());
            task.setInstanceId(t.getProcessInstanceId());

            Map<String, Object> variables = new HashMap<>(vars.length);

            for (int i = 0; i < vars.length; i++) {
                Object val = runtimeService.getVariable(t.getExecutionId(), vars[i]);
                if(val!=null){
                    variables.put(vars[i], val);
                }
            }
            task.setVars(variables);
            list.add(task);
        }
        return list;
    }

    @Override
    public List<org.wisdom.activiti.process.Task> findAllTasksByInstanceKey(String key, String ... vars) {
        return getTasksWithVariables(taskService.createTaskQuery().processDefinitionKey(key), vars);

    }

    @Override
    public void completeTask(String taskId, Map<String, Object> params) {
        taskService.complete(taskId, params);
    }

    @Override
    public List<Instance> findAllInstancesByKey(String key, String labelTemplate, String ... vars ) {


        List<Instance> instances = new ArrayList<>();
        for (ProcessInstance processInstance : runtimeService.createProcessInstanceQuery().processDefinitionKey(key).list()) {
            Instance instance = buildtInstance(processInstance);

            if(labelTemplate != null ){



                String values[] = new String[vars.length];
                for (int i = 0; i < vars.length; i++) {
                    Object val = runtimeService.getVariable(processInstance.getId(), vars[i]);
                    if(val!=null){
                        values[i] = String.valueOf(val);
                    }else{
                        values[i] ="";
                    }
                }

                instance.setLabel(String.format(labelTemplate, values));
            }

            instances.add(instance);
        }

        return instances;
    }

    private Instance buildtInstance(ProcessInstance processInstance) {
        Instance instance = new Instance();
        if(processInstance==null)
            return instance;
        instance.setActivityId(processInstance.getActivityId());
        instance.setId(processInstance.getId());
        instance.setProcessId(processInstance.getProcessDefinitionId());
        instance.setProcessName(processInstance.getProcessDefinitionName());
        return instance;
    }
}
