/**
 * 
 */
package org.sample.web.components;

import java.math.BigDecimal;

import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

/**
 * @author mpostelnicu
 * 
 */
public class TextFieldBootstrapFormComponent<TYPE> extends GenericBootstrapFormComponent<TYPE, TextField<TYPE>> {
	private static final long serialVersionUID = 1L;

	public TextFieldBootstrapFormComponent(String id, IModel<String> labelModel, IModel<TYPE> model) {
		super(id, labelModel, model);
	}
	
	public TextFieldBootstrapFormComponent(String id, IModel<String> labelModel) {
		super(id, labelModel, null);
	}

	@Override
	protected TextField<TYPE> inputField(String id, IModel<TYPE> model) {
		return (TextField<TYPE>) new TextField<TYPE>(id,initFieldModel());
	}
	
    public TextFieldBootstrapFormComponent<TYPE> integer() {
        field.setType(Integer.class);
        return this;
    }
    
    public TextFieldBootstrapFormComponent<TYPE> decimal() {
        field.setType(BigDecimal.class);
        return this;
    }
}
