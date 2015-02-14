package org.wisdom.activiti.process.form;

import org.activiti.engine.FormService;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.apache.felix.ipojo.annotations.Component;
import org.apache.felix.ipojo.annotations.Instantiate;
import org.apache.felix.ipojo.annotations.Provides;
import org.apache.felix.ipojo.annotations.Requires;

import java.util.List;

/**
 * Created by chelebithil on 13/02/15.
 */
@Component
@Provides
@Instantiate
public class FormsImpl implements Forms {

    @Requires
    FormService formService;

    @Override
    public List<FormProperty> fields(String processId) {

        StartFormData startFormData = formService.getStartFormData(processId);

        return startFormData.getFormProperties();
    }
}
