package org.wisdom.activiti.process;

import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

public interface ProcessBusiness {

    List<ProcessDefinition> processes();

    boolean deleteProcess(String processDefinitionId);

    List<ProcessInstance> instances();

    List<ProcessInstance> instances(String key, String deployment, String id);

    ProcessInstance instanceById(String id);

    boolean deleteInstance(String id);

    InputStream getDiagram(String processDefinitionId);

    List<ProcessDefinition> processesByKey(String processDefinitionId);

    ProcessDefinition processById(String processDefinitionId);

    Map<String, Object> getInstanceVariables(String taskId);

    List<Task> getcurrentTasks(String processInstanceId);
}