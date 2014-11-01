package org.sample.web.components;

import org.apache.wicket.Component;
import org.apache.wicket.behavior.Behavior;
import org.apache.wicket.markup.ComponentTag;

/**
 * A behavior that adds a placeholder attribute support for an input components.
 */
public class PlaceholderBehavior extends Behavior {


	private static final long serialVersionUID = 1L;
	private final String placeholder;

    public PlaceholderBehavior(String placeholder) {
        this.placeholder = placeholder;
    }

    @Override
    public void onComponentTag(Component component, ComponentTag tag) {
        super.onComponentTag(component, tag);
        tag.put("placeholder", placeholder);
    }
}
