/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.ajax.form.AjaxFormComponentUpdatingBehavior;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.form.FormComponent;
import org.apache.wicket.markup.html.form.validation.FormComponentFeedbackBorder;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;
import org.sample.web.models.SubComponentWrapModel;

import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior;
import de.agilecoders.wicket.core.markup.html.bootstrap.form.InputBehavior.Size;

/**
 * @author mpostelnicu
 * 
 */
public abstract class GenericBootstrapFormComponent<TYPE, FIELD extends FormComponent<TYPE>> extends Panel {

	private static final long serialVersionUID = 1L;
	protected FormComponentFeedbackBorder border;
	protected FIELD field;
	
	protected InputBehavior sizeBehavior;
	
	@SuppressWarnings("unchecked")
	protected IModel<TYPE> initFieldModel() {
		if (getDefaultModel() == null)
			return new SubComponentWrapModel<TYPE>(this);
		return (IModel<TYPE>) getDefaultModel();
	}

	protected String getUpdateEvent() {
		return "onblur";
	}
	
	public void size(Size size) {
		sizeBehavior.size(size);
	}

	public GenericBootstrapFormComponent(String id, IModel<String> labelModel, IModel<TYPE> model) {
		super(id, model);
		border = new FormComponentFeedbackBorder("border");
		border.setOutputMarkupId(true);
		add(border);
		setOutputMarkupId(true);
		border.add(new Label("label", labelModel));
		field = inputField("field", model);
		field.setLabel(labelModel);
		field.setOutputMarkupId(true);
		sizeBehavior=new InputBehavior(InputBehavior.Size.XXLarge);
		field.add(sizeBehavior);
		
		field.add(new AjaxFormComponentUpdatingBehavior(getUpdateEvent()) {

			private static final long serialVersionUID = 1L;

			@Override
			protected void onUpdate(AjaxRequestTarget target) {
				target.add(border);
				GenericBootstrapFormComponent.this.onUpdate(target);
			}

			@Override
			protected void onError(AjaxRequestTarget target, RuntimeException e) {
				target.add(border);
			}
		});
		border.add(field);
	}

	protected abstract FIELD inputField(String id, IModel<TYPE> model);

	public GenericBootstrapFormComponent<TYPE, FIELD> required() {
		field.setRequired(true);
		return this;
	}

	protected void onUpdate(AjaxRequestTarget target) {
	}
	
	public FIELD getField() {
		return field;
	}
}
