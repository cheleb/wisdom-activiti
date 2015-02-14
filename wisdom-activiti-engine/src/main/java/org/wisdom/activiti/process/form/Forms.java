package org.wisdom.activiti.process.form;

import org.activiti.engine.form.FormProperty;

import java.util.List;

/**
 * Created by chelebithil on 13/02/15.
 */
public interface Forms {


    List<FormProperty> fields(String processId);


}
