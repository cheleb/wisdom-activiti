package org.wisdom.activiti.process;

import org.activiti.engine.repository.ProcessDefinition;

/**
 * Created by chelebithil on 27/12/14.
 */
public class ProcessDefinitionDTO implements ProcessDefinition {
    private String id;
    private String category;
    private String name;
    private String key;
    private String descritption;
    private int version;
    private String resourceName;
    private String deploymentId;
    private String diagramResourceName;
    private boolean startFormKey;
    private boolean graphicalNotation;
    private boolean suspended;
    private String tenantId;

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String getId() {
        return id;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String getCategory() {
        return category;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return name;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getKey() {
        return key;
    }

    public void setDescritption(String descritption) {
        this.descritption = descritption;
    }

    @Override
    public String getDescription() {
        return descritption;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    @Override
    public int getVersion() {
        return version;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    @Override
    public String getResourceName() {
        return resourceName;
    }

    public void setDeploymentId(String deploymentId) {
        this.deploymentId = deploymentId;
    }

    @Override
    public String getDeploymentId() {
        return deploymentId;
    }


    public void setDiagramResourceName(String diagramResourceName) {
        this.diagramResourceName = diagramResourceName;
    }

    @Override
    public String getDiagramResourceName() {
        return diagramResourceName;
    }

    public void setStartFormKey(boolean startFormKey) {
        this.startFormKey = startFormKey;
    }

    @Override
    public boolean hasStartFormKey() {
        return startFormKey;
    }

    public void setGraphicalNotation(boolean graphicalNotation) {
        this.graphicalNotation = graphicalNotation;
    }

    @Override
    public boolean hasGraphicalNotation() {
        return graphicalNotation;
    }

    public void setSuspended(boolean suspended) {
        this.suspended = suspended;
    }

    @Override
    public boolean isSuspended() {
        return suspended;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    @Override
    public String getTenantId() {
        return tenantId;
    }
}
