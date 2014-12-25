package org.wisdom.activiti.process;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;

import java.util.List;

public interface ProcessBusiness {
    List<String> processes();

    List<ProcessInstance> instances();

    List<ProcessInstance> instances(String key, String deployment, String id);

    boolean deleteInstance(String id);
}