/**
 * 
 */
package org.sample.web.pages.edits;

import org.apache.wicket.ajax.AjaxRequestTarget;
import org.apache.wicket.markup.html.form.Form;
import org.apache.wicket.model.CompoundPropertyModel;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.util.time.Duration;
import org.sample.web.components.IndicatingAjaxBootstrapCancelButton;
import org.sample.web.components.IndicatingAjaxBootstrapDeleteButton;
import org.sample.web.components.IndicatingAjaxBootstrapSubmitButton;
import org.sample.web.exceptions.NullJpaRepositoryException;
import org.sample.web.exceptions.NullListPageClassException;
import org.sample.web.models.PersistableJpaRepositoryModel;
import org.sample.web.pages.HeaderFooter;
import org.sample.web.pages.lists.AbstractListPage;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationMessage;

/**
 * @author mpostelnicu
 *
 */
public abstract class AbstractEditPage<T extends AbstractPersistable<Long>> extends HeaderFooter {

	private static final long serialVersionUID = 1L;
	public static final String PARAM_ID="id";
	
	protected abstract T newInstance();

	protected JpaRepository<T, Long> jpaRepository;
	
	protected Class<? extends AbstractListPage<T>> listPageClass;
	
	protected EditForm editForm;
	
	protected Long entityId;

	
	protected IndicatingAjaxBootstrapSubmitButton saveButton;
	
	protected IndicatingAjaxBootstrapDeleteButton deleteButton;

	
	public class SaveEditPageButton extends IndicatingAjaxBootstrapSubmitButton {

		private static final long serialVersionUID = 1L;

		public SaveEditPageButton(String id, IModel<String> model) {
			super(id, model);
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			@SuppressWarnings("unchecked")
			T saveable =  editForm.getModelObject();
			jpaRepository.save(saveable);
			if(headless) 
				target.appendJavaScript("javascript:self.close()");
			else 
				setResponsePage(listPageClass);				
		}
		
		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
			target.add(feedbackPanel);
		}
		
	}

	public class DeleteEditPageButton extends IndicatingAjaxBootstrapDeleteButton {
		
		private static final long serialVersionUID = 1L;

		public DeleteEditPageButton(String id, IModel<String> model) {
			super(id, model);
		}

		@Override
		protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
			T deleteable = editForm.getModelObject();
			try {
				jpaRepository.delete(deleteable);
			} catch (DataIntegrityViolationException e) {
				error(new NotificationMessage(Model.of("Nu pot sterge datele deoarece sunt inca folosite!"))
						.hideAfter(Duration.NONE));
				target.add(feedbackPanel);
				return;
			}
			setResponsePage(listPageClass);
		}
		
		@Override
		protected void onError(AjaxRequestTarget target, Form<?> form) {
			target.add(feedbackPanel);
		}
		
	}
	
	public SaveEditPageButton getSaveEditPageButton() {
		return new SaveEditPageButton("save", new Model<>("Save"));
	}
	
	public DeleteEditPageButton getDeleteEditPageButton() {
		return new DeleteEditPageButton("delete", new Model<>("Delete"));
	}
	
	public boolean isEntityPersisted() {
		return entityId!=null;
	}
	
	public class EditForm extends Form<T> {

		private static final long serialVersionUID = 1L;
		
		public void setCompoundPropertyModel(final IModel<T> model) {
			setModel(new CompoundPropertyModel<T>(model));
		}

		public EditForm(final String id, final IModel<T> model) {
			this(id);
			setCompoundPropertyModel(model);
		}

		public EditForm(final String id) {
			super(id);

			saveButton= getSaveEditPageButton();
			
			add(saveButton);
			
			deleteButton= getDeleteEditPageButton();
			
			add(deleteButton);
			
			add(new IndicatingAjaxBootstrapCancelButton("cancel", new Model<>("Cancel")) {

				private static final long serialVersionUID = 1L;

				@Override
				protected void onSubmit(AjaxRequestTarget target, Form<?> form) {
					if(headless) 
						target.appendJavaScript("javascript:self.close()");
					else
						setResponsePage(listPageClass);
				}
			});
		}
	}
	
	public AbstractEditPage(PageParameters parameters) {
		super(parameters);
		
		
		add(createPageTitleTag("edit.title"));
		add(createPageHeading("edit.heading"));
		add(createPageMessage("edit.message"));

		if (!parameters.get(PARAM_ID).isNull())
			entityId = parameters.get(PARAM_ID).toLongObject();

		editForm = new EditForm("editForm");
		add(editForm);

	}
	
	@Override
	protected void onInitialize() {
		// TODO Auto-generated method stub
		super.onInitialize();
		if (jpaRepository == null)
			throw new NullJpaRepositoryException();
		if (listPageClass == null)
			throw new NullListPageClassException();


		IModel<T> model = null;
		if (entityId != null)
			model = new PersistableJpaRepositoryModel<T>(entityId,
					jpaRepository);
		else
			model = new Model<T>(newInstance());

		editForm.setCompoundPropertyModel(model);
	}
	
	

}
