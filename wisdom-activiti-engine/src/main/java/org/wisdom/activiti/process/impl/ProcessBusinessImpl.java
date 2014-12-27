package org.wisdom.activiti.process.impl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
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



    @Override
    public List<String> processes() {
        List<String> processes = new ArrayList<String>();
        for (ProcessDefinition processDefinition : repositoryService.createProcessDefinitionQuery().list()) {
            processes.add(processDefinition.getId());
        }
        return processes;
    }

    @Override
    public InputStream getDiagram(String processDefinitionId){
        return  repositoryService.getProcessDiagram(processDefinitionId);
    }

    @Override
    public ProcessDefinition process(String processDefinitionId) {
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
    public boolean deleteInstance(String id) {
        runtimeService.deleteProcessInstance(id, "admin caneelation");
        return true;
    }
}
