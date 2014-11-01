package org.sample.web.pages;

import org.apache.wicket.authroles.authorization.strategies.role.annotations.AuthorizeInstantiation;
import org.apache.wicket.model.Model;
import org.sample.web.app.security.Roles;
import org.sample.web.components.IconPanel;
import org.sample.web.pages.lists.ListCategoryDummyPage;
import org.sample.web.pages.lists.ListDummyPage;
import org.sample.web.pages.nav.AbstractNavPage;


/**
 * @author mpostelnicu
 * 
 */
@AuthorizeInstantiation({ Roles.USER })
public class HomePage extends AbstractNavPage {
	private static final long serialVersionUID = 1L;



	public HomePage() {
		super();

		listIcons.add(new IconPanel<ListDummyPage>(listIcons.newChildId(),
				Model.of("Dummy"), ListDummyPage.class, "fa-book"));
		
		listIcons.add(new IconPanel<ListCategoryDummyPage>(listIcons.newChildId(),
				Model.of("Category Dummy"), ListCategoryDummyPage.class, "fa-eye"));

	}

}