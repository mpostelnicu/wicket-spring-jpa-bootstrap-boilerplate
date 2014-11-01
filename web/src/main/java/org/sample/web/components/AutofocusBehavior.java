package org.sample.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * A behavior that adds a autofocus attribute support for an input components.
 */
public class AutofocusBehavior extends Behavior {

	private static final long serialVersionUID = 1L;

	@Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        tag.put("autofocus", "autofocus");
    }
}
