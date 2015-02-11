/*
 * #%L
 * Wisdom-Framework
 * %%
 * Copyright (C) 2013 - 2014 Wisdom Framework
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */
package org.wisdom.activiti;

import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.activiti.process.ProcessBusiness;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.*;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.security.Authenticated;
import org.wisdom.api.templates.Template;
import org.wisdom.monitor.service.MonitorExtension;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Your first Wisdom Controller.
 */
@Controller
@Path("/monitor/activiti")
@Authenticated("Monitor-Authenticator")
public class ProcessController extends DefaultController {

    /**
     * Injects a template that lists processes.
     */
    @View("processes")
    Template processes;

    /**
     * Injects a template that shows detail of a process.
     */
    @View("process")
    Template process;

    /**
     * Injects a template that lists instances.
     */
    @View("instances")
    Template instances;

    /**
     * Injects activiti service.
     */
    @Requires
    ProcessBusiness processBusiness;

    @Requires
    RuntimeService runtimeService;

    /**
     * The action method returning the processes page.
     *
     * @return the processes page
     */
    @Route(method = HttpMethod.GET, uri = "/processes")
    public Result processes() {
        return ok(render(processes, "processes", processBusiness.processes()));
    }


    /**
     * The action method returning the process detail page.
     *
     * @param key of process
     * @param deployment of process
     * @param id of process
     * @return the process page
     */
    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}")
    public Result processById(@PathParameter("key") String key, @PathParameter("deployment") String deployment, @PathParameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;

        ProcessDefinition processDefinition = processBusiness.processById(processDefinitionId);

        if(request().accepts("application/json")){
            return ok(processDefinition).as("application/json");
        }

        return ok(render(process, "process", processDefinition, "allversions", processBusiness.processesByKey(key)));

    }

    /**
     * The action returns the bpmn diagram for a given process.
     *
     * @param key of process
     * @param deployment of process
     * @param id of process
     * @return
     */
    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}/diagram")
    public Result diagram(@PathParameter("key") String key, @PathParameter("deployment") String deployment, @PathParameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;
        return ok(processBusiness.getDiagram(processDefinitionId)).as("image/png");
    }


    /**
     * The action returns instances of a given process.
     *
     * @param key of process
     * @param deployment of process
     * @param id of process
     * @return
     */
    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}/instance/")
    public Result instances(@PathParameter("key") String key,@PathParameter("deployment") String deployment, @PathParameter("id") String id) {
        if (request().accepts("application/json")) {
            return ok(processBusiness.instances(key, deployment, id)).json();
        }
        return ok(render(instances,"processName", key, "processId", id, "instances", processBusiness.instances(key, deployment, id)));
    }


    @Route(method = HttpMethod.POST, uri="/process/{key}:{deployment}:{id}")
    public Result start(@PathParameter("key") String key,@PathParameter("deployment") String deployment, @PathParameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;

        ProcessInstance processInstance = runtimeService.startProcessInstanceById(processDefinitionId);

        return ok(processInstance.getId());
    }

    @Route(method = HttpMethod.DELETE, uri="/process/{key}:{deployment}:{id}")
    public Result delete(@PathParameter("key") String key,@PathParameter("deployment") String deployment, @PathParameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;
        if(processBusiness.deleteProcess(processDefinitionId)){
            List<ProcessDefinition> processDefinitions = processBusiness.processesByKey(key);
            if(processDefinitions.isEmpty()){
                return ok("/activiti/processes");
            }
            return ok("/activiti/process/" + processDefinitions.get(0).getId());
        }
        return internalServerError();
    }


}
