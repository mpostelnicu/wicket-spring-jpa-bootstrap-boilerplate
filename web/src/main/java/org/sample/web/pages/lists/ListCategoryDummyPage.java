/**
 * 
 */
package org.sample.web.pages.lists;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sample.persistence.dao.CategoryDummy;
import org.sample.persistence.repository.CategoryDummyRepository;
import org.sample.web.pages.edits.EditCategoryDummyPage;

/**
 * @author mpostelnicu
 *
 */
public class ListCategoryDummyPage extends AbstractListPage<CategoryDummy> {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	protected CategoryDummyRepository categoryDummyRepository;
	
	public ListCategoryDummyPage(PageParameters pageParameters) {

		this.jpaRepository=categoryDummyRepository;
		this.editPageClass=EditCategoryDummyPage.class;
		columns.add(new PropertyColumn<CategoryDummy,String>(new Model<String>("Name"), "name", "name"));
	}


}
