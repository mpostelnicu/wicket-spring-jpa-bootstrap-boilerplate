/**
 * 
 */
package org.sample.web.pages.lists;

import org.apache.wicket.extensions.markup.html.repeater.data.table.PropertyColumn;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.spring.injection.annot.SpringBean;
import org.sample.persistence.dao.Dummy;
import org.sample.persistence.repository.DummyRepository;
import org.sample.web.pages.edits.EditDummyPage;

/**
 * @author mpostelnicu
 *
 */
public class ListDummyPage extends AbstractListPage<Dummy> {
	
	private static final long serialVersionUID = 1L;
	
	@SpringBean
	protected DummyRepository dummyRepository;
	
	public ListDummyPage(PageParameters pageParameters) {

		this.jpaRepository=dummyRepository;
		this.editPageClass=EditDummyPage.class;
		columns.add(new PropertyColumn<Dummy,String>(new Model<String>("Name"), "name", "name"));
	}


}
