package org.wisdom.activiti.process.impl;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.activiti.process.ProcessBusiness;
import org.wisdom.api.annotations.Service;

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



    @Override
    public List<String> listAll() {
        List<String> processes = new ArrayList<String>();
        for (ProcessDefinition processDefinition : repositoryService.createProcessDefinitionQuery().list()) {
            processes.add(processDefinition.getId());
        }
        return processes;
    }
}
