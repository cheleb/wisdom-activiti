package org.wisdom.activiti.process;

import java.util.Map;

/**
 * Created by cheleb on 10/01/15.
 */
public class Task {

    private String key;

    private String id;

    private String instanceId;

    private String name;

    private String description;

    public Map<String, Object> getVars() {
        return vars;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getInstanceId() {
        return instanceId;
    }

    public void setInstanceId(String instanceId) {
        this.instanceId = instanceId;
    }

    public void setVars(Map<String, Object> vars) {
        this.vars = vars;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private Map<String, Object> vars;

}
