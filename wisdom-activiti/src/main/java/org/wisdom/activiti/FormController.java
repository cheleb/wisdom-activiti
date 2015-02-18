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
import org.wisdom.api.http.MimeTypes;
import org.wisdom.api.http.Result;
import org.wisdom.api.security.Authenticated;

import javax.activation.MimeType;


@Controller
@Path("/activiti")
@Authenticated("Monitor-Authenticator")
public class FormController extends DefaultController {


    @Requires
    private Forms forms;

    @Route(method = HttpMethod.GET, uri = "/process/{key}:{deployment}:{id}/startform")
    public Result startform(@PathParameter("key") String key,@PathParameter("deployment") String deployment, @PathParameter("id") String id){
        String processDefinitionId = key + ':' + deployment + ':' + id;
        if(request().accepts(MimeTypes.JSON)){
            return json().render(forms.fields(processDefinitionId));
        }
        return ok(forms.fields(processDefinitionId));
    }

}
