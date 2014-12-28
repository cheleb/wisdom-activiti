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
import org.activiti.engine.repository.ProcessDefinition;
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
import java.util.List;

/**
 * Your first Wisdom Controller.
 */
@Controller
@Path("/activiti")
@Authenticated("Monitor-Authenticator")
public class ProcessController extends DefaultController {

    /**
     * Injects a template named 'processes'.
     */
    @View("processes")
    Template processes;

    @View("process")
    Template process;

    /**
     * Injects a template named 'instances'.
     */
    @View("instances")
    Template instances;


    /**
     * Injects activiti repositoryService.
     */
    @Requires
    ProcessBusiness processBusiness;

    /**
     * The action method returning the welcome page. It handles
     * HTTP GET request on the "/" URL.
     *
     * @return the welcome page
     */
    @Route(method = HttpMethod.GET, uri = "/processes")
    public Result processes() {
        return ok(render(processes, "processes", processBusiness.processes()));
    }

    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}")
    public Result process(@Parameter("key") String key, @Parameter("deployment") String deployment, @Parameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;

        ProcessDefinition processDefinition = processBusiness.process(processDefinitionId);

        if(request().accepts("application/json")){
            return ok(processDefinition).as("application/json");
        }

        return ok(render(process, "process", processDefinition));

    }

    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}/diagram")
    public Result diagram(@Parameter("key") String key, @Parameter("deployment") String deployment, @Parameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;
        return ok(processBusiness.getDiagram(processDefinitionId)).as("image/png");
    }


    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}/instances")
    public Result instances(@Parameter("key") String key,@Parameter("deployment") String deployment, @Parameter("id") String id) {
        if (request().accepts("application/json")) {
            return ok(processBusiness.instances(key, deployment, id)).json();
        }
        return ok(render(instances,"processIds", key+':'+deployment+':'+id, "instances", processBusiness.instances(key, deployment, id)));
    }
}
