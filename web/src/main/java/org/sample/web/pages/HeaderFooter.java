package org.sample.web.pages;

import org.apache.wicket.Component;
import org.apache.wicket.authroles.authorization.strategies.role.metadata.MetaDataRoleAuthorizationStrategy;
import org.apache.wicket.model.Model;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.sample.web.app.security.Roles;

import de.agilecoders.wicket.core.markup.html.bootstrap.common.NotificationPanel;
import de.agilecoders.wicket.core.markup.html.bootstrap.image.IconType;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.Navbar;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarButton;
import de.agilecoders.wicket.core.markup.html.bootstrap.navbar.NavbarComponents;

/**
 * @author mpostelnicu
 */
public abstract class HeaderFooter extends AbstractWebPage {

	private static final long serialVersionUID = 1L;
	protected NotificationPanel feedbackPanel;

	@Override
	protected void onInitialize() {
		super.onInitialize();
		add(createNavbar());
		feedbackPanel = createFeedbackPanel();
		add(feedbackPanel);
	}

	public HeaderFooter() {
		super();
	}

	public HeaderFooter(PageParameters parameters) {
		super(parameters);
	}

	protected void addHomePageButton(Navbar navbar) {
		NavbarButton<HomePage> navbarButton = new NavbarButton<HomePage>(this.getApplication().getHomePage(),
				Model.of("Home")).setIconType(IconType.home);
		
		navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.LEFT, navbarButton));
	}

	protected void addLogoutButton(Navbar navbar) {
		NavbarButton<Logout> navbarButton = new NavbarButton<Logout>(Logout.class, Model.of("Logout"))
				.setIconType(IconType.off);
		navbar.addComponents(NavbarComponents.transform(Navbar.ComponentPosition.RIGHT, navbarButton));
		MetaDataRoleAuthorizationStrategy.authorize(navbarButton, Component.RENDER, Roles.USER);
	}



	protected Navbar createNavbar() {
		Navbar navbar = new Navbar("navbar");
		navbar.setPosition(Navbar.Position.TOP);
		navbar.brandName(Model.of("Sample"));
		addHomePageButton(navbar);
		addLogoutButton(navbar);

		navbar.setVisible(!headless);
		return navbar;
	}

}
