/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author mpostelnicu
 * 
 */
public abstract class IndicatingAjaxBootstrapSubmitButton extends IndicatingAjaxButton {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param model
	 */
	public IndicatingAjaxBootstrapSubmitButton(String id, IModel<String> model) {
		super(id, model);
		add(new AttributeAppender("class", new Model<String>("btn btn-primary"), " "));
	}

	@Override
	protected abstract void onSubmit(AjaxRequestTarget target, Form<?> form);

}
