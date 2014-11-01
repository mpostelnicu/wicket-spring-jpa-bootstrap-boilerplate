/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.markup.html.form.TextArea;
import org.apache.wicket.model.IModel;

/**
 * @author mpostelnicu
 * 
 */
public class TextAreaFieldBootstrapFormComponent<TYPE> extends GenericBootstrapFormComponent<TYPE, TextArea<TYPE>> {


	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TextAreaFieldBootstrapFormComponent(String id, IModel<String> labelModel, IModel<TYPE> model) {
		super(id, labelModel, model);
	}
	
	public TextAreaFieldBootstrapFormComponent(String id, IModel<String> labelModel) {
		super(id, labelModel, null);
	}

	@Override
	protected TextArea<TYPE> inputField(String id, IModel<TYPE> model) {
		return (TextArea<TYPE>) new TextArea<TYPE>(id,initFieldModel());
	}

}
