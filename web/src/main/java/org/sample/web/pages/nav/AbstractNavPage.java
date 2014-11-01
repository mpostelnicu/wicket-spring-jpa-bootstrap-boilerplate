/**
 * 
 */
package org.sample.web.pages.nav;

import org.apache.wicket.markup.repeater.RepeatingView;
import org.sample.web.pages.HeaderFooter;

/**
 * @author mpostelnicu
 *
 */
public abstract class AbstractNavPage extends HeaderFooter {

	private static final long serialVersionUID = 1L;
	protected RepeatingView listIcons;

	public AbstractNavPage() {
		super();

		add(createPageTitleTag("nav.title"));
		add(createPageHeading("nav.title"));
		add(createPageMessage("nav.message"));

		listIcons = new RepeatingView("listIcons");
		add(listIcons);
	}

}
