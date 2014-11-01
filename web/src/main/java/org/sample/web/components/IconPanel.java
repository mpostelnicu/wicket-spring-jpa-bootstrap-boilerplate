/**
 * 
 */
package org.sample.web.components;

import org.apache.wicket.AttributeModifier;
import org.apache.wicket.Page;
import org.apache.wicket.markup.html.basic.Label;
import org.apache.wicket.markup.html.link.BookmarkablePageLink;
import org.apache.wicket.markup.html.panel.Panel;
import org.apache.wicket.model.IModel;

/**
 * @author mpostelnicu
 *
 */
public class IconPanel<C extends Page> extends Panel {


	private static final long serialVersionUID = 1L;
	
	private final BookmarkablePageLink<Void> bookmarkablePageLink;
	private final Label label;
	private Label image;

	public IconPanel(String id, IModel<String> labelModel, Class<C> pageClass, String iconClass) {
		super(id);
		bookmarkablePageLink=new BookmarkablePageLink<Void>("link", pageClass);
		add(bookmarkablePageLink);		
		image=new Label("image");
		image.add(AttributeModifier.append("class", "fa fa-5x "+iconClass));
		bookmarkablePageLink.add(image);
		label=new Label("label",labelModel);
		bookmarkablePageLink.add(label);
	}

}
