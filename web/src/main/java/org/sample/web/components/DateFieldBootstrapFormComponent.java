/**
 * 
 */
package org.sample.web.components;

import java.util.Date;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.extensions.ajax.markup.html.IndicatingAjaxLink;
import org.apache.wicket.markup.html.form.TextField;
import org.apache.wicket.model.IModel;

import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextField;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig;
import de.agilecoders.wicket.extensions.markup.html.bootstrap.form.DateTextFieldConfig.View;

/**
 * @author mpostelnicu
 * 
 */
public class DateFieldBootstrapFormComponent extends GenericBootstrapFormComponent<Date, TextField<Date>> {

	private static final long serialVersionUID = 1L;
	public static final String FORMAT="dd/MM/yyyy";
	
	public DateFieldBootstrapFormComponent(String id, IModel<String> labelModel, IModel<Date> model) {
		super(id, labelModel, model);
		
		IndicatingAjaxLink<String> clearDateLink = new IndicatingAjaxLink<String>("clearDate") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick(AjaxRequestTarget target) {
				DateFieldBootstrapFormComponent.this.field.setModelObject(null);
				target.add(DateFieldBootstrapFormComponent.this.field);
			}
		};
		border.add(clearDateLink);
	}
	
	public DateFieldBootstrapFormComponent(String id, IModel<String> labelModel) {
		this(id,labelModel,null);
	}

	@Override
	protected TextField<Date> inputField(String id, IModel<Date> model) {
	       DateTextFieldConfig config = new DateTextFieldConfig().withView(View.Year).
	        		withFormat(FORMAT).autoClose(true).forceParse(false).showTodayButton(true).highlightToday(true);        
		return (TextField<Date>) new DateTextField(id,initFieldModel(),config);
	}

	@Override
	protected String getUpdateEvent() {
		return "onchange";
	}
	
}
