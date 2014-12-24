package org.wisdom.activiti.process.impl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.wisdom.activiti.process.ProcessBusiness;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by cheleb on 24/12/14.
 */
public class ProcessBusinessImpl implements ProcessBusiness {


    private RepositoryService repositoryService;


    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }

    public RepositoryService getRepositoryService() {
        return repositoryService;
    }

    @Override
    public List<String> listAll() {
        List<String> processes = new ArrayList<String>();
        for (ProcessDefinition processDefinition : repositoryService.createProcessDefinitionQuery().list()) {
            processes.add(processDefinition.getId());
        }
        return processes;
    }
}
