/**
 * 
 */
package org.sample.web.components;

import java.util.Collection;

import org.apache.wicket.model.IModel;

import com.vaynberg.wicket.select2.ChoiceProvider;
import com.vaynberg.wicket.select2.Select2MultiChoice;

/**
 * @author mpostelnicu
 * 
 */
public class Select2MultiChoiceBootstrapFormComponent<TYPE> extends GenericBootstrapFormComponent<Collection<TYPE>, Select2MultiChoice<TYPE>> {



	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Select2MultiChoiceBootstrapFormComponent(String id, IModel<String> labelModel, IModel<Collection<TYPE>> model,
			ChoiceProvider<TYPE> choiceProvider) {
		super(id, labelModel, model);
		field.setProvider(choiceProvider);
		field.getSettings().setAllowClear(true);
		field.getSettings().setPlaceholder("");
		field.getSettings().setDropdownAutoWidth(true);
	}
	
	public Select2MultiChoiceBootstrapFormComponent<TYPE> provider(ChoiceProvider<TYPE> choiceProvider) {
		field.setProvider(choiceProvider);
		return this;
	}

	public Select2MultiChoiceBootstrapFormComponent(String id, IModel<String> labelModel, ChoiceProvider<TYPE> choiceProvider) {
		this(id, labelModel, null, choiceProvider);
	}


	@Override
	protected Select2MultiChoice<TYPE> inputField(String id, IModel<Collection<TYPE>> model) {
		return new Select2MultiChoice<TYPE>(id, initFieldModel());
	}

	
	@Override
	protected String getUpdateEvent() {
		return "onchange";
	}
}
