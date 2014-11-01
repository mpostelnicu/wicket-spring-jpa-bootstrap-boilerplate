/**
 * 
 */
package org.sample.web.pages.edits;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sample.persistence.dao.CategoryDummy;
import org.sample.persistence.dao.Dummy;
import org.sample.persistence.repository.CategoryDummyRepository;
import org.sample.persistence.repository.DummyRepository;
import org.sample.web.components.Select2ChoiceBootstrapFormComponent;
import org.sample.web.components.TextFieldBootstrapFormComponent;
import org.sample.web.components.providers.textchoice.StringJpaTextChoiceProvider;
import org.sample.web.pages.lists.ListDummyPage;

/**
 * @author mpostelnicu
 *
 */
public class EditDummyPage extends AbstractEditPage<Dummy> {


	private static final long serialVersionUID = 1L;


	/* (non-Javadoc)
	 * @see org.devgateway.ccrs.web.wicket.page.AbstractEditPage#newInstance()
	 */
	@Override
	protected Dummy newInstance() {
		return new Dummy();
	}
	
	@SpringBean
	private DummyRepository dummyRepository;
	
	@SpringBean
	private CategoryDummyRepository categoryDummyRepository;
	
	
	public EditDummyPage(PageParameters parameters) {
		super(parameters);
		this.jpaRepository = dummyRepository;
		this.listPageClass = ListDummyPage.class;
		
		editForm.add(new TextFieldBootstrapFormComponent<>("name", new Model<>("Name")).required());
		
		editForm.add(new Select2ChoiceBootstrapFormComponent<CategoryDummy>("category", new Model<>(
				"Dummy Category"), new StringJpaTextChoiceProvider<CategoryDummy>(
						categoryDummyRepository)));
	
	}
	
}
