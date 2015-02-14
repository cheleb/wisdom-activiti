package org.wisdom.activiti;

/**
 * Created by chelebithil on 13/02/15.
 */

import org.apache.felix.ipojo.annotations.Requires;
import org.wisdom.activiti.process.form.Forms;
import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.Controller;
import org.wisdom.api.annotations.Path;
import org.wisdom.api.annotations.PathParameter;
import org.wisdom.api.annotations.Route;
import org.wisdom.api.http.HttpMethod;
import org.wisdom.api.http.Result;
import org.wisdom.api.security.Authenticated;


@Controller
@Path("/monitor/activiti")
@Authenticated("Monitor-Authenticator")
public class FormController extends DefaultController {


    @Requires
    private Forms forms;

    @Route(method = HttpMethod.GET, uri = "/form/{key}:{deployment}:{id}")
    public Result formProperties(@PathParameter("key") String key,@PathParameter("deployment") String deployment, @PathParameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;
        return ok(forms.fields(processDefinitionId));
    }

}
