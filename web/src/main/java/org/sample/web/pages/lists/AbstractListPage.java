/**
 * 
 */
package org.sample.web.pages.lists;

import java.util.ArrayList;
import java.util.List;

import org.apache.wicket.extensions.ajax.markup.html.repeater.data.table.AjaxFallbackDefaultDataTable;
import org.apache.wicket.extensions.markup.html.repeater.data.grid.ICellPopulator;
import org.apache.wicket.extensions.markup.html.repeater.data.table.AbstractColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.IColumn;
import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.markup.html.link.Link;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.markup.repeater.Item;
import org.apache.wicket.model.IModel;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sample.web.app.WebConstants;
import org.sample.web.components.providers.datatable.SortableJpaRepositoryDataProvider;
import org.sample.web.exceptions.NullEditPageClassException;
import org.sample.web.exceptions.NullJpaRepositoryException;
import org.sample.web.pages.HeaderFooter;
import org.sample.web.pages.edits.AbstractEditPage;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author mpostelnicu
 * 
 */
public abstract class AbstractListPage<T extends AbstractPersistable<Long>> extends HeaderFooter {
	
	
	private class ActionPanel extends Panel {

		private static final long serialVersionUID = 1L;

		/**
		 * @param id
		 *            component id
		 * @param model
		 *            model for contact
		 */
		public ActionPanel(String id, IModel<T> model) {
			super(id, model);
			add(new Link<T>("edit") {
				private static final long serialVersionUID = 1L;
				@SuppressWarnings("unchecked")
				@Override
				public void onClick() {
					setEditResponsePage((T) ActionPanel.this.getDefaultModelObject());				
				}
			});
		}		
	}
	
	protected void setEditResponsePage(T entity) {
		PageParameters pageParameters = new PageParameters();
		if(entity!=null) pageParameters.set(AbstractEditPage.PARAM_ID,entity.getId());
		setResponsePage(editPageClass, pageParameters);	
	}
	
	protected Class<? extends AbstractEditPage<T>> editPageClass;

	private static final long serialVersionUID = 1L;
	protected AjaxFallbackDefaultDataTable<T, String> dataTable;
	protected List<IColumn<T, String>> columns;

	@SpringBean
	protected Environment environment;

	protected JpaRepository<T, Long> jpaRepository;

	public AbstractListPage() {
		
		add(createPageTitleTag("list.title"));
		add(createPageHeading("list.heading"));
		add(createPageMessage("list.message"));

		columns = new ArrayList<IColumn<T, String>>();
		columns.add(new PropertyColumn<T, String>(new Model<String>("ID"), "id"));
		
		add(new Link<T>("new") {
			private static final long serialVersionUID = 1L;
			@Override
			public void onClick() {
				setEditResponsePage(null);
			}
		});
	}
	


	@Override
	protected void onInitialize() {
		super.onInitialize();
		
		if (jpaRepository == null)
			throw new NullJpaRepositoryException();
		if (editPageClass == null)
			throw new NullEditPageClassException();
		
		columns.add(new AbstractColumn<T, String>(new Model<String>("Actions")) {
			private static final long serialVersionUID = 1L;

			public void populateItem(Item<ICellPopulator<T>> cellItem, String componentId, IModel<T> model) {
				cellItem.add(new ActionPanel(componentId, model));
			}
		});
		dataTable = new AjaxFallbackDefaultDataTable<T, String>("table", columns,
				new SortableJpaRepositoryDataProvider<T>(jpaRepository), WebConstants.PAGE_SIZE);
		add(dataTable);
	}

}
