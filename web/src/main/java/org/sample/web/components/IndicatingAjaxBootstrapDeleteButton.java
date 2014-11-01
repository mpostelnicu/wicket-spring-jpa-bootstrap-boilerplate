/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.attributes.AjaxCallListener;
import org.apache.wicket.ajax.attributes.AjaxRequestAttributes;
import org.apache.wicket.behavior.AttributeAppender;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxButton;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;

/**
 * @author mpostelnicu
 * 
 */
public abstract class IndicatingAjaxBootstrapDeleteButton extends IndicatingAjaxButton {

	private static final long serialVersionUID = 1L;

	/**
	 * @param id
	 * @param model
	 */
	public IndicatingAjaxBootstrapDeleteButton(String id, IModel<String> model) {
		super(id, model);
		add(new AttributeAppender("class", new Model<String>("btn btn-danger"), " "));
		add(new AttributeAppender("onclick", new Model<String>("window.onbeforeunload = null;"), " "));
		setDefaultFormProcessing(false);
	}

	@Override
	protected abstract void onSubmit(AjaxRequestTarget target, Form<?> form);

	@Override
	protected void updateAjaxAttributes(AjaxRequestAttributes attributes) {

		super.updateAjaxAttributes(attributes);
		AjaxCallListener ajaxCallListener = new AjaxCallListener();
		ajaxCallListener.onPrecondition("return confirm('Confirm Delete?');");
		attributes.getAjaxCallListeners().add(ajaxCallListener);
	}

	@Override
	protected void onError(final AjaxRequestTarget target, Form<?> form) {
	}

}
