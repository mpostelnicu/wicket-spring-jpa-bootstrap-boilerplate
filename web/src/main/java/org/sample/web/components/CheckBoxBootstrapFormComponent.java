/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.markup.html.form.CheckBox;
import org.apache.wicket.model.IModel;

/**
 * @author mpostelnicu
 * 
 */
public class CheckBoxBootstrapFormComponent extends GenericBootstrapFormComponent<Boolean, CheckBox> {
	private static final long serialVersionUID = 1L;

	public CheckBoxBootstrapFormComponent(String id, IModel<String> labelModel, IModel<Boolean> model) {
		super(id, labelModel, model);
	}
	
	public CheckBoxBootstrapFormComponent(String id, IModel<String> labelModel) {
		super(id, labelModel, null);
	}

	
	@Override
	protected CheckBox inputField(String id, IModel<Boolean> model) {
		return (CheckBox) new CheckBox(id,initFieldModel());
	}

	

}
