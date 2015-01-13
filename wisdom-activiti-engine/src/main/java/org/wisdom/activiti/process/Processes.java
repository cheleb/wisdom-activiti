package org.wisdom.activiti.process;


import java.util.List;
import java.util.Map;


/**
 * Created by cheleb on 10/01/15.
 */
public interface Processes {


    List<Process> findAll();

    List<Instance> findAllInstancesByKey(String key, String labelTemplate, String ... vars );

    List<Instance> findAllInstancesByKey(String key);

    Instance findInstancesById(String instanceId, String ... vars);

    Instance findInstancesById(String instanceId);

    List<Task> findByTaskKeyAndField(String key, Map<String, String> predicate, String... vars);

    List<Task> findAllTasksByInstanceKey(String s, String ... vars);

    void completeTask(String taskId, Map<String, Object> params);
}
