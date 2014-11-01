/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.model.IModel;

import com.vaynberg.wicket.select2.ChoiceProvider;
import com.vaynberg.wicket.select2.Select2Choice;

/**
 * @author mpostelnicu
 * 
 */
public class Select2ChoiceBootstrapFormComponent<TYPE> extends GenericBootstrapFormComponent<TYPE, Select2Choice<TYPE>> {

	private static final long serialVersionUID = 1L;

	public Select2ChoiceBootstrapFormComponent(String id, IModel<String> labelModel, IModel<TYPE> model,
			ChoiceProvider<TYPE> choiceProvider) {
		super(id, labelModel, model);
		field.setProvider(choiceProvider);
		field.getSettings().setAllowClear(true);
		field.getSettings().setPlaceholder("");
		field.getSettings().setDropdownAutoWidth(true);
	}
	
	public Select2ChoiceBootstrapFormComponent<TYPE> provider(ChoiceProvider<TYPE> choiceProvider) {
		field.setProvider(choiceProvider);
		return this;
	}

	public Select2ChoiceBootstrapFormComponent(String id, IModel<String> labelModel, ChoiceProvider<TYPE> choiceProvider) {
		this(id, labelModel, null, choiceProvider);
	}

	@Override
	protected Select2Choice<TYPE> inputField(String id, IModel<TYPE> model) {
		return new Select2Choice<TYPE>(id, initFieldModel());
	}
	

	@Override
	protected String getUpdateEvent() {
		return "onchange";
	}
}
