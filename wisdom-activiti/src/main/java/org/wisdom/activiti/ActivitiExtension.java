package org.wisdom.activiti;

import org.wisdom.api.DefaultController;
import org.wisdom.api.annotations.Controller;
import org.wisdom.api.security.Authenticated;
import org.wisdom.monitor.service.MonitorExtension;

/**
 * Created by chelebithil on 26/12/14.
 */
@Controller
@Authenticated("Monitor-Authenticator")
public class ActivitiExtension extends DefaultController implements MonitorExtension {


    @Override
    public String label() {
        return "Processes";
    }

    @Override
    public String url() {
        return "/monitor/activiti/processes";
    }

    @Override
    public String category() {
        return "Activiti";
    }
}
