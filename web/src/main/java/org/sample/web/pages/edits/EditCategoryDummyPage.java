/**
 * 
 */
package org.sample.web.pages.edits;

import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sample.persistence.dao.CategoryDummy;
import org.sample.persistence.repository.CategoryDummyRepository;
import org.sample.web.components.TextFieldBootstrapFormComponent;
import org.sample.web.pages.lists.ListCategoryDummyPage;

/**
 * @author mpostelnicu
 *
 */
public class EditCategoryDummyPage extends AbstractEditPage<CategoryDummy> {


	private static final long serialVersionUID = 1L;


	/* (non-Javadoc)
	 * @see org.devgateway.ccrs.web.wicket.page.AbstractEditPage#newInstance()
	 */
	@Override
	protected CategoryDummy newInstance() {
		return new CategoryDummy();
	}
	
	@SpringBean
	private CategoryDummyRepository categoryDummyRepository;
	

	
	public EditCategoryDummyPage(PageParameters parameters) {
		super(parameters);
		this.jpaRepository = categoryDummyRepository;
		this.listPageClass = ListCategoryDummyPage.class;
		
		editForm.add(new TextFieldBootstrapFormComponent<>("name", new Model<>("Name")).required());
		
	
	}
	
}
